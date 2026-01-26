import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles user interface display for MemoMax.
 * Manages formatted output and error messages.
 */
public class Ui {
    private static final String DIVIDER = "    ____________________________________________________________";
    private final Scanner scanner;

    /**
     * Creates a new Ui instance with a Scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a command from the user.
     *
     * @return The user's input as a string
     */
    public String readCommand() {
        return scanner.nextLine();
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

    /**
     * Shows a storage/file system error message.
     *
     * @param message the storage error message to display
     */
    public void showStorageError(String message) {
        System.out.println("[Storage] " + message);
        System.out.println(DIVIDER);
    }

    /**
     * Shows the welcome message with logo.
     *
     * @param logo The ASCII art logo to display
     */
    public void showWelcome(String logo) {
        System.out.println("Hello! I'm MemoMax");
        System.out.println("What can I do for you?" + logo);
        System.out.println(DIVIDER);
    }

    /**
     * Shows the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }

    /**
     * Displays a formatted list of tasks.
     *
     * @param tasks The list of tasks to display
     * @param isEmpty Whether the task list is empty
     */
    public void showTaskList(ArrayList<Task> tasks, boolean isEmpty) {
        if (isEmpty) {
            System.out.println("There are currently no tasks in your list");
            System.out.println(DIVIDER);
        } else {
            System.out.println("Here is/are the task(s) in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i).toString());
            }
            System.out.println("You have " + tasks.size() + " task(s) in the list.");
            System.out.println(DIVIDER);
        }
    }

    /**
     * Displays a task added confirmation.
     *
     * @param task The task that was added
     * @param taskCount The new total number of tasks
     */
    public void showTasksAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + task.toString());
        System.out.println("Now you have " + taskCount + " task(s) in the list.");
        System.out.println(DIVIDER);
    }

    /**
     * Displays a task marked as done confirmation.
     *
     * @param task The task that was marked
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(" " + task.toString());
        System.out.println(DIVIDER);
    }

    /**
     * Displays a task marked as not done confirmation.
     *
     * @param task The task that was unmarked
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(" " + task.toString());
        System.out.println(DIVIDER);
    }

    /**
     * Displays a task deleted confirmation.
     *
     * @param task The task that was deleted
     * @param taskCount The new total number of tasks
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(" " + task.toString());
        System.out.println("Now you have " + taskCount + " task(s) in the list.");
        System.out.println(DIVIDER);
    }

    /**
     * Displays the help information.
     */
    public void showHelp() {
        System.out.println("Here's what I can help with:");
        System.out.println("1. Add a task: todo <description>");
        System.out.println("2. Add a deadline: deadline <task> /by yyyy-MM-dd HHmm");
        System.out.println("3. Add an event: event <task> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm");
        System.out.println("4. See all tasks: list");
        System.out.println("5. Mark as done: mark <number>");
        System.out.println("6. Mark as not done: unmark <number>");
        System.out.println("7. Delete a task: delete <number>");
        System.out.println("8. Say goodbye: bye");
        System.out.println(DIVIDER);
    }

    /**
     * Displays unknown command message.
     */
    public void showUnknownCommand() {
        System.out.println("Invalid command");
        System.out.println("Type 'help' for more options");
        System.out.println(DIVIDER);
    }
}
