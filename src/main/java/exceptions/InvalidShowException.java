package exceptions;

/** Thrown when the entered show ID does not exist. */
public class InvalidShowException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidShowException(String message) {
        super(message);
    }
}
