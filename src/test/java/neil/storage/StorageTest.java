package neil.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import neil.exception.NeilException;
import neil.task.DeadlineTask;
import neil.task.EventTask;
import neil.task.Task;
import neil.task.ToDoTask;

/**
 * Tests for saving and loading task data from disk.
 */
class StorageTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void load_fileDoesNotExist_emptyTaskListReturned() throws NeilException {
        Storage storage = new Storage(temporaryDirectory.resolve("missing.txt").toString());

        assertEquals(List.of(), storage.load());
    }

    @Test
    void save_thenLoad_tasksPersistedInOrder() throws NeilException, IOException {
        Path taskFile = temporaryDirectory.resolve("nested/tasks.txt");
        Storage storage = new Storage(taskFile.toString());
        Task todo = new ToDoTask("read book");
        Task deadline = new DeadlineTask("return book", java.time.LocalDate.of(2026, 8, 25));
        deadline.markAsDone();
        Task event = new EventTask("team meeting", "Monday 2pm", "Monday 4pm");

        storage.save(List.of(todo, deadline, event));

        assertEquals(List.of(
                "T | 0 | read book",
                "D | 1 | return book | 2026-08-25",
                "E | 0 | team meeting | Monday 2pm | Monday 4pm"), Files.readAllLines(taskFile));
        assertEquals(List.of(
                "T | 0 | read book",
                "D | 1 | return book | 2026-08-25",
                "E | 0 | team meeting | Monday 2pm | Monday 4pm"),
                storage.load().stream().map(Task::encode).toList());
    }

    @Test
    void load_fileWithBlankLines_blankLinesIgnored() throws NeilException, IOException {
        Path taskFile = temporaryDirectory.resolve("tasks.txt");
        Files.writeString(taskFile, "\nT | 0 | read book\n   \n");
        Storage storage = new Storage(taskFile.toString());

        assertEquals(List.of("T | 0 | read book"),
                storage.load().stream().map(Task::encode).toList());
    }

    @Test
    void load_fileWithInvalidTaskData_exceptionThrown() throws IOException {
        Path taskFile = temporaryDirectory.resolve("tasks.txt");
        Files.writeString(taskFile, "T | 2 | read book\n");
        Storage storage = new Storage(taskFile.toString());

        assertThrows(NeilException.class, storage::load);
    }
}
