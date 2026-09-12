package neil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import neil.ui.Ui;

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
                neil.getResponse("todo read book").message()
        );
        assertEquals(
                "Nice! I've marked this task as done:\n[T][X] read book",
                neil.getResponse("mark 1").message()
        );
        assertEquals(
                "Here are the matching tasks in your list:\n1.[T][X] read book",
                neil.getResponse("find BOOK").message()
        );
        assertEquals(List.of("T | 1 | read book"), Files.readAllLines(storageFile));
    }

    @Test
    void constructor_savedTasksExist_tasksAvailableToCommands() throws IOException {
        Path storageFile = temporaryDirectory.resolve("tasks.txt");
        Files.writeString(storageFile, "T | 0 | read book\n");

        Neil neil = new Neil(storageFile.toString());

        assertEquals(
                "Here are the tasks in your list:\n1.[T][ ] read book\n",
                neil.getResponse("list").message()
        );
    }

    @Test
    void getResponse_helpCommand_helpReturnedWithoutChangingStorage() throws IOException {
        Path storageFile = temporaryDirectory.resolve("tasks.txt");
        Files.writeString(storageFile, "T | 0 | read book\n");
        Neil neil = new Neil(storageFile.toString());
        CommandResult response = neil.getResponse("  help  ");

        assertEquals(new Ui().showHelp(), response.message());
        assertTrue(response.isHelp());
        assertEquals(List.of("T | 0 | read book"), Files.readAllLines(storageFile));
    }

    @Test
    void getResponse_helpCommandAfterStorageError_helpReturned() throws IOException {
        Path storageDirectory = temporaryDirectory.resolve("tasks");
        Files.createDirectory(storageDirectory);
        Neil neil = new Neil(storageDirectory.toString());
        CommandResult response = neil.getResponse("help");

        assertEquals(new Ui().showHelp(), response.message());
        assertTrue(response.isHelp());
    }

    @Test
    void getWelcomeResponse_storageError_errorResultReturned() throws IOException {
        Path storageDirectory = temporaryDirectory.resolve("tasks");
        Files.createDirectory(storageDirectory);
        Neil neil = new Neil(storageDirectory.toString());

        assertTrue(neil.getWelcomeResponse().isError());
    }

    @Test
    void getResponse_invalidHelpCommand_errorReturned() {
        Neil neil = new Neil(temporaryDirectory.resolve("tasks.txt").toString());
        CommandResult uppercaseResponse = neil.getResponse("HELP");
        CommandResult argumentsResponse = neil.getResponse("help deadline");

        assertEquals("Neil: command HELP not supported", uppercaseResponse.message());
        assertEquals("Neil: Use: help", argumentsResponse.message());
        assertTrue(uppercaseResponse.isError());
        assertTrue(argumentsResponse.isError());
    }

    @Test
    void getResponse_invalidCommand_errorReturnedWithoutExiting() {
        Neil neil = new Neil(temporaryDirectory.resolve("tasks.txt").toString());

        CommandResult response = neil.getResponse("hello");

        assertEquals("Neil: command hello not supported", response.message());
        assertTrue(response.isError());
        assertFalse(neil.isExitCommand("hello"));
    }

    @Test
    void getResponse_byeCommand_goodbyeReturnedAndExitRequested() {
        Neil neil = new Neil(temporaryDirectory.resolve("tasks.txt").toString());

        CommandResult response = neil.getResponse("  BYE  ");

        assertEquals("Bye. Hope to see you again soon!", response.message());
        assertFalse(response.isError());
        assertTrue(neil.isExitCommand("bye"));
        assertTrue(neil.isExitCommand("\t bye \t"));
        assertFalse(neil.isExitCommand("bye now"));
    }
}
