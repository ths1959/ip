/**
 * Represents a todo task.
 * Extends Task with specific todo formatting.
 */
public class Todo extends Task {

    /**
     * Creates a new todo task.
     *
     * @param description Todo description
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns formatted todo string.
     *
     * @return "[T]" prefix with task details
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
