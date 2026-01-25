import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with start and end times.
 * Extends Task with time interval information.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Creates a new event task.
     *
     * @param description Event description
     * @param from Start time
     * @param to End time
     */
    public Event(String description, String from, String to) throws MemoMaxException {
        super(description);
        try {
            this.from = LocalDateTime.parse(from.trim(), INPUT_FORMAT);
            this.to = LocalDateTime.parse(to.trim(), INPUT_FORMAT);
        } catch (Exception e) {
            throw new MemoMaxException("Invalid date! Use: yyyy-MM-dd HHmm");
        }
    }

    /**
     * Returns formatted event string with time interval.
     *
     * @return "[E]" prefix with task details and time interval
     */
    @Override
    public String toString() {
        DateTimeFormatter outFmt = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[E]" + super.toString() + " (from: " + from.format(outFmt) +
                " to: " + to.format(outFmt) + ")";
    }

    /**
     * Returns Event data in file storage format.
     *
     * @return string representation for file storage
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " +
                from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
    }
}
