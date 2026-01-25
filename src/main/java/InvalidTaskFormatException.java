/**
 * Exception thrown when task format in storage file is invalid.
 */
public class InvalidTaskFormatException extends MemoMaxException{
    /**
     * Constructs an InvalidTaskFormatException with the specified message.
     *
     * @param message the detail message
     */
    public InvalidTaskFormatException(String message) {
        super(message);
    }
}
