package memomax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import memomax.exception.MemoMaxException;
import memomax.task.Task;
import memomax.task.Todo;

/**
 * Tests the functionality of the Storage class.
 * Ensures that loading and saving to a file works as expected.
 */
public class StorageTest {
    private static final String TEST_FILE_PATH = "data/test_storage.txt";

    @Test
    public void load_corruptedLine_skipsLineAndContinues() throws Exception {
        Storage storage = new Storage(TEST_FILE_PATH);
        java.nio.file.Files.createDirectories(java.nio.file.Path.of("data"));
        java.nio.file.Files.writeString(java.nio.file.Path.of(TEST_FILE_PATH),
            "T | 0 | Valid Task\nX | Corrupted | Line\n");

        try {
            storage.load();
            fail("Should have thrown a MemoMaxException for corrupted lines");
        } catch (MemoMaxException e) {
            ArrayList<Task> partialTasks = e.getPartialTasks();
            assertEquals(1, partialTasks.size());
            assertTrue(partialTasks.get(0).toString().contains("Valid Task"));
            assertTrue(e.getMessage().contains("corrupted lines found"));
        } finally {
            File file = new File(TEST_FILE_PATH);
            if (file.exists()) {
                assertTrue(file.delete(), "Failed to delete test file after corrupted line test");
            }
        }
    }

    @Test
    public void save_validTasks_fileCreated() throws Exception {
        Storage storage = new Storage(TEST_FILE_PATH);
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Test storage"));

        storage.save(tasks);

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());

        assertTrue(file.delete(), "Failed to delete test file after save test");
    }

    @Test
    public void load_validFile_returnsCorrectTasks() throws Exception {
        Storage storage = new Storage(TEST_FILE_PATH);
        ArrayList<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("Save and load"));

        storage.save(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(1, loadedTasks.size());
        assertTrue(loadedTasks.get(0).toString().contains("Save and load"));

        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            assertTrue(file.delete(), "Failed to delete test file after load test");
        }
    }

    @Test
    public void load_nonExistentFile_returnsEmptyList() throws Exception {
        Storage storage = new Storage("data/non_existent.txt");
        ArrayList<Task> tasks = storage.load();

        assertTrue(tasks.isEmpty());

        File file = new File("data/non_existent.txt");
        if (file.exists()) {
            assertTrue(file.delete(), "Failed to delete non-existent marker file");
        }
    }
}
