package memomax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import memomax.exception.MemoMaxException;

/**
 * Represents a deadline task with a due date.
 * Extends the Task with deadline-specific information.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    protected LocalDateTime by;


    /**
     * Creates a new deadline task.
     *
     * @param description Task description.
     * @param by Due date and time.
     * @throws MemoMaxException If the date format is invalid.
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
     * Returns a formatted deadline string with the due date.
     *
     * @return A string containing the "[D]" prefix, task details, and deadline.
     */
    @Override
    public String toString() {
        DateTimeFormatter outFmt = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[D]" + super.toString() + " (by: " + by.format(outFmt) + ")";
    }

    /**
     * Returns the Deadline data in a file storage format.
     *
     * @return A string representation formatted for file storage.
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description
                + " | " + by.format(INPUT_FORMAT);
    }
}
