import java.util.ArrayList;
import java.util.Scanner;

/*
 Note: ChatGPT was consulted for adherence towards Javadoc documentation standards.
 Core logic, structure, and error messages remain my own implementation.
*/

/**
 * Main chatbot class for MemoMax.
 * Handles user commands and manages task list.
 */
public class MemoMax {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage("./data/MemoMax.txt");

    /**
     * Main entry point for the chatbot.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        String logo = " __  __  _____  __  __   ___   __  __     _    __  __\n"
                + "|  \\/  || ____||  \\/  | / _ \\ |  \\/  |   / \\   \\ \\/ /\n"
                + "| |\\/| ||  _|  | |\\/| || | | || |\\/| |  / _ \\   \\  / \n"
                + "| |  | || |___ | |  | || |_| || |  | | / ___ \\  /  \\ \n"
                + "|_|  |_||_____||_|  |_| \\___/ |_|  |_|/_/   \\_\\/_/\\_\\\n";
        ui.showWelcome(logo);

        loadTasksFromFile();

        runChatbotLoop();

        ui.showGoodbye();
    }

    /**
     * Main chatbot loop that processes user commands.
     */
    private static void runChatbotLoop() {
        while (true) {
            String userInput = ui.readCommand();
            String[] inputParts = userInput.split(" ");
            CommandType commandType = CommandType.parseCommand(inputParts[0]);

            switch (commandType) {
                case BYE:
                    break;
                case LIST:
                    handleList();
                    break;
                case MARK:
                    handleMark(inputParts);
                    break;
                case UNMARK:
                    handleUnmark(inputParts);
                    break;
                case DELETE:
                    handleDelete(inputParts);
                    break;
                case TODO:
                    handleTodo(userInput, inputParts);
                    break;
                case DEADLINE:
                    handleDeadline(userInput);
                    break;
                case EVENT:
                    handleEvent(userInput);
                    break;
                case HELP:
                    handleHelp(inputParts);
                    break;
                case UNKNOWN:
                    handleUnknownCommand();
                    break;
            }

            if (commandType == CommandType.BYE) {
                break;
            }
        }
    }

    /**
     * Loads tasks from storage file into memory.
     */
    private static void loadTasksFromFile() {
        try {
            tasks = storage.load();
        } catch (MemoMaxException e) {
            ui.showStorageError("Failed to load saved tasks: " + e.getMessage());
            tasks = new ArrayList<>();
        }
    }

    /**
     * Saves tasks from memory to storage file.
     */
    private static void saveTasksToFile() {
        try {
            storage.save(tasks);
        } catch (MemoMaxException e) {
            ui.showStorageError("Failed to save tasks: " + e.getMessage());
        }
    }

    /**
     * Displays all tasks in the list.
     */
    private static void handleList() {
        ui.showTaskList(tasks, tasks.isEmpty());
    }

    /**
     * Marks a task as done.
     *
     * @param inputParts The split input parts
     */
    private static void handleMark(String[] inputParts) {
        try {
            validateTaskNumber(inputParts);
            int taskNumber = parseTaskNumber(inputParts[1]);
            validateListNotEmpty();
            validateTaskExists(taskNumber);
            int index = taskNumber - 1;
            validateNotAlreadyMarked(index);

            tasks.get(index).mark();
            ui.showTaskMarked(tasks.get(index));
        } catch (MemoMaxException e) {
            ui.showErrorMessage(e.getMessage());
        }

        saveTasksToFile();
    }

    /**
     * Marks a task as not done.
     *
     * @param inputParts The split input parts
     */
    private static void handleUnmark(String[] inputParts) {
        try {
            validateTaskNumber(inputParts);
            int taskNumber = parseTaskNumber(inputParts[1]);
            validateListNotEmpty();
            validateTaskExists(taskNumber);
            int index = taskNumber - 1;
            validateNotAlreadyUnmarked(index);

            tasks.get(index).unmark();
            ui.showTaskUnmarked(tasks.get(index));
        } catch (MemoMaxException e) {
            ui.showErrorMessage(e.getMessage());
        }

        saveTasksToFile();
    }

