package memomax;

import memomax.exception.MemoMaxException;
import memomax.parser.Parser;
import memomax.parser.CommandType;
import memomax.storage.Storage;
import memomax.tasklist.TaskList;
import memomax.task.Task;
import memomax.task.Todo;
import memomax.task.Deadline;
import memomax.task.Event;
import memomax.ui.Ui;
import java.util.ArrayList;

/**
 * Main chatbot class for MemoMax.
 * Handles user commands and manages task list.
 */
public class MemoMax {
    private static TaskList tasks = new TaskList();
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
            ArrayList<Task> loadedTasks = storage.load();
            tasks = new TaskList(loadedTasks);
        } catch (MemoMaxException e) {
            ui.showStorageError("Failed to load saved tasks: " + e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Saves tasks from memory to storage file.
     */
    private static void saveTasksToFile() {
        try {
            storage.save(tasks.getAllTasks());
        } catch (MemoMaxException e) {
            ui.showStorageError("Failed to save tasks: " + e.getMessage());
        }
    }

    /**
     * Displays all tasks in the list.
     */
    private static void handleList() {
        ui.showTaskList(tasks.getAllTasks(), tasks.isEmpty());
    }

    /**
     * Marks a task as done.
     *
     * @param inputParts The split input parts
     */
    private static void handleMark(String[] inputParts) {
        try {
            int taskNumber = Parser.parseTaskNumber(inputParts, "mark");
            int index = taskNumber - 1;

            tasks.mark(index);
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
            int index = taskNumber - 1;

            tasks.unmark(index);
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
            int index = taskNumber - 1;
            Task taskToRemove = tasks.delete(index);

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
}