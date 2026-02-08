package memomax.tasklist;

import java.util.ArrayList;

import memomax.exception.MemoMaxException;
import memomax.task.Task;

/**
 * Manages a collection of tasks.
 * Provides operations to add, delete, mark, unmark, and search tasks.
 */
public class TaskList {
    private static final String ICON_MARKED = "[X]";
    private static final String ICON_UNMARKED = "[ ]";

    private final ArrayList<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList with existing tasks.
     *
     * @param tasks The list of tasks to initialize with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the list.
     *
     * @param index The index of the task to remove (0-based).
     * @return The removed task.
     * @throws MemoMaxException If the index is invalid.
     */
    public Task delete(int index) throws MemoMaxException {
        validateIndex(index);
        return tasks.remove(index);
    }

    /**
     * Gets a task from the list.
     *
     * @param index The index of the task to get (0-based).
     * @return The task at the specified index.
     * @throws MemoMaxException If the index is invalid.
     */
    public Task get(int index) throws MemoMaxException {
        validateIndex(index);
        return tasks.get(index);
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task to mark (0-based).
     * @throws MemoMaxException If the index is invalid or task is already marked.
     */
    public void mark(int index) throws MemoMaxException {
        validateIndex(index);
        Task task = tasks.get(index);

        if (task.getStatusIcon().equals(ICON_MARKED)) {
            throw new MemoMaxException("Task " + (index + 1)
                    + " is already marked as done!");
        }

        task.mark();
    }

    /**
     * Marks a task as not done.
     *
     * @param index The index of the task to unmark (0-based).
     * @throws MemoMaxException If the index is invalid or task is already unmarked.
     */
    public void unmark(int index) throws MemoMaxException {
        validateIndex(index);
        Task task = tasks.get(index);

        if (task.getStatusIcon().equals(ICON_UNMARKED)) {
            throw new MemoMaxException("Task " + (index + 1)
                    + " is already not done!");
        }

        task.unmark();
    }

    /**
     * Returns all tasks in the list.
     *
     * @return A copy of the task list.
     */
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Finds tasks whose description contains the specified keyword.
     *
     * @param keyword The search keyword.
     * @return List of matching tasks.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Task task : tasks) {
            if (task.toString().toLowerCase().contains(lowerKeyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return True if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Validates that an index is within bounds.
     *
     * @param index The index to validate (0-based).
     * @throws MemoMaxException If the index is out of bounds.
     */
    private void validateIndex(int index) throws MemoMaxException {
        boolean isOutOfBounds = index < 0 || index >= tasks.size();
        if (isOutOfBounds) {
            throw new MemoMaxException("Task " + (index + 1) + " does not exist!");
        }
    }
}
