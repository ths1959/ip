package memomax.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import memomax.exception.MemoMaxException;

/**
 * Tests the functionality of the Parser class.
 * Ensures command extraction and parameter validation work correctly.
 */
public class ParserTest {

    @Test
    public void parseTodo_validInput_returnsDescription() throws MemoMaxException {
        String result = Parser.parseTodo("todo read book");
        assertEquals("read book", result);
    }

    @Test
    public void parseTodo_withExtraSpaces_returnsTrimmedDescription() throws MemoMaxException {
        String result = Parser.parseTodo("todo   read book   ");
        assertEquals("read book", result);
    }

    @Test
    public void parseTodo_emptyInput_throwsException() {
        try {
            Parser.parseTodo("todo");
            fail("Expected MemoMaxException for empty todo");
        } catch (MemoMaxException e) {
            assertTrue(e.getMessage().contains("Todo needs a description"));
        }
    }

    @Test
    public void parseTodo_onlyCommandWord_throwsException() {
        try {
            Parser.parseTodo("todo ");
            fail("Expected MemoMaxException for empty description");
        } catch (MemoMaxException e) {
            assertTrue(e.getMessage().contains("Todo needs a description"));
        }
    }

    @Test
    public void parseTaskNumber_validNumber_returnsNumber() throws MemoMaxException {
        String[] inputParts = {"mark", "3"};
        int result = Parser.parseTaskNumber(inputParts, "mark");
        assertEquals(3, result);
    }

    @Test
    public void parseTaskNumber_zero_throwsException() {
        String[] inputParts = {"mark", "0"};
        try {
            Parser.parseTaskNumber(inputParts, "mark");
            fail("Expected MemoMaxException for zero");
        } catch (MemoMaxException e) {
            assertTrue(e.getMessage().contains("Task number must be positive"));
        }
    }

    @Test
    public void parseTaskNumber_negative_throwsException() {
        String[] inputParts = {"mark", "-5"};
        try {
            Parser.parseTaskNumber(inputParts, "mark");
            fail("Expected MemoMaxException for negative number");
        } catch (MemoMaxException e) {
            assertTrue(e.getMessage().contains("Task number must be positive"));
        }
    }

    @Test
    public void parseTaskNumber_notNumber_throwsException() {
        String[] inputParts = {"mark", "abc"};
        try {
            Parser.parseTaskNumber(inputParts, "mark");
            fail("Expected MemoMaxException for non-number");
        } catch (MemoMaxException e) {
            assertTrue(e.getMessage().contains("is not a number"));
        }
    }

    @Test
    public void parseTaskNumber_missingNumber_throwsException() {
        String[] inputParts = {"mark"};
        try {
            Parser.parseTaskNumber(inputParts, "mark");
            fail("Expected MemoMaxException for missing number");
        } catch (MemoMaxException e) {
            assertTrue(e.getMessage().contains("Please tell me which task number"));
        }
    }

    @Test
    public void parseDeadline_validInput_returnsDescriptionAndDate() throws MemoMaxException {
        String[] result = Parser.parseDeadline("deadline return book /by 2025-01-30 1800");
        assertEquals("return book", result[0]);
        assertEquals("2025-01-30 1800", result[1]);
    }

    @Test
    public void parseDeadline_missingBy_throwsException() {
        try {
            Parser.parseDeadline("deadline return book");
            fail("Expected MemoMaxException for missing /by");
        } catch (MemoMaxException e) {
            assertTrue(e.getMessage().contains("Deadline needs a due date"));
        }
    }

    @Test
    public void parseDeadline_emptyDate_throwsException() {
        try {
            Parser.parseDeadline("deadline return book /by ");
            fail("Expected MemoMaxException for empty date");
        } catch (MemoMaxException e) {
            assertTrue(e.getMessage().contains("Due date is not specified"));
        }
    }

    @Test
    public void parseDeadline_emptyDescription_throwsException() {
        try {
            Parser.parseDeadline("deadline /by 2025-01-30 1800");
            fail("Expected MemoMaxException for empty description");
        } catch (MemoMaxException e) {
            assertTrue(e.getMessage().contains("Task not specified"));
        }
    }

    @Test
    public void parseEvent_validInput_returnsAllParts() throws MemoMaxException {
        String[] result = Parser.parseEvent("event meeting /from 2025-01-30 1400 /to 2025-01-30 1600");
        assertEquals("meeting", result[0]);
        assertEquals("2025-01-30 1400", result[1]);
        assertEquals("2025-01-30 1600", result[2]);
    }

    @Test
    public void parseEvent_swappedDelimiters_throwsException() {
        try {
            Parser.parseEvent("event meeting /to 2025-01-30 1600 /from 2025-01-30 1400");
            fail("Expected MemoMaxException for swapped delimiters");
        } catch (MemoMaxException e) {
            assertTrue(e.getMessage().contains("/from must come before /to"));
        }
    }

    @Test
    public void parseEvent_missingFrom_throwsException() {
        try {
            Parser.parseEvent("event meeting /to 2025-01-30 1600");
            fail("Expected MemoMaxException for missing /from");
        } catch (MemoMaxException e) {
            assertTrue(e.getMessage().contains("Start time not specified"));
        }
    }

    @Test
    public void parseEvent_missingTo_throwsException() {
        try {
            Parser.parseEvent("event meeting /from 2025-01-30 1400");
            fail("Expected MemoMaxException for missing /to");
        } catch (MemoMaxException e) {
            assertTrue(e.getMessage().contains("End time not specified"));
        }
    }

    @Test
    public void parseUpdate_multipleSpaces_returnsCorrectParts() throws MemoMaxException {
        String[] result = Parser.parseUpdate("update    1    new description");
        assertEquals("1", result[0]);
        assertEquals("new description", result[1]);
    }
}
