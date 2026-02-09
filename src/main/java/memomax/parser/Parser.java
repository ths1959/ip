package memomax.parser;

import memomax.exception.MemoMaxException;

/**
 * Represents a parser that converts user input into commands and parameters.
 * Handles extraction and validation of command arguments.
 */
public class Parser {

    private static final String DELIMITER_BY = "/by";
    private static final String DELIMITER_FROM = "/from";
    private static final String DELIMITER_TO = "/to";

    private static final String PREFIX_TODO = "todo ";
    private static final String PREFIX_FIND = "find ";
    private static final String PREFIX_DEADLINE = "deadline ";
    private static final String PREFIX_EVENT = "event ";
    private static final String PREFIX_UPDATE = "update ";

    /**
     * Parses a todo command and extracts the description.
     *
     * @param userInput The full user input string.
     * @return The todo description.
     * @throws MemoMaxException If the description is empty.
     */
    public static String parseTodo(String userInput) throws MemoMaxException {
        assert userInput != null : "User input should not be null";
        assert userInput.toLowerCase().startsWith("todo") : "parseTodo called for non-todo input";

        if (userInput.trim().equalsIgnoreCase("todo")) {
            throw new MemoMaxException("Todo needs a description. Example: todo read book");
        }

        String description = userInput.substring(PREFIX_TODO.length()).trim();
        if (description.isEmpty()) {
            throw new MemoMaxException("Todo needs a description. Example: todo read book");
        }
        return description;
    }

    /**
     * Parses a mark, unmark, or delete command and extracts the task number.
     *
     * @param inputParts The split input parts.
     * @param command The command type ("mark", "unmark", or "delete").
     * @return The parsed task number (1-based).
     * @throws MemoMaxException If the number is missing or invalid.
     */
    public static int parseTaskNumber(String[] inputParts, String command) throws MemoMaxException {
        assert inputParts != null : "Input parts array should not be null";
        assert inputParts.length > 0 : "Input parts array should not be empty";
        assert inputParts[0].equals(command) : "parseTaskNumber called with mismatched command word";

        if (inputParts.length < 2) {
            throw new MemoMaxException("Please tell me which task number."
                    + " Example: " + command + " 1");
        }

        try {
            int taskNumber = Integer.parseInt(inputParts[1]);
            if (taskNumber <= 0) {
                throw new MemoMaxException("Task number must be positive. "
                        + "Example: " + command + " 1");
            }
            return taskNumber;
        } catch (NumberFormatException e) {
            throw new MemoMaxException("'" + inputParts[1] + "' is not a number. "
                    + "Please use a number like 1, 2, or 3.");
        }
    }

    /**
     * Parses a find command and extracts the keyword.
     *
     * @param userInput The full user input string.
     * @return The search keyword.
     * @throws MemoMaxException If the keyword is empty.
     */
    public static String parseFind(String userInput) throws MemoMaxException {
        assert userInput != null : "User input should not be null";
        assert userInput.toLowerCase().startsWith("find") : "parseFind called for non-find input";

        if (userInput.trim().equalsIgnoreCase("find")) {
            throw new MemoMaxException("Find needs a keyword. Example: find book");
        }

        String keyword = userInput.substring(PREFIX_FIND.length()).trim();
        if (keyword.isEmpty()) {
            throw new MemoMaxException("Find keyword cannot be empty. Example: find book");
        }

        return keyword;
    }

