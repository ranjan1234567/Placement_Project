package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import datastructures.WaitingListQueue;
import exceptions.BookingNotFoundException;
import exceptions.InvalidShowException;
import exceptions.SeatNotAvailableException;
import model.Booking;
import model.Customer;
import model.Show;
import model.WaitListEntry;

/** Handles booking, cancellation, seat allocation, and waiting lists. */
public class BookingService {
    private MovieService movieService;
    private HashMap<String, Booking> bookings = new HashMap<String, Booking>();
    private HashMap<String, WaitingListQueue> waitingLists = new HashMap<String, WaitingListQueue>();
    private int bookingNumber = 1000;

    public BookingService(MovieService movieService) {
        this.movieService = movieService;
    }

    /** Checks all seats first, then books them. */
    public Booking bookTickets(Customer customer, String showId, ArrayList<Integer> seats)
            throws InvalidShowException, SeatNotAvailableException {

        Show show = movieService.findShow(showId);
        if (show == null) {
            throw new InvalidShowException("Show not found.");
        }

        if (seats.size() == 0) {
            throw new SeatNotAvailableException("Please select at least one seat.");
        }

        // HashSet helps us detect if the same seat number was entered twice.
        HashSet<Integer> selectedSeats = new HashSet<Integer>();

        for (int i = 0; i < seats.size(); i++) {
            int seat = seats.get(i);

            if (seat < 1 || seat > show.getTotalSeats()) {
                throw new SeatNotAvailableException("Seat number " + seat + " is invalid.");
            }

            if (selectedSeats.contains(seat)) {
                throw new SeatNotAvailableException("Seat " + seat + " was entered more than once.");
            }

            if (show.isSeatBooked(seat)) {
                throw new SeatNotAvailableException("Seat " + seat + " is already booked.");
            }

            selectedSeats.add(seat);
        }

        // All seats are valid, so now mark them as booked.
        for (int i = 0; i < seats.size(); i++) {
            show.bookSeat(seats.get(i));
        }

        bookingNumber++;
        String bookingId = "B" + bookingNumber;
        Booking booking = new Booking(bookingId, customer, show, seats);
        bookings.put(bookingId, booking);
        return booking;
    }

    /** Adds a request to the end of the waiting list for a show. */
    public void joinWaitingList(Customer customer, String showId, int seatCount)
            throws InvalidShowException {

        Show show = movieService.findShow(showId);
        if (show == null) {
            throw new InvalidShowException("Show not found.");
        }

        if (seatCount < 1 || seatCount > show.getTotalSeats()) {
            throw new IllegalArgumentException("Invalid number of seats.");
        }

        WaitingListQueue queue = waitingLists.get(showId.toUpperCase());
        if (queue == null) {
            queue = new WaitingListQueue();
            waitingLists.put(showId.toUpperCase(), queue);
        }

        WaitListEntry entry = new WaitListEntry(customer, seatCount);
        queue.enqueue(entry);
    }

    /** Cancels a booking and makes its seats available again. */
    public Booking cancelBooking(String bookingId, String customerId)
            throws BookingNotFoundException {

        Booking booking = bookings.get(bookingId.toUpperCase());

        if (booking == null || booking.isCancelled()
                || !booking.getCustomer().getUserId().equalsIgnoreCase(customerId)) {
            throw new BookingNotFoundException("Active booking not found for your account.");
        }

        booking.cancel();
        Show show = booking.getShow();
        ArrayList<Integer> seats = booking.getSeatNumbers();

        for (int i = 0; i < seats.size(); i++) {
            show.releaseSeat(seats.get(i));
        }

        return booking;
    }

    /** Returns only active bookings for one customer. */
    public ArrayList<Booking> getCustomerBookings(String customerId) {
        ArrayList<Booking> result = new ArrayList<Booking>();

        for (Booking booking : bookings.values()) {
            if (!booking.isCancelled()
                    && booking.getCustomer().getUserId().equalsIgnoreCase(customerId)) {
                result.add(booking);
            }
        }

        return result;
    }

    /** Checks the waiting list after a cancellation and books seats for customers in FIFO order. */
    public ArrayList<Booking> processWaitingList(String showId) {
        ArrayList<Booking> newBookings = new ArrayList<Booking>();
        Show show = movieService.findShow(showId);
        WaitingListQueue queue = waitingLists.get(showId.toUpperCase());

        if (show == null || queue == null) {
            return newBookings;
        }

        while (!queue.isEmpty()) {
            WaitListEntry nextEntry = queue.peek();
            int neededSeats = nextEntry.getSeatCount();

            // Do not skip the first customer if there are not enough seats for them.
            if (show.getAvailableSeatCount() < neededSeats) {
                break;
            }

            queue.dequeue();
            ArrayList<Integer> freeSeats = new ArrayList<Integer>();

            for (int seat = 1; seat <= show.getTotalSeats() && freeSeats.size() < neededSeats; seat++) {
                if (!show.isSeatBooked(seat)) {
                    freeSeats.add(seat);
                }
            }

            for (int i = 0; i < freeSeats.size(); i++) {
                show.bookSeat(freeSeats.get(i));
            }

            bookingNumber++;
            String bookingId = "B" + bookingNumber;
            Booking booking = new Booking(bookingId, nextEntry.getCustomer(), show, freeSeats);
            bookings.put(bookingId, booking);
            newBookings.add(booking);
        }

        return newBookings;
    }

    /** Returns the number of waiting requests, not the number of seats requested. */
    public int getWaitingListCount(String showId) throws InvalidShowException {
        Show show = movieService.findShow(showId);
        if (show == null) {
            throw new InvalidShowException("Show not found.");
        }

        WaitingListQueue queue = waitingLists.get(showId.toUpperCase());
        if (queue == null) {
            return 0;
        }
        return queue.size();
    }

    public ArrayList<Booking> getAllBookings() {
        return new ArrayList<Booking>(bookings.values());
    }
}
