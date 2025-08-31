/**
 * Base exception for all Cathy-related errors.
 *
 * <p>Use this checked exception for predictable, recoverable conditions such as invalid user input, parse failures,
 * and storage I/O issues. The main loop should catch {@code CathyException} and
 * display a friendly message via {@link Ui}.
 */
public class CathyException extends Exception {

    /**
     * Creates a {@code CathyException} with no detail message.
     */
    public CathyException() {
        super();
    }

    /**
     * Creates a {@code CathyException} with the specified detail message.
     *
     * @param message human-readable error message to show to the user
     */
    public CathyException(String message) {
        super(message);
    }
}
