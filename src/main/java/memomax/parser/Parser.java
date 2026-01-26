package memomax.parser;

import memomax.exception.MemoMaxException;

/**
 * Parses user input into commands and parameters.
 * Handles extraction and validation of command arguments.
 */
public class Parser {

    /**
     * Parses a todo command and extracts the description.
     *
     * @param userInput The full user input string
     * @return The todo description
     * @throws MemoMaxException if description is empty
     */
    public static String parseTodo(String userInput) throws MemoMaxException {
        if (userInput.trim().equals("todo")) {
            throw new MemoMaxException("Todo needs a description. Example: todo read book");
        }
        String description = userInput.substring("todo ".length()).trim();
        if (description.isEmpty()) {
            throw new MemoMaxException("Todo description cannot be empty. Example: todo read book");
        }
        return description;
    }

    /**
     * Parses a mark/unmark/delete command and extracts the task number.
     *
     * @param inputParts The split input parts
     * @param command The command type ("mark", "unmark", or "delete")
     * @return The parsed task number (1-based)
     * @throws MemoMaxException if number is missing or invalid
     */
    public static int parseTaskNumber(String[] inputParts, String command) throws MemoMaxException {
        if (inputParts.length < 2) {
            throw new MemoMaxException("Please tell me which task number." +
                    " Example: " + command + " 1");
        }

        try {
            int taskNumber = Integer.parseInt(inputParts[1]);
            if (taskNumber <= 0) {
                throw new MemoMaxException("Task number must be positive. " +
                        "Example: " + command + " 1");
            }
            return taskNumber;
        } catch (NumberFormatException e) {
            throw new MemoMaxException("'" + inputParts[1] + "' is not a number. " +
                    "Please use a number like 1, 2, or 3.");
        }
    }

    /**
     * Parses a deadline command into description and due date.
     *
     * @param userInput The full user input string
     * @return String array [description, dueDate]
     * @throws MemoMaxException if format is invalid
     */
    public static String[] parseDeadline(String userInput) throws MemoMaxException {
        if (userInput.trim().equals("deadline")) {
            throw new MemoMaxException("Deadline needs a description and a due date. " +
                    "Example: deadline return book /by 2025-02-01 1800");
        }

        if (!userInput.contains("/by")) {
            throw new MemoMaxException("Deadline needs a due date. " +
                    "Example: deadline return book /by 2026-02-14 1800");
        }

        String[] actionDate = userInput.split("/by", -1);
        if (actionDate.length < 2) {
            throw new MemoMaxException("Please add a due date after '/by'. " +
                    "Example: deadline return book /by 2026-02-14 1800");
        }

        String action = actionDate[0].substring("deadline ".length()).trim();
        String date = actionDate[1].trim();

        if (action.isEmpty()) {
            throw new MemoMaxException("Task not specified. " +
                    "Example: deadline return book /by 2026-02-14 1800");
        }

        if (date.isEmpty()) {
            throw new MemoMaxException("Due date is not specified. " +
                    "Example: deadline return book /by 2026-02-14 1800");
        }

        return new String[]{action, date};
    }

    /**
     * Parses an event command into description, start time, and end time.
     *
     * @param userInput The full user input string
     * @return String array [description, fromTime, toTime]
     * @throws MemoMaxException if format is invalid
     */
    public static String[] parseEvent(String userInput) throws MemoMaxException {
        boolean hasFrom = userInput.contains("/from");
        boolean hasTo = userInput.contains("/to");

        if (!hasFrom && !hasTo) {
            throw new MemoMaxException("Event needs description, start, " +
                    "and end times. " + "Example: event meeting " +
                    "/from 2026-02-14 1400 /to 2026-02-14 1600");
        }
        if (!hasFrom) {
            throw new MemoMaxException("Start time not specified, " +
                    "add '/from' time. " + "Example: event meeting " +
                    "/from 2026-02-14 1400 /to 2026-02-14 1600");
        }

        String[] eventDate = userInput.split("/from", -1);
        String event = eventDate[0].substring("event ".length()).trim();

        if (eventDate.length < 2) {
            throw new MemoMaxException("Start time not specified. " +
                    "Example: event meeting /from 2026-02-14 1400 /to 2026-02-14 1600");
        }

        if (!hasTo) {
            throw new MemoMaxException("End time not specified, add '/to' time. " +
                    "Example: event meeting /from 2026-02-14 1400 /to 2026-02-14 1600");
        }

        String[] fromTo = eventDate[1].split("/to", -1);
        if (fromTo.length < 2) {
            throw new MemoMaxException("End time not specified. " +
                    "Example: event meeting /from 2026-02-14 1400 /to 2026-02-14 1600");
        }

        String from = fromTo[0].trim();
        String to = fromTo[1].trim();

        if (event.isEmpty()) {
            throw new MemoMaxException("Event not specified. " +
                    "Example: event meeting /from 2026-02-14 14:00 /to 2026-02-14 16:00");
        }
        if (from.isEmpty()) {
            throw new MemoMaxException("Start time not specified. " +
                    "Example: event meeting /from 2026-02-14 1400 /to 2026-02-14 1600");
        }
        if (to.isEmpty()) {
            throw new MemoMaxException("End time not specified. " +
                    "Example: event meeting /from 2026-02-14 1400 /to 2026-02-14 1600");
        }

        return new String[]{event, from, to};
    }
}
