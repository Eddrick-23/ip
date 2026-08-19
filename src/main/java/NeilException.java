public class NeilException extends Exception {
    public NeilException(String message) {
        super("Neil: " + message);
    }
}

