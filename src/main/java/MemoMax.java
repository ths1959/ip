import java.util.ArrayList;

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
                    handleTodo(userInput);
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
            int taskNumber = Parser.parseTaskNumber(inputParts, "mark");
            Parser.validateListNotEmpty(tasks.size());
            Parser.validateTaskExists(taskNumber, tasks.size());
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
            int taskNumber = Parser.parseTaskNumber(inputParts, "unmark");
            Parser.validateListNotEmpty(tasks.size());
            Parser.validateTaskExists(taskNumber, tasks.size());
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
            int taskNumber = Parser.parseTaskNumber(inputParts, "delete");
            Parser.validateListNotEmpty(tasks.size());
            Parser.validateTaskExists(taskNumber, tasks.size());

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
     */
    private static void handleTodo(String userInput) {
        try {
            String description = Parser.parseTodo(userInput);
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
            String[] parsed = Parser.parseDeadline(userInput);
            String action = parsed[0];
            String date = parsed[1];
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
            String[] parsed = Parser.parseEvent(userInput);
            String event = parsed[0];
            String from = parsed[1];
            String to = parsed[2];

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