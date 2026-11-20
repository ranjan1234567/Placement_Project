package exceptions;

/** Thrown when a seat number is invalid, repeated, or already booked. */
public class SeatNotAvailableException extends Exception {
    private static final long serialVersionUID = 1L;

    public SeatNotAvailableException(String message) {
        super(message);
    }
}
