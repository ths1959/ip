package memomax.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests the CommandType enum and its parsing logic.
 * Ensures string inputs are correctly mapped to command types.
 */
public class CommandTypeTest {

    @Test
    public void parseCommand_validCommands_returnsCorrectType() {
        assertEquals(CommandType.TODO, CommandType.parseCommand("todo"));
        assertEquals(CommandType.DEADLINE, CommandType.parseCommand("deadline"));
        assertEquals(CommandType.LIST, CommandType.parseCommand("list"));
    }

    @Test
    public void parseCommand_mixedCase_returnsCorrectType() {
        assertEquals(CommandType.EVENT, CommandType.parseCommand("EvEnT"));
        assertEquals(CommandType.BYE, CommandType.parseCommand("BYE"));
    }

    @Test
    public void parseCommand_withWhitespace_returnsCorrectType() {
        assertEquals(CommandType.FIND, CommandType.parseCommand("  find  "));
        assertEquals(CommandType.HELP, CommandType.parseCommand("\nhelp\t"));
    }

    @Test
    public void parseCommand_invalidOrEmptyInput_returnsUnknown() {
        assertEquals(CommandType.UNKNOWN, CommandType.parseCommand("invalid"));
        assertEquals(CommandType.UNKNOWN, CommandType.parseCommand(""));
        assertEquals(CommandType.UNKNOWN, CommandType.parseCommand(null));
    }
}