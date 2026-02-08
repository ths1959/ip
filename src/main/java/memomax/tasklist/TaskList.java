package memomax.tasklist;

import java.util.ArrayList;

import memomax.exception.MemoMaxException;
import memomax.task.Task;

/**
 * Manages a collection of tasks.
 * Provides operations to add, delete, mark, unmark, and search tasks.
 */
public class TaskList {
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
        assert tasks != null : "Source task list should not be null";
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        assert task != null : "Cannot add a null task to the list";
        int oldSize = tasks.size();
        tasks.add(task);
        assert tasks.size() == oldSize + 1 : "Task list size should increment by 1";
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
        assert index >= 0 && index < tasks.size() : "Index must be valid after validation";

        int oldSize = tasks.size();
        Task removedTask = tasks.remove(index);
        assert tasks.size() == oldSize - 1 : "Task list size should decrement by 1";

        return removedTask;
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
        assert index >= 0 && index < tasks.size() : "Index must be valid after validation";

        Task task = tasks.get(index);
        assert task != null : "Retrieved task should not be null";

        return task;
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task to mark (0-based).
     * @throws MemoMaxException If the index is invalid or task is already marked.
     */
    public void mark(int index) throws MemoMaxException {
        validateIndex(index);
        assert index >= 0 && index < tasks.size() : "Index must be valid after validation";

        Task task = tasks.get(index);
        assert task != null : "Task to mark should not be null";

        if (task.getStatusIcon().equals("[X]")) {
            throw new MemoMaxException("Task " + (index + 1)
                    + " is already marked as done!");
        }

        task.mark();
        assert task.getStatusIcon().equals("[X]") : "Task should be marked as done";
    }

    /**
     * Marks a task as not done.
     *
     * @param index The index of the task to unmark (0-based).
     * @throws MemoMaxException If the index is invalid or task is already unmarked.
     */
    public void unmark(int index) throws MemoMaxException {
        validateIndex(index);
        assert index >= 0 && index < tasks.size() : "Index must be valid after validation";

        Task task = tasks.get(index);
        assert task != null : "Task to unmark should not be null";

        if (task.getStatusIcon().equals("[ ]")) {
            throw new MemoMaxException("Task " + (index + 1)
                    + " is already not done!");
        }

        task.unmark();
        assert task.getStatusIcon().equals("[ ]") : "Task should be unmarked";
    }

    /**
     * Returns all tasks in the list.
     *
     * @return A copy of the task list.
     */
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> copy = new ArrayList<>(tasks);
        assert copy.size() == tasks.size() : "Copy size should match original size";
        return copy;
    }

    /**
     * Finds tasks whose description contains the specified keyword.
     *
     * @param keyword The search keyword.
     * @return List of matching tasks.
     */
    public ArrayList<Task> findTasks(String keyword) {
        assert keyword != null : "Search keyword should not be null";

        ArrayList<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        tasks.stream()
                .filter(task -> task.toString().toLowerCase().contains(lowerKeyword))
                .forEach(matchingTasks::add);

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
        if (index < 0 || index >= tasks.size()) {
            throw new MemoMaxException("Task " + (index + 1) + " does not exist!");
        }
    }
}
