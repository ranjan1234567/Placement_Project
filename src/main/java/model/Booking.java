package model;

import java.util.ArrayList;

/** Stores one customer's booking. */
public class Booking {
    private String bookingId;
    private Customer customer;
    private Show show;
    private ArrayList<Integer> seatNumbers;
    private double totalAmount;
    private boolean cancelled;

    public Booking(String bookingId, Customer customer, Show show, ArrayList<Integer> seatNumbers) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.show = show;
        this.seatNumbers = new ArrayList<Integer>(seatNumbers);
        this.totalAmount = show.getTicketPrice() * seatNumbers.size();
        this.cancelled = false;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Show getShow() {
        return show;
    }

    public ArrayList<Integer> getSeatNumbers() {
        return new ArrayList<Integer>(seatNumbers);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId
                + " | Movie show: " + show.getShowId()
                + " | Seats: " + seatNumbers
                + " | Amount: Rs." + totalAmount;
    }
}
