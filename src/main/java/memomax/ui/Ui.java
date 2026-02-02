package memomax.ui;

import java.util.ArrayList;
import java.util.Scanner;

import memomax.task.Task;

/**
 * Handles user interface display for MemoMax.
 * Manages formatted output and error messages.
 */
public class Ui {
    private static final String DIVIDER = "    ________________________________________________________";
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
     * @return The formatted error message
     */
    public String showErrorMessage(String message) {
        return "Oops! " + message + "\n"
                + "Enter 'help' for more information\n"
                + DIVIDER;
    }

    /**
     * Shows a storage/file system error message.
     *
     * @param message the storage error message to display
     * @return The formatted storage error message
     */
    public String showStorageError(String message) {
        return "[Storage] " + message + "\n"
                + DIVIDER;
    }

    /**
     * Shows the welcome message with logo.
     *
     * @param logo The ASCII art logo to display
     * @return The formatted welcome message
     */
    public String showWelcome(String logo) {
        return "Hello! I'm MemoMax\n"
                + "What can I do for you?" + logo + "\n"
                + DIVIDER;
    }

    /**
     * Shows the goodbye message.
     *
     * @return The formatted goodbye message
     */
    public String showGoodbye() {
        return "Bye. Hope to see you again soon!\n"
                + DIVIDER;
    }

    /**
     * Displays a formatted list of tasks.
     *
     * @param tasks The list of tasks to display
     * @param isEmpty Whether the task list is empty
     * @return The formatted task list string
     */
    public String showTaskList(ArrayList<Task> tasks, boolean isEmpty) {
        if (isEmpty) {
            return "There are currently no tasks in your list\n" + DIVIDER;
        } else {
            StringBuilder sb = new StringBuilder("Here is/are the task(s) in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1)).append(".").append(tasks.get(i).toString()).append("\n");
            }
            sb.append("You have ").append(tasks.size()).append(" task(s) in the list.\n");
            sb.append(DIVIDER);
            return sb.toString();
        }
    }

    /**
     * Displays a task added confirmation.
     *
     * @param task The task that was added
     * @param taskCount The new total number of tasks
     * @return The formatted confirmation message
     */
    public String showTasksAdded(Task task, int taskCount) {
        return "Got it. I've added this task:\n"
                + " " + task.toString() + "\n"
                + "Now you have " + taskCount + " task(s) in the list.\n"
                + DIVIDER;
    }

    /**
     * Displays a task marked as done confirmation.
     *
     * @param task The task that was marked
     * @return The formatted confirmation message
     */
    public String showTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n"
                + " " + task.toString() + "\n"
                + DIVIDER;
    }

    /**
     * Displays a task marked as not done confirmation.
     *
     * @param task The task that was unmarked
     * @return The formatted confirmation message
     */
    public String showTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n"
                + " " + task.toString() + "\n"
                + DIVIDER;
    }

    /**
     * Displays a task deleted confirmation.
     *
     * @param task The task that was deleted
     * @param taskCount The new total number of tasks
     * @return The formatted confirmation message
     */
    public String showTaskDeleted(Task task, int taskCount) {
        return "Noted. I've removed this task:\n"
                + " " + task.toString() + "\n"
                + "Now you have " + taskCount + " task(s) in the list.\n"
                + DIVIDER;
    }

    /**
     * Displays search results for the find command.
     *
     * @param matchingTasks List of tasks matching the specified keyword.
     * @param keyword The search keyword.
     * @return The formatted find results
     */
    public String showFindResults(ArrayList<Task> matchingTasks, String keyword) {
        StringBuilder sb = new StringBuilder();
        if (matchingTasks.isEmpty()) {
            sb.append("No tasks found containing: '").append(keyword).append("'\n");
        } else {
            sb.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append((i + 1)).append(".").append(matchingTasks.get(i)).append("\n");
            }
        }
        sb.append(DIVIDER);
        return sb.toString();
    }

    /**
     * Displays the help information.
     *
     * @return The formatted help information
     */
    public String showHelp() {
        return "Here's what I can help with:\n"
                + "1. Add a task: todo <description>\n"
                + "2. Add a deadline: deadline <task> /by yyyy-MM-dd HHmm\n"
                + "3. Add an event: event <task> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm\n"
                + "4. See all tasks: list\n"
                + "5. Mark as done: mark <number>\n"
                + "6. Mark as not done: unmark <number>\n"
                + "7. Delete a task: delete <number>\n"
                + "8. Find tasks: find <keyword>\n"
                + "9. Say goodbye: bye\n"
                + DIVIDER;
    }

    /**
     * Displays unknown command message.
     *
     * @return The formatted unknown command message
     */
    public String showUnknownCommand() {
        return "Invalid command\n"
                + "Type 'help' for more options\n"
                + DIVIDER;
    }
}