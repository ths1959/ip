package memomax.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import memomax.exception.MemoMaxException;
import memomax.task.Task;
import memomax.task.Todo;
import memomax.task.Deadline;
import memomax.task.Event;

/**
 * Handles loading and saving tasks to file.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates Storage with given file path.
     *
     * @param filePath The path of the file to store tasks.
     */
    public Storage(String filePath) {
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

        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (!line.isEmpty()) {
                    Task task = parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
        } catch (IOException e) {
            throw new MemoMaxException("Cannot read tasks file. Starting fresh");
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
                for (Task task : tasks) {
                    writer.write(task.toFileFormat() + "\n");
                }
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
                boolean dirsCreated = parentDir.mkdirs();
                if (!dirsCreated) {
                    throw new MemoMaxException("Failed to create directory: "
                            + parentDir.getPath());
                }
            }

            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (!fileCreated) {
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

        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0].trim();
        String doneStatus = parts[1].trim();
        String description = parts[2].trim();

        if (!type.equals("T") && !type.equals("D") && !type.equals("E")) {
            return null;
        }

        if (!doneStatus.equals("0") && !doneStatus.equals("1")) {
            return null;
        }

        boolean isDone = doneStatus.equals("1");
        Task task;

        try {
            switch (type) {
            case "T":
                if (parts.length != 3 || description.isEmpty()) {
                    return null;
                }
                task = new Todo(description);
                break;

            case "D":
                if (parts.length != 4) {
                    return null;
                }
                String by = parts[3].trim();
                if (description.isEmpty() || by.isEmpty()) {
                    return null;
                }
                task = new Deadline(description, by);
                break;

            case "E":
                if (parts.length != 5) {
                    return null;
                }
                String from = parts[3].trim();
                String to = parts[4].trim();
                if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    return null;
                }
                task = new Event(description, from, to);
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