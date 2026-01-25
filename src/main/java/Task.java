/**
 * Represents a task in the MemoMax application.
 * Base class for different task types with common functionality.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with given description.
     *
     * @param description Task description
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
     * @return "[X]" if done, "[ ]" if not done
     */
    public String getStatusIcon() {
        if (isDone) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    /**
     * Returns string representation of the task.
     *
     * @return Formatted task string with status and description
     */
    public String toString() {
        return getStatusIcon() + " " + this.description;
    }

    /**
     * Returns task data in file storage format.
     *
     * @return string representation for file storage
     */
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
