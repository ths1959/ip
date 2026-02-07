package memomax.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import memomax.task.Task;
import memomax.task.Todo;

/**
 * Tests the functionality of the Ui class.
 * Focuses on verifying that output messages for task lists and search results
 * are correctly formatted and displayed to the console.
 */
public class UiTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Ui ui;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        ui = new Ui();
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void showTaskList_emptyList_printsNoTasksMessage() {
        String result = ui.showTaskList(new ArrayList<>(), true);
        assertTrue(result.contains("currently no tasks in your list"));
    }

    @Test
    public void showFindResults_noMatches_printsNoFoundMessage() {
        String result = ui.showFindResults(new ArrayList<>(), "book");
        assertTrue(result.contains("No tasks found containing: 'book'"));
    }

    @Test
    public void showFindResults_withMatches_printsTasks() {
        ArrayList<Task> matches = new ArrayList<>();
        matches.add(new Todo("read book"));
        String result = ui.showFindResults(matches, "book");
        assertTrue(result.contains("matching tasks in your list"));
        assertTrue(result.contains("read book"));
    }
}
