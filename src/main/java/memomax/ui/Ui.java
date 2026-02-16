package memomax.ui;

import java.util.ArrayList;
import java.util.Scanner;

import memomax.task.Task;

/**
 * Handles user interface display for MemoMax.
 * Manages formatted output and error messages.
 */
public class Ui {
    private static final String MESSAGE_COUNT_PREFIX = "Now you have ";
    private static final String MESSAGE_COUNT_SUFFIX = " task(s) in the list.";
    private static final String HELP_PROMPT = "Enter 'help' for more information";

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
     * Helper method to build a formatted message using varargs.
     *
     * @param lines Variable number of strings to be joined by newlines.
     * @return A single formatted string.
     */
    private String buildMessage(String... lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * Displays an error message with help prompt.
     *
     * @param message Error message to display
     * @return The formatted error message
     */
    public String showErrorMessage(String message) {
        return buildMessage(
                "Oops! " + message,
                HELP_PROMPT
        );
    }

    /**
     * Shows a storage/file system error message.
     *
     * @param message the storage error message to display
     * @return The formatted storage error message
     */
    public String showStorageError(String message) {
        return buildMessage("Oops! " + message);
    }

    /**
     * Shows the welcome message with logo.
     *
     * @return The formatted welcome message
     */
    public String showWelcome() {
        return buildMessage(
                "Hello! I'm MemoMax",
                "What can I do for you?"
        );
    }

    /**
     * Shows the goodbye message.
     *
     * @return The formatted goodbye message
     */
    public String showGoodbye() {
        return buildMessage("Bye. Hope to see you again soon!");
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
            return buildMessage("There are currently no tasks in your list");
        }

        ArrayList<String> lines = new ArrayList<>();
        lines.add("Here is/are the task(s) in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            lines.add((i + 1) + "." + tasks.get(i).toString());
        }
        lines.add(MESSAGE_COUNT_PREFIX + tasks.size() + MESSAGE_COUNT_SUFFIX);

        return buildMessage(lines.toArray(new String[0]));
    }

    /**
     * Displays a task added confirmation.
     *
     * @param task The task that was added
     * @param taskCount The new total number of tasks
     * @return The formatted confirmation message
     */
    public String showTasksAdded(Task task, int taskCount) {
        return buildMessage(
                "Got it. I've added this task:",
                " " + task.toString(),
                MESSAGE_COUNT_PREFIX + taskCount + MESSAGE_COUNT_SUFFIX
        );
    }

    /**
     * Displays a task marked as done confirmation.
     *
     * @param task The task that was marked
     * @return The formatted confirmation message
     */
    public String showTaskMarked(Task task) {
        return buildMessage(
                "Nice! I've marked this task as done:",
                " " + task.toString()
        );
    }

    /**
     * Displays a task marked as not done confirmation.
     *
     * @param task The task that was unmarked
     * @return The formatted confirmation message
     */
    public String showTaskUnmarked(Task task) {
        return buildMessage(
                "OK, I've marked this task as not done yet:",
                " " + task.toString()
        );
    }

    /**
     * Displays a task deleted confirmation.
     *
     * @param task The task that was deleted
     * @param taskCount The new total number of tasks
     * @return The formatted confirmation message
     */
    public String showTaskDeleted(Task task, int taskCount) {
        return buildMessage(
                "Noted. I've removed this task:",
                " " + task.toString(),
                MESSAGE_COUNT_PREFIX + taskCount + MESSAGE_COUNT_SUFFIX
        );
    }

    /**
     * Displays a task updated confirmation.
     *
     * @param task The updated task
     * @return The formatted confirmation message
     */
    public String showTaskUpdated(Task task) {
        return buildMessage(
                "OK, I've updated this task:",
                " " + task.toString()
        );
    }

    /**
     * Displays search results for the find command.
     *
     * @param matchingTasks List of tasks matching the specified keyword.
     * @param keyword The search keyword.
     * @return The formatted find results
     */
    public String showFindResults(ArrayList<Task> matchingTasks, String keyword) {
        if (matchingTasks.isEmpty()) {
            return buildMessage("No tasks found containing: '" + keyword + "'");
        }

        ArrayList<String> lines = new ArrayList<>();
        lines.add("Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            lines.add((i + 1) + "." + matchingTasks.get(i));
        }

        return buildMessage(lines.toArray(new String[0]));
    }

    /**
     * Displays the help information.
     *
     * @return The formatted help information
     */
    public String showHelp() {
        return buildMessage(
                "Here's what I can help with:",
                "1. Add a task: todo <description>",
                "2. Add a deadline: deadline <task> /by yyyy-MM-dd HHmm",
                "3. Add an event: event <task> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm",
                "4. See all tasks: list",
                "5. Mark as done: mark <number>",
                "6. Mark as not done: unmark <number>",
                "7. Delete a task: delete <number>",
                "8. Find tasks: find <keyword>",
                "9. Update a task: update <number> <new description>",
                "10. Say goodbye: bye"
        );
    }

    /**
     * Displays unknown command message.
     *
     * @return The formatted unknown command message
     */
    public String showUnknownCommand() {
        return buildMessage(
                "Invalid command",
                HELP_PROMPT
        );
    }
}
