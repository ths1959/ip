package memomax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import memomax.exception.MemoMaxException;

/**
 * Represents an event task with start and end times.
 * Extends the Task class with time interval information.
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Creates a new event task.
     *
     * @param description Event description.
     * @param from Start time.
     * @param to End time.
     * @throws MemoMaxException If the date format is invalid.
     */
    public Event(String description, String from, String to) throws MemoMaxException {
        super(description);
        assert from != null && !from.trim().isEmpty() : "Event 'from' string should not be null or empty";
        assert to != null && !to.trim().isEmpty() : "Event 'to' string should not be null or empty";

        try {
            this.from = LocalDateTime.parse(from.trim(), INPUT_FORMAT);
            this.to = LocalDateTime.parse(to.trim(), INPUT_FORMAT);
        } catch (Exception e) {
            throw new MemoMaxException("Invalid date! Use: yyyy-MM-dd HHmm");
        }
    }

    /**
     * Gets the start time of the event in input format.
     *
     * @return The formatted start time string.
     */
    public String getFrom() {
        assert from != null : "Event start time should not be null when calling getFrom";
        return from.format(INPUT_FORMAT);
    }

    /**
     * Gets the end time of the event in input format.
     *
     * @return The formatted end time string.
     */
    public String getTo() {
        assert to != null : "Event end time should not be null when calling getTo";
        return to.format(INPUT_FORMAT);
    }

    /**
     * Returns a formatted event string with time interval.
     *
     * @return A string with the "[E]" prefix, task details, and time interval.
     */
    @Override
    public String toString() {
        assert from != null && to != null : "Event times should not be null during toString conversion";
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[E]" + super.toString() + " (from: " + from.format(outputFormatter)
                + " to: " + to.format(outputFormatter) + ")";
    }

    /**
     * Returns event data in file storage format.
     *
     * @return A string representation formatted for file storage.
     */
    @Override
    public String toFileFormat() {
        assert from != null && to != null : "Event times should not be null during file format conversion";
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
    }
}
