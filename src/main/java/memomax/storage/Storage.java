package memomax.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import memomax.exception.MemoMaxException;
import memomax.task.Deadline;
import memomax.task.Event;
import memomax.task.Task;
import memomax.task.Todo;

/**
 * Handles loading and saving tasks to file.
 */
public class Storage {
    private static final String DELIMITER = " \\| ";
    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";
    private static final String STATUS_DONE = "1";

    private final String filePath;

    /**
     * Creates Storage with given file path.
     *
     * @param filePath The path of the file to store tasks.
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path should not be null or empty";
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * Returns an empty list if the file does not exist.
     *
     * @return An ArrayList of tasks.
     * @throws MemoMaxException If the file cannot be read or has corrupted data.
     */
    public ArrayList<Task> load() throws MemoMaxException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        ensureDirectoryAndFileExist();

        if (!file.exists()) {
            return tasks;
        }

        int corruptedLines = 0;
        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (!line.isEmpty()) {
                    Task task = parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    } else {
                        corruptedLines++;
                    }
                }
            }
        } catch (IOException e) {
            throw new MemoMaxException("Cannot read tasks file. Starting fresh");
        }

        if (corruptedLines > 0) {
            throw new MemoMaxException("Warning: " + corruptedLines
                + " corrupted lines found in storage. These were skipped.", tasks);
        }

        return tasks;
    }

    /**
     * Saves tasks to the storage file.
     * Creates the data folder if it does not exist.
     *
     * @param tasks The list of tasks to be saved.
     * @throws MemoMaxException If file cannot be written.
     */
    public void save(ArrayList<Task> tasks) throws MemoMaxException {
        assert tasks != null : "Task list to save should not be null";
        try {
            ensureDirectoryAndFileExist();
            File file = new File(filePath);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                boolean dirsCreated = parentDir.mkdirs();
                if (!dirsCreated) {
                    throw new MemoMaxException("Failed to save tasks.");
                }
            }

            try (FileWriter writer = new FileWriter(file)) {
                tasks.stream()
                        .peek(task -> {
                            assert task != null : "Cannot save a null task to file";
                        })
                        .map(task -> task.toFileFormat() + "\n")
                        .forEach(formattedLine -> {
                            try {
                                writer.write(formattedLine);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            } catch (RuntimeException e) {
                throw new MemoMaxException("Failed to save tasks.");
            }
        } catch (IOException e) {
            throw new MemoMaxException("Failed to save tasks.");
        }
    }

    /**
     * Ensures the directory and file exist.
     * Creates them if they do not exist.
     *
     * @throws MemoMaxException If the directory or file cannot be created.
     */
    private void ensureDirectoryAndFileExist() throws MemoMaxException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        try {
            if (parentDir != null && !parentDir.exists()) {
                boolean isParentDirCreated = parentDir.mkdirs();
                if (!isParentDirCreated) {
                    throw new MemoMaxException("Failed to create directory: "
                            + parentDir.getPath());
                }
            }

            if (!file.exists()) {
                boolean isNewFileCreated = file.createNewFile();
                if (!isNewFileCreated) {
                    throw new MemoMaxException("Failed to create file: " + filePath);
                }
            }
        } catch (IOException e) {
            throw new MemoMaxException("Failed to create file or directory: "
                    + e.getMessage());
        }
    }

    /**
     * Parses a storage file line into a Task object.
     * Returns null if the line format is invalid.
     *
     * @param line A single line from the storage file.
     * @return The parsed Task object.
     */
    private Task parseTask(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(DELIMITER);
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0].trim();
        String doneStatus = parts[1].trim();
        String description = parts[2].trim();

        if (description.isEmpty()) {
            return null;
        }

        boolean isDone = doneStatus.equals(STATUS_DONE);
        Task task;

        try {
            switch (type) {
            case TYPE_TODO:
                if (parts.length != 3) {
                    return null;
                }
                task = new Todo(description);
                break;

            case TYPE_DEADLINE:
                if (parts.length != 4 || parts[3].trim().isEmpty()) {
                    return null;
                }
                task = new Deadline(description, parts[3].trim());
                break;

            case TYPE_EVENT:
                if (parts.length != 5 || parts[3].trim().isEmpty() || parts[4].trim().isEmpty()) {
                    return null;
                }
                task = new Event(description, parts[3].trim(), parts[4].trim());
                break;

            default:
                return null;
            }
        } catch (Exception e) {
            return null;
        }

        if (isDone) {
            task.mark();
        }

        return task;
    }
}
