public class Ui {
    private static final String DIVIDER = "    ____________________________________________________________";

    public void showMessage(String message) {
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    public void showErrorMessage(String message) {
        System.out.println(message);
        System.out.println("Enter 'help' for more information");
        System.out.println(DIVIDER);
    }
}
