package exceptions;

/** Thrown when a customer tries to cancel a booking that is not theirs or is inactive. */
public class BookingNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public BookingNotFoundException(String message) {
        super(message);
    }
}
