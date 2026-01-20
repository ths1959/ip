/**
 * Represents an event task with start and end times.
 * Extends Task with time interval information.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates a new event task.
     *
     * @param description Event description
     * @param from Start time
     * @param to End time
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns formatted event string with time interval.
     *
     * @return "[E]" prefix with task details and time interval
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