    /**
     * Parses a deadline command into description and due date.
     *
     * @param userInput The full user input string.
     * @return A String array containing [description, dueDate].
     * @throws MemoMaxException If the format is invalid.
     */
    public static String[] parseDeadline(String userInput) throws MemoMaxException {
        assert userInput != null : "User input should not be null";
        assert userInput.toLowerCase().startsWith("deadline") : "parseDeadline called for non-deadline input";

        if (userInput.trim().equalsIgnoreCase("deadline")) {
            throw new MemoMaxException("Deadline needs a description and a due date. "
                    + "Example: deadline return book " + DELIMITER_BY + " 2025-02-01 1800");
        }

        if (!userInput.contains(DELIMITER_BY)) {
            throw new MemoMaxException("Deadline needs a due date. "
                    + "Example: deadline return book " + DELIMITER_BY + " 2026-02-14 1800");
        }

        String[] actionDate = userInput.split(DELIMITER_BY, -1);
        if (actionDate.length < 2) {
            throw new MemoMaxException("Please add a due date after '" + DELIMITER_BY + "'. "
                    + "Example: deadline return book " + DELIMITER_BY + " 2026-02-14 1800");
        }

        String action = actionDate[0].substring(PREFIX_DEADLINE.length()).trim();
        String date = actionDate[1].trim();

        if (action.isEmpty()) {
            throw new MemoMaxException("Task not specified. "
                    + "Example: deadline return book " + DELIMITER_BY + " 2026-02-14 1800");
        }

        if (date.isEmpty()) {
            throw new MemoMaxException("Due date is not specified. "
                    + "Example: deadline return book " + DELIMITER_BY + " 2026-02-14 1800");
        }

        return new String[]{action, date};
    }

    /**
     * Parses an event command into description, start time, and end time.
     *
     * @param userInput The full user input string.
     * @return A String array containing [description, fromTime, toTime].
     * @throws MemoMaxException If the format is invalid.
     */
    public static String[] parseEvent(String userInput) throws MemoMaxException {
        assert userInput != null : "User input should not be null";
        assert userInput.toLowerCase().startsWith("event") : "parseEvent called for non-event input";

        int fromIndex = userInput.indexOf(DELIMITER_FROM);
        int toIndex = getToIndex(userInput, fromIndex);

        String event = userInput.substring(PREFIX_EVENT.length(), fromIndex).trim();
        String from = userInput.substring(fromIndex + DELIMITER_FROM.length(), toIndex).trim();
        String to = userInput.substring(toIndex + DELIMITER_TO.length()).trim();

        if (event.isEmpty()) {
            throw new MemoMaxException("Event not specified. "
                    + "Example: event meeting " + DELIMITER_FROM + " 2026-02-14 1400 "
                    + DELIMITER_TO + " 2026-02-14 1600");
        }
        if (from.isEmpty()) {
            throw new MemoMaxException("Start time not specified. "
                    + "Example: event meeting " + DELIMITER_FROM + " 2026-02-14 1400 "
                    + DELIMITER_TO + " 2026-02-14 1600");
        }
        if (to.isEmpty()) {
            throw new MemoMaxException("End time not specified. "
                    + "Example: event meeting " + DELIMITER_FROM + " 2026-02-14 1400 "
                    + DELIMITER_TO + " 2026-02-14 1600");
        }

        return new String[]{event, from, to};
    }

    private static int getToIndex(String userInput, int fromIndex) throws MemoMaxException {
        int toIndex = userInput.indexOf(DELIMITER_TO);

        if (fromIndex == -1) {
            throw new MemoMaxException("Start time not specified. "
                    + "Example: event meeting " + DELIMITER_FROM + " 2026-02-14 1400 "
                    + DELIMITER_TO + " 2026-02-14 1600");
        }
        if (toIndex == -1) {
            throw new MemoMaxException("End time not specified. "
                    + "Example: event meeting " + DELIMITER_FROM + " 2026-02-14 1400 "
                    + DELIMITER_TO + " 2026-02-14 1600");
        }
        return toIndex;
    }

    /**
     * Parses an update command to extract the task index and the new description.
     *
     * @param userInput The full user input string.
     * @return A String array containing [taskNumber, newDescription].
     * @throws MemoMaxException If the format is invalid or parts are missing.
     */
    public static String[] parseUpdate(String userInput) throws MemoMaxException {
        assert userInput != null : "User input should not be null";
        assert userInput.toLowerCase().startsWith("update") : "parseUpdate called for non-update input";

        if (userInput.trim().equalsIgnoreCase("update")) {
            throw new MemoMaxException("Update needs a task number and new description. "
                    + "Example: update 1 new description");
        }

        String content = userInput.substring(PREFIX_UPDATE.length()).trim();
        String[] parts = content.split(" ", 2);

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MemoMaxException("Please provide both a task number and the new description. "
                    + "Example: update 1 new description");
        }

        return new String[]{parts[0].trim(), parts[1].trim()};
    }
}
