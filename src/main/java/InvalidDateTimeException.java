/**
 * Exception thrown when a provided date/time string cannot be parsed
 * into a valid {@link java.time.LocalDate} or {@link java.time.LocalDateTime}.
 *
 * <p>This is typically raised when the user provides an input string
 * that does not match any supported date/time formats.
 *
 * <p>Commonly supported formats include:
 * <ul>
 *   <li>{@code yyyy-MM-dd} (e.g. {@code 2025-09-10})</li>
 *   <li>{@code yyyy-MM-dd HHmm} (e.g. {@code 2025-09-10 2359})</li>
 *   <li>ISO-8601 {@code yyyy-MM-dd'T'HH:mm} (e.g. {@code 2025-09-10T23:59})</li>
 * </ul>
 *
 * <p>By default, the exception message provides guidance on the expected formats.
 */
public class InvalidDateTimeException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidDateTimeException} with a default message
     * explaining the correct formats.
     */
    public InvalidDateTimeException() {
        super("Invalid date/time format.\n"+
                "     Use yyyy-MM-dd or yyyy-MM-dd HHmm. It's not that hard.");
    }

    /**
     * Constructs a new {@code InvalidDateTimeException} with a custom message.
     *
     * @param message the custom error message to be shown
     */
    public InvalidDateTimeException(String message) {
        super(message);
    }
}