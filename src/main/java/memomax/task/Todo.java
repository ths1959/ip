package memomax.task;

/**
 * Represents a todo task.
 * Extends the Task with specific todo formatting.
 */
public class Todo extends Task {

    /**
     * Creates a new todo task.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the formatted todo string.
     *
     * @return A string containing the "[T]" prefix and task details.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the todo data in a file storage format.
     *
     * @return A string representation formatted for file storage
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat();
    }
}
