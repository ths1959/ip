package memomax.exception;

import java.util.ArrayList;

import memomax.task.Task;

/**
 * Represents an exception specific to MemoMax chatbot errors.
 * Provides user-friendly error messages and can carry partial data.
 */
public class MemoMaxException extends Exception {
    private ArrayList<Task> partialTasks = new ArrayList<>();

    /**
     * Creates a MemoMaxException with given error message.
     *
     * @param message Error description for user
     */
    public MemoMaxException(String message) {
        super(message);
    }

    /**
     * Creates a MemoMaxException that carries partially loaded tasks.
     *
     * @param message Error description for user.
     * @param tasks The list of tasks successfully loaded before the error.
     */
    public MemoMaxException(String message, ArrayList<Task> tasks) {
        super(message);
        this.partialTasks = tasks;
    }

    /**
     * Returns the tasks that were successfully loaded before the exception occurred.
     *
     * @return An ArrayList of tasks.
     */
    public ArrayList<Task> getPartialTasks() {
        return partialTasks;
    }
}
