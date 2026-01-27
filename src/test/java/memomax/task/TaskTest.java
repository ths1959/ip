package memomax.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the Task class.
 * Ensures that task creation, status change, and string formatting work correctly.
 */
public class TaskTest {

    @Test
    public void constructor_validDescription_taskCreated() {
        Task task = new Task("Read book");
        assertTrue(task.toString().contains("Read book"));
    }

    @Test
    public void mark_taskNotDone_taskMarked() {
        Task task = new Task("Read book");
        task.mark();
        assertTrue(task.toString().contains("[X]"));
    }

    @Test
    public void unmark_taskDone_taskUnmarked() {
        Task task = new Task("Read book");
        task.mark();
        task.unmark();
        assertTrue(task.toString().contains("[ ]"));
    }

    @Test
    public void toString_taskNotDone_returnsCorrectFormat() {
        Task task = new Task("Read book");
        String result = task.toString();
        assertTrue(result.contains("[ ]"));
        assertTrue(result.contains("Read book"));
    }

    @Test
    public void toString_taskDone_returnsCorrectFormat() {
        Task task = new Task("Read book");
        task.mark();
        String result = task.toString();
        assertTrue(result.contains("[X]"));
        assertTrue(result.contains("Read book"));
    }

    @Test
    public void getStatusIcon_taskNotDone_returnsBracketSpace() {
        Task task = new Task("Read book");
        assertEquals("[ ]", task.getStatusIcon());
    }

    @Test
    public void getStatusIcon_taskDone_returnsBracketX() {
        Task task = new Task("Read book");
        task.mark();
        assertEquals("[X]", task.getStatusIcon());
    }
}
