package neil.exception;

/**
 * Represents an error that can be reported to a Neil user.
 */
public class NeilException extends Exception {
    /**
     * Creates an exception with a message prefixed by the application name.
     *
     * @param message Description of the error.
     */
    public NeilException(String message) {
        super("Neil: " + message);
    }
}
