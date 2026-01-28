package memomax.tasklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import memomax.task.Task;
import memomax.task.Todo;

/**
 * Tests the functionality of the TaskList class.
 * Ensures that adding, deleting, and finding tasks works correctly.
 */
public class TaskListTest {

    @Test
    public void delete_validIndex_taskRemoved() throws Exception {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Read book"));
        taskList.add(new Todo("Write code"));

        Task removedTask = taskList.delete(0);

        assertEquals(1, taskList.size());
        assertTrue(removedTask.toString().contains("Read book"));
    }

    @Test
    public void getAllTasks_existingTasks_returnsCorrectList() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Task 1"));
        taskList.add(new Todo("Task 2"));

        ArrayList<Task> allTasks = taskList.getAllTasks();

        assertEquals(2, allTasks.size());
        assertTrue(allTasks.get(0).toString().contains("Task 1"));
    }

    @Test
    public void findTasks_matchingKeyword_returnsFilteredList() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Read book"));
        taskList.add(new Todo("Eat lunch"));
        taskList.add(new Todo("Exercise"));

        ArrayList<Task> matches = taskList.findTasks("book");

        assertEquals(1, matches.size());
        assertTrue(matches.get(0).toString().contains("Read book"));
    }

    @Test
    public void isEmpty_emptyList_returnsTrue() {
        TaskList taskList = new TaskList();
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptyList_returnsFalse() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Work"));
        assertFalse(taskList.isEmpty());
    }
}