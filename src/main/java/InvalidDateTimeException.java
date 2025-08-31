/**
 * Exception thrown when a provided date/time string
 * cannot be parsed into a valid LocalDate or LocalDateTime.
 */
public class InvalidDateTimeException extends RuntimeException {

    /**
     * Constructs a new InvalidDateTimeException with the default message.
     */
    public InvalidDateTimeException() {
        super("Use yyyy-MM-dd or yyyy-MM-dd HHmm.\n     It's not that hard.");
    }

    /**
     * Constructs a new InvalidDateTimeException with a custom message.
     *
     * @param message custom error message
     */
    public InvalidDateTimeException(String message) {
        super(message);
    }
}