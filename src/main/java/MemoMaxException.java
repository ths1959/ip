/**
 * Exception specific to MemoMax chatbot errors.
 * Provides user-friendly error messages.
 */
public class MemoMaxException extends Exception {
    /**
     * Creates a MemoMaxException with given error message.
     *
     * @param message Error description for user
     */
    public MemoMaxException(String message) {
        super(message);
    }
}