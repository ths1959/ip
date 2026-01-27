package memomax.parser;

/**
 * Command types supported by MemoMax.
 */
public enum CommandType {
    BYE, LIST, MARK, UNMARK, DELETE,
    TODO, DEADLINE, EVENT, HELP, UNKNOWN;

    /**
     * Parses a string input into its corresponding CommandType.
     *
     * @param command The raw command string from user input
     * @return The matching CommandType, or UNKNOWN if no match is found
     */
    public static CommandType parseCommand(String command) {
        if (command == null || command.trim().isEmpty()) {
            return UNKNOWN;
        }
        String lowerCommand = command.toLowerCase().trim();
        switch (lowerCommand) {
            case "bye":
                return BYE;
            case "list":
                return LIST;
            case "mark":
                return MARK;
            case "unmark":
                return UNMARK;
            case "delete":
                return DELETE;
            case "todo":
                return TODO;
            case "deadline":
                return DEADLINE;
            case "event":
                return EVENT;
            case "help":
                return HELP;
            default:
                return UNKNOWN;
        }
    }
}