    /**
     * Deletes a task from the list.
     *
     * @param inputParts The split input parts
     */
    private static void handleDelete(String[] inputParts) {
        try {
            validateTaskNumber(inputParts);
            int taskNumber = parseTaskNumber(inputParts[1]);
            validateListNotEmpty();
            validateTaskExists(taskNumber);

            int index = taskNumber - 1;
            Task taskToRemove = tasks.get(index);
            tasks.remove(index);

            ui.showTaskDeleted(taskToRemove, tasks.size());
        } catch (MemoMaxException e) {
            ui.showErrorMessage(e.getMessage());
        }

        saveTasksToFile();
    }

    /**
     * Adds a new todo task.
     *
     * @param userInput The user input string
     * @param inputParts The split input parts
     */
    private static void handleTodo(String userInput, String[] inputParts) {
        try {
            if (inputParts.length == 1) {
                throw new MemoMaxException("Todo needs a description. Example: todo read book");
            }
            String description = userInput.substring("todo ".length()).trim();
            Task newTask = new Todo(description);
            tasks.add(newTask);

            ui.showTasksAdded(newTask, tasks.size());
        } catch (MemoMaxException e) {
            ui.showErrorMessage(e.getMessage());
        }

        saveTasksToFile();
    }

    /**
     * Adds a new deadline task.
     *
     * @param userInput The user input string
     */
    private static void handleDeadline(String userInput) {
        try {
            if (userInput.trim().equals("deadline")) {
                throw new MemoMaxException("Deadline needs a description and a due date. " +
                        "Example: deadline return book /by 2025-02-01 1800");
            }

            if (!userInput.contains("/by")) {
                throw new MemoMaxException("Deadline needs a due date. " +
                        "Example: deadline return book /by 2026-02-14 1800");
            }

            String[] actionDate = userInput.split("/by", -1);
            if (actionDate.length < 2) {
                throw new MemoMaxException("Please add a due date after '/by'. " +
                        "Example: deadline return book /by 2026-02-14 1800");
            }

            String action = actionDate[0].substring("deadline ".length()).trim();
            String date = actionDate[1].trim();

            if (action.isEmpty()) {
                throw new MemoMaxException("Task not specified. " +
                        "Example: deadline return book /by 2026-02-14 1800");
            }
            if (date.isEmpty()) {
                throw new MemoMaxException("Due date is not specified. " +
                        "Example: deadline return book /by 2026-02-14 1800");
            }

            Task newTask = new Deadline(action, date);
            tasks.add(newTask);
            ui.showTasksAdded(newTask, tasks.size());
        } catch (MemoMaxException e) {
            ui.showErrorMessage(e.getMessage());
        }

        saveTasksToFile();
    }

    /**
     * Adds a new event task.
     *
     * @param userInput The user input string
     */
    private static void handleEvent(String userInput) {
        try {
            boolean hasFrom = userInput.contains("/from");
            boolean hasTo = userInput.contains("/to");

            if (!hasFrom && !hasTo) {
                throw new MemoMaxException("Event needs description, start, " +
                        "and end times. " + "Example: event meeting " +
                        "/from 2026-02-14 1400 /to 2026-02-14 1600");
            }
            if (!hasFrom) {
                throw new MemoMaxException("Start time not specified, " +
                        "add '/from' time. " + "Example: event meeting " +
                        "/from 2026-02-14 1400 /to 2026-02-14 1600");
            }

            String[] eventDate = userInput.split("/from", -1);
            String event = eventDate[0].substring("event ".length()).trim();

            if (eventDate.length < 2) {
                throw new MemoMaxException("Start time not specified. " +
                        "Example: event meeting /from 2026-02-14 1400 /to 2026-02-14 1600");
            }

            if (!hasTo) {
                throw new MemoMaxException("End time not specified, add '/to' time. " +
                        "Example: event meeting /from 2026-02-14 1400 /to 2026-02-14 1600");
            }

            String[] fromTo = eventDate[1].split("/to", -1);
            if (fromTo.length < 2) {
                throw new MemoMaxException("End time not specified. " +
                        "Example: event meeting /from 2026-02-14 1400 /to 2026-02-14 1600");
            }

            String from = fromTo[0].trim();
            String to = fromTo[1].trim();

            if (event.isEmpty()) {
                throw new MemoMaxException("Event not specified. " +
                        "Example: event meeting /from 2026-02-14 14:00 /to 2026-02-14 16:00");
            }
            if (from.isEmpty()) {
                throw new MemoMaxException("Start time not specified. " +
                        "Example: event meeting /from 2026-02-14 1400 /to 2026-02-14 1600");
            }
            if (to.isEmpty()) {
                throw new MemoMaxException("End time not specified. " +
                        "Example: event meeting /from 2026-02-14 1400 /to 2026-02-14 1600");
            }

            Task newTask = new Event(event, from, to);
            tasks.add(newTask);
            ui.showTasksAdded(newTask, tasks.size());
        } catch (MemoMaxException e) {
            ui.showErrorMessage(e.getMessage());
        }

        saveTasksToFile();
    }

