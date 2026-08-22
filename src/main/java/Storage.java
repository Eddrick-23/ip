import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading tasks from and saving tasks to the hard disk.
 */
public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
    }

    /**
     * Saves all tasks to the configured file.
     *
     * @param tasks tasks to save
     * @throws NeilException if the file cannot be written
     */
    public void save(List<Task> tasks) throws NeilException {
        List<String> lines = new ArrayList<>();

        for (Task task : tasks) {
            lines.add(task.encode());
        }

        try {
            Path parentDirectory = filePath.getParent();

            if (parentDirectory != null) {
                Files.createDirectories(parentDirectory);
            }

            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new NeilException("Unable to save tasks to " + filePath);
        }
    }

    /**
     * Loads tasks from the configured file.
     * Returns an empty list if the file does not exist yet.
     *
     * @return tasks loaded from the file
     * @throws NeilException if the file cannot be read or decoded
     */
    public List<Task> load() throws NeilException {
        List<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

            for (String line : lines) {
                if (!line.isBlank()) {
                    tasks.add(Task.decode(line));
                }
            }

            return tasks;
        } catch (IOException e) {
            throw new NeilException("Unable to load tasks from " + filePath);
        }
    }
}