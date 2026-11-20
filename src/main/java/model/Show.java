package model;

import java.util.HashSet;

/** Stores one scheduled movie show and its booked seats. */
public class Show {
    private String showId;
    private String movieId;
    private String theatreName;
    private String showTime;
    private double ticketPrice;
    private int totalSeats;
    private HashSet<Integer> bookedSeats = new HashSet<Integer>();

    public Show(String showId, String movieId, String theatreName,
                String showTime, double ticketPrice, int totalSeats) {
        this.showId = showId;
        this.movieId = movieId;
        this.theatreName = theatreName;
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
    }

    public String getShowId() {
        return showId;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public String getShowTime() {
        return showTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeatCount() {
        return totalSeats - bookedSeats.size();
    }

    public boolean isSeatBooked(int seatNumber) {
        return bookedSeats.contains(seatNumber);
    }

    public void bookSeat(int seatNumber) {
        bookedSeats.add(seatNumber);
    }

    public void releaseSeat(int seatNumber) {
        bookedSeats.remove(seatNumber);
    }
}