    /**
     * Displays help information.
     *
     * @param inputParts The split input parts
     */
    private static void handleHelp(String[] inputParts) {
        if (inputParts.length != 1) {
            ui.showUnknownCommand();
        } else {
            ui.showHelp();
        }
    }

    /**
     * Handles unknown commands.
     */
    private static void handleUnknownCommand() {
        ui.showUnknownCommand();
    }

    // Helper validation methods

    /**
     * Validates that a task number is specified.
     *
     * @param inputParts The split input parts
     * @throws MemoMaxException if task number is not specified
     */
    private static void validateTaskNumber(String[] inputParts) throws MemoMaxException {
        if (inputParts.length < 2) {
            if (inputParts[0].equals("mark")) {
                throw new MemoMaxException("Please tell me which task number. Example: mark 1");
            }
            if (inputParts[0].equals("unmark")) {
                throw new MemoMaxException("Please tell me which task number. Example: unmark 1");
            }
            if (inputParts[0].equals("delete")) {
                throw new MemoMaxException("Please tell me which task number. Example: delete 1");
            }
        }
    }

    /**
     * Parses a task number from string.
     *
     * @param numberStr The string containing the task number
     * @return The parsed task number
     * @throws MemoMaxException if the string is not a valid number
     */
    private static int parseTaskNumber(String numberStr) throws MemoMaxException {
        try {
            return Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            throw new MemoMaxException("'" + numberStr + "' is not a number. " +
                    "Please use a number like 1, 2, or 3.");
        }
    }

    /**
     * Validates that a task exists at the given number.
     *
     * @param taskNumber The task number to validate
     * @throws MemoMaxException if the task does not exist
     */
    private static void validateTaskExists(int taskNumber) throws MemoMaxException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new MemoMaxException("Task " + taskNumber + " doesn't exist. " +
                    "You have " + tasks.size() + " task(s) in your list.");
        }
    }

    /**
     * Validates that the task list is not empty.
     *
     * @throws MemoMaxException if the task list is empty
     */
    private static void validateListNotEmpty() throws MemoMaxException {
        if (tasks.isEmpty()) {
            throw new MemoMaxException("Your list is empty! Add some tasks first " +
                    "using 'todo', 'deadline', or 'event'.");
        }
    }

    /**
     * Validates that a task is not already marked as done.
     *
     * @param index The index of the task to check
     * @throws MemoMaxException if the task is already marked
     */
    private static void validateNotAlreadyMarked(int index) throws MemoMaxException {
        if (tasks.get(index).getStatusIcon().equals("[X]")) {
            throw new MemoMaxException("Task " + (index + 1) + " is already marked as done!");
        }
    }

    /**
     * Validates that a task is not already marked as not done.
     *
     * @param index The index of the task to check
     * @throws MemoMaxException if the task is already unmarked
     */
    private static void validateNotAlreadyUnmarked(int index) throws MemoMaxException {
        if (tasks.get(index).getStatusIcon().equals("[ ]")) {
            throw new MemoMaxException("Task " + (index + 1) + " is already not done!");
        }
    }
}