package memomax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import memomax.task.Task;
import memomax.task.Todo;

/**
 * Tests the functionality of the Storage class.
 * Ensures that loading and saving to a file works as expected.
 */
public class StorageTest {
    private static final String TEST_FILE_PATH = "data/test_storage.txt";

    @Test
    public void save_validTasks_fileCreated() throws Exception {
        Storage storage = new Storage(TEST_FILE_PATH);
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Test storage"));

        storage.save(tasks);

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
        file.delete();
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
        new File(TEST_FILE_PATH).delete();
    }

    @Test
    public void load_nonExistentFile_returnsEmptyList() throws Exception {
        Storage storage = new Storage("data/non_existent.txt");
        ArrayList<Task> tasks = storage.load();

        assertTrue(tasks.isEmpty());
        new File("data/non_existent.txt").delete();
    }
}