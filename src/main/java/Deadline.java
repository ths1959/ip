import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with due date.
 * Extends Task with deadline information.
 */
public class Deadline extends Task{
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Creates a new deadline task.
     *
     * @param description Task description
     * @param by Due date/time
     */
    public Deadline(String description, String by) throws MemoMaxException {
        super(description);
        try {
            this.by = LocalDateTime.parse(by.trim(), INPUT_FORMAT);
        } catch (Exception e) {
            throw new MemoMaxException("Invalid date! Use: yyyy-MM-dd HHmm");
        }
    }

    /**
     * Returns formatted deadline string with due date.
     *
     * @return "[D]" prefix with task details and deadline
     */
    @Override
    public String toString() {
        DateTimeFormatter outFmt = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[D]" + super.toString() + " (by: " + by.format(outFmt) + ")";
    }

    /**
     * Returns Deadline data in file storage format.
     *
     * @return string representation for file storage
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description +
                " | " + by.format(INPUT_FORMAT);
    }
}
