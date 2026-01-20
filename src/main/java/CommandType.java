/**
 * Command types supported by MemoMax.
 */
public enum CommandType {
    BYE, LIST, MARK, UNMARK, DELETE,
    TODO, DEADLINE, EVENT, HELP, UNKNOWN;

    /**
     * Converts string to CommandType.
     * Returns UNKNOWN for invalid commands.
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
