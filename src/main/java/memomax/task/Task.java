package memomax.task;

/**
 * Represents a task in the MemoMax application.
 * Serves as the base class for different task types with common functionality.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description.
     *
     * @param description The description of the task.
     */
    Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as completed.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the status icon for display.
     *
     * @return A string "[X]" if done, or "[ ]" if not done.
     */
    public String getStatusIcon() {
        if (isDone) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    /**
     * Returns the string representation of the task.
     *
     * @return A formatted task string with status and description.
     */
    public String toString() {
        return getStatusIcon() + " " + this.description;
    }

    /**
     * Returns the task data in a file storage format.
     *
     * @return A string representation formatted for file storage.
     */
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
