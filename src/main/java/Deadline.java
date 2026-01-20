/**
 * Represents a deadline task with due date.
 * Extends Task with deadline information.
 */
public class Deadline extends Task{
    protected String by;

    /**
     * Creates a new deadline task.
     *
     * @param description Task description
     * @param by Due date/time
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns formatted deadline string with due date.
     *
     * @return "[D]" prefix with task details and deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
