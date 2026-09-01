package neil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for processing complete user-command workflows.
 */
class NeilTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void getResponse_taskWorkflow_behaviorsAndPersistencePreserved() throws IOException {
        Path storageFile = temporaryDirectory.resolve("tasks.txt");
        Neil neil = new Neil(storageFile.toString());

        assertEquals(
                "Got it. I've added this task:\n[T][ ] read book\n"
                        + "Now you have 1 tasks in the list.",
                neil.getResponse("todo read book")
        );
        assertEquals(
                "Nice! I've marked this task as done:\n[T][X] read book",
                neil.getResponse("mark 1")
        );
        assertEquals(
                "Here are the matching tasks in your list:\n1.[T][X] read book",
                neil.getResponse("find BOOK")
        );
        assertEquals("T | 1 | read book\n", Files.readString(storageFile));
    }

    @Test
    void constructor_savedTasksExist_tasksAvailableToCommands() throws IOException {
        Path storageFile = temporaryDirectory.resolve("tasks.txt");
        Files.writeString(storageFile, "T | 0 | read book\n");

        Neil neil = new Neil(storageFile.toString());

        assertEquals(
                "Here are the tasks in your list:\n1.[T][ ] read book\n",
                neil.getResponse("list")
        );
    }

    @Test
    void getResponse_invalidCommand_errorReturnedWithoutExiting() {
        Neil neil = new Neil(temporaryDirectory.resolve("tasks.txt").toString());

        assertEquals("Neil: command hello not supported", neil.getResponse("hello"));
        assertFalse(neil.isExitCommand("hello"));
    }

    @Test
    void getResponse_byeCommand_goodbyeReturnedAndExitRequested() {
        Neil neil = new Neil(temporaryDirectory.resolve("tasks.txt").toString());

        assertEquals("Bye. Hope to see you again soon!", neil.getResponse("BYE"));
        assertTrue(neil.isExitCommand("bye"));
        assertFalse(neil.isExitCommand(" bye "));
    }
}
