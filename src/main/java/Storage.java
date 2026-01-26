import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to file.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates Storage with given file path.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from storage file.
     * Returns empty list if file doesn't exist.
     *
     * @throws MemoMaxException if file cannot be read or has corrupted data
     */
    public ArrayList<Task> load() throws MemoMaxException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

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
     * Saves tasks to storage file.
     * Creates data folder if it doesn't exist.
     *
     * @throws MemoMaxException if file cannot be written
     */
    public void save(ArrayList<Task> tasks) throws MemoMaxException {
        try {
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
     * Parses a storage file line into Task object.
     * Returns null if line format is invalid.
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