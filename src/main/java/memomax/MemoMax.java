package memomax;

import java.util.ArrayList;

import memomax.exception.MemoMaxException;
import memomax.parser.CommandType;
import memomax.parser.Parser;
import memomax.storage.Storage;
import memomax.task.Deadline;
import memomax.task.Event;
import memomax.task.Task;
import memomax.task.Todo;
import memomax.tasklist.TaskList;
import memomax.ui.Ui;

/**
 * Main chatbot class for MemoMax.
 * Handles user commands and manages task list.
 */
public class MemoMax {
    private static TaskList tasks = new TaskList();
    private static final Ui UI = new Ui();
    private static final Storage STORAGE = new Storage("./data/MemoMax.txt");

    /**
     * Main entry point for the chatbot.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        String welcomeMessage = UI.showWelcome();
        System.out.println(welcomeMessage);

        loadTasksFromFile();

        runChatbotLoop();

        String goodbyeMessage = UI.showGoodbye();
        System.out.println(goodbyeMessage);
    }

    /**
     * Returns the welcome message from the UI.
     * @return A string containing only the welcome text.
     */
    public String getGreeting() {
        return UI.showWelcome();
    }

    /**
     * Processes user input and returns MemoMax's response for the GUI.
     * This method acts as a bridge for Level-10 integration.
     *
     * @param input The raw user input string.
     * @return The response message from MemoMax.
     */
    public String getResponse(String input) {
        assert input != null : "Input string to getResponse should not be null";
        if (tasks.isEmpty()) {
            loadTasksFromFile();
        }

        String[] inputParts = input.split(" ");
        assert inputParts.length > 0 : "Input should contain at least one word";
        CommandType commandType = CommandType.parseCommand(inputParts[0]);

        switch (commandType) {
        case BYE:
            return UI.showGoodbye();
        case LIST:
            return handleList();
        case MARK:
            return handleMark(inputParts);
        case UNMARK:
            return handleUnmark(inputParts);
        case DELETE:
            return handleDelete(inputParts);
        case TODO:
            return handleTodo(input);
        case DEADLINE:
            return handleDeadline(input);
        case EVENT:
            return handleEvent(input);
        case HELP:
            return handleHelp(inputParts);
        case FIND:
            return handleFind(input);
        case UPDATE:
            return handleUpdate(input);
        default:
            return handleUnknownCommand();
        }
    }

    /**
     * Main chatbot loop that processes user commands.
     */
    private static void runChatbotLoop() {
        while (true) {
            String userInput = UI.readCommand();
            assert userInput != null : "UI readCommand should not return null";
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
            case FIND:
                handleFind(userInput);
                break;
            case UPDATE:
                handleUpdate(userInput);
                break;
            default:
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
            ArrayList<Task> loadedTasks = STORAGE.load();
            assert loadedTasks != null : "STORAGE.load() should return a list, even if empty";
            tasks = new TaskList(loadedTasks);
        } catch (MemoMaxException e) {
            String storageErrorMessage = UI.showStorageError("Failed to load saved tasks: " + e.getMessage());
            System.err.println(storageErrorMessage);
            tasks = new TaskList();
        }
    }

    /**
     * Saves tasks from memory to storage file.
     */
    private static void saveTasksToFile() {
        assert tasks != null : "Task list must exist to be saved";
        try {
            STORAGE.save(tasks.getAllTasks());
        } catch (MemoMaxException e) {
            String storageErrorMessage = UI.showStorageError("Failed to save tasks: " + e.getMessage());
            System.err.println(storageErrorMessage);
        }
    }

    /**
     * Displays all tasks in the list.
     */
    private static String handleList() {
        assert tasks != null : "Task list must be initialized to display";
        String listOutput = UI.showTaskList(tasks.getAllTasks(), tasks.isEmpty());
        System.out.println(listOutput);
        return listOutput;
    }

    /**
     * Marks a task as done.
     *
     * @param inputParts The split input parts
     */
    private static String handleMark(String[] inputParts) {
        assert inputParts != null && inputParts.length >= 1 : "Input parts must be valid";
        String response;
        try {
            int taskNumber = Parser.parseTaskNumber(inputParts, "mark");
            int index = taskNumber - 1;

            tasks.mark(index);
            response = UI.showTaskMarked(tasks.get(index));
            saveTasksToFile();
        } catch (MemoMaxException e) {
            response = UI.showErrorMessage(e.getMessage());
        }
        System.out.println(response);
        return response;
    }

    /**
     * Marks a task as not done.
     *
     * @param inputParts The split input parts
     */
    private static String handleUnmark(String[] inputParts) {
        assert inputParts != null && inputParts.length >= 1 : "Input parts must be valid";
        String response;
        try {
            int taskNumber = Parser.parseTaskNumber(inputParts, "unmark");
            int index = taskNumber - 1;

            tasks.unmark(index);
            response = UI.showTaskUnmarked(tasks.get(index));
            saveTasksToFile();
        } catch (MemoMaxException e) {
            response = UI.showErrorMessage(e.getMessage());
        }
        System.out.println(response);
        return response;
    }

    /**
     * Deletes a task from the list.
     *
     * @param inputParts The split input parts
     */
    private static String handleDelete(String[] inputParts) {
        assert inputParts != null && inputParts.length >= 1 : "Input parts must be valid";
        String response;
        try {
            int taskNumber = Parser.parseTaskNumber(inputParts, "delete");
            int index = taskNumber - 1;
            Task taskToRemove = tasks.delete(index);

            response = UI.showTaskDeleted(taskToRemove, tasks.size());
            saveTasksToFile();
        } catch (MemoMaxException e) {
            response = UI.showErrorMessage(e.getMessage());
        }
        System.out.println(response);
        return response;
    }

    /**
     * Finds tasks containing the search keyword.
     *
     * @param userInput The user input string.
     */
    private static String handleFind(String userInput) {
        assert userInput != null : "Find input should not be null";
        String response;
        try {
            String keyword = Parser.parseFind(userInput);
            ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
            response = UI.showFindResults(matchingTasks, keyword);
        } catch (MemoMaxException e) {
            response = UI.showErrorMessage(e.getMessage());
        }
        System.out.println(response);
        return response;
    }

    /**
     * Updates an existing task's description.
     *
     * @param userInput The raw user input string.
     */
    private static String handleUpdate(String userInput) {
        assert userInput != null : "Update input should not be null";
        String response;
        try {
            String[] parsed = Parser.parseUpdate(userInput);
            int index = Integer.parseInt(parsed[0]) - 1;
            String newDescription = parsed[1];

            Task oldTask = tasks.get(index);
            Task updatedTask = new Todo(newDescription);

            if (oldTask.getStatusIcon().equals("[X]")) {
                updatedTask.mark();
            }

            tasks.update(index, updatedTask);
            response = UI.showTaskUpdated(updatedTask);
            saveTasksToFile();
        } catch (MemoMaxException | NumberFormatException e) {
            response = UI.showErrorMessage(e.getMessage());
        }
        System.out.println(response);
        return response;
    }

    /**
     * Adds a new todo task.
     *
     * @param userInput The user input string
     */
    private static String handleTodo(String userInput) {
        assert userInput != null : "Todo input should not be null";
        String response;
        try {
            String description = Parser.parseTodo(userInput);
            Task newTask = new Todo(description);
            tasks.add(newTask);

            response = UI.showTasksAdded(newTask, tasks.size());
            saveTasksToFile();
        } catch (MemoMaxException e) {
            response = UI.showErrorMessage(e.getMessage());
        }
        System.out.println(response);
        return response;
    }

    /**
     * Adds a new deadline task.
     *
     * @param userInput The user input string
     */
    private static String handleDeadline(String userInput) {
        assert userInput != null : "Deadline input should not be null";
        String response;
        try {
            String[] parsed = Parser.parseDeadline(userInput);
            String taskDescription = parsed[0];
            String date = parsed[1];
            Task newTask = new Deadline(taskDescription, date);
            tasks.add(newTask);
            response = UI.showTasksAdded(newTask, tasks.size());
            saveTasksToFile();
        } catch (MemoMaxException e) {
            response = UI.showErrorMessage(e.getMessage());
        }
        System.out.println(response);
        return response;
    }

    /**
     * Adds a new event task.
     *
     * @param userInput The user input string
     */
    private static String handleEvent(String userInput) {
        assert userInput != null : "Event input should not be null";
        String response;
        try {
            String[] parsed = Parser.parseEvent(userInput);
            String event = parsed[0];
            String from = parsed[1];
            String to = parsed[2];

            Task newTask = new Event(event, from, to);
            tasks.add(newTask);
            response = UI.showTasksAdded(newTask, tasks.size());
            saveTasksToFile();
        } catch (MemoMaxException e) {
            response = UI.showErrorMessage(e.getMessage());
        }
        System.out.println(response);
        return response;
    }

    /**
     * Displays help information.
     *
     * @param inputParts The split input parts
     */
    private static String handleHelp(String[] inputParts) {
        assert inputParts != null : "Help input parts should not be null";
        String response;
        if (inputParts.length != 1) {
            response = UI.showUnknownCommand();
        } else {
            response = UI.showHelp();
        }
        System.out.println(response);
        return response;
    }

    /**
     * Handles unknown commands.
     */
    private static String handleUnknownCommand() {
        String response = UI.showUnknownCommand();
        System.out.println(response);
        return response;
    }
}