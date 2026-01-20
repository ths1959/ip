/**
 * Handles user interface display for MemoMax.
 * Manages formatted output and error messages.
 */
public class Ui {
    private static final String DIVIDER = "    ____________________________________________________________";

    /**
     * Displays a standard message with divider.
     *
     * @param message Message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    /**
     * Displays an error message with help prompt.
     *
     * @param message Error message to display
     */
    public void showErrorMessage(String message) {
        System.out.println("Oops! " + message);
        System.out.println("Enter 'help' for more information");
        System.out.println(DIVIDER);
    }
}
