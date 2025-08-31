/**
 * Base exception for all Cathy-related errors.
 */
public class CathyException extends Exception {

    public CathyException() {
        super();
    }

    public CathyException(String message) {
        super("     " + message);
    }
    public CathyException(String message, Throwable cause) {
        super("     " + message, cause);
    }
}
