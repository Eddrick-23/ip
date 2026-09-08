package neil.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import neil.task.Task;
import neil.task.ToDoTask;

/**
 * Tests for displaying messages to the user.
 */
class UiTest {
    @Test
    void showWelcome_applicationStarted_helpCommandSuggested() {
        Ui ui = new Ui();

        assertTrue(ui.showWelcome().contains("Type \"help\" to see available commands."));
    }

    @Test
    void showHelp_helpRequested_commandsAndExamplesReturned() {
        Ui ui = new Ui();

        String expectedOutput = "AVAILABLE COMMANDS\n"
                + "==================\n"
                + "\n"
                + "ADD TASKS\n"
                + "---------\n"
                + "\n"
                + "todo DESCRIPTION\n"
                + "  Adds a todo task.\n"
                + "  Example: todo read book\n"
                + "\n"
                + "deadline DESCRIPTION /by YYYY-MM-DD\n"
                + "  Adds a deadline task.\n"
                + "  Example: deadline return book /by 2026-09-15\n"
                + "\n"
                + "event DESCRIPTION /from START /to END\n"
                + "  Adds an event task.\n"
                + "  Example: event meeting /from 2pm /to 3pm\n"
                + "\n"
                + "MANAGE TASKS\n"
                + "------------\n"
                + "\n"
                + "list\n"
                + "  Shows all tasks.\n"
                + "\n"
                + "mark NUMBER\n"
                + "  Marks a task as done.\n"
                + "  Example: mark 1\n"
                + "\n"
                + "unmark NUMBER\n"
                + "  Marks a task as not done.\n"
                + "  Example: unmark 1\n"
                + "\n"
                + "delete NUMBER\n"
                + "  Deletes a task.\n"
                + "  Example: delete 1\n"
                + "\n"
                + "find KEYWORD\n"
                + "  Finds matching tasks.\n"
                + "  Example: find book\n"
                + "\n"
                + "OTHER\n"
                + "-----\n"
                + "\n"
                + "help\n"
                + "  Shows this help message.\n"
                + "\n"
                + "bye\n"
                + "  Exits Neil.\n"
                + "\n"
                + "NUMBER is the task number shown by list.";
        assertEquals(expectedOutput, ui.showHelp());
    }

    @Test
    void showMatchingTasks_matchingTasks_tasksDisplayedWithNewNumbers() {
        Task firstTask = new ToDoTask("read book");
        Task secondTask = new ToDoTask("return book");
        secondTask.markAsDone();
        Ui ui = new Ui();

        String message = ui.showMatchingTasks(List.of(firstTask, secondTask));

        String expectedOutput = "Here are the matching tasks in your list:\n"
                + "1.[T][ ] read book\n"
                + "2.[T][X] return book";
        assertEquals(expectedOutput, message);
    }

    @Test
    void showMatchingTasks_noMatchingTasks_headingDisplayed() {
        Ui ui = new Ui();

        String message = ui.showMatchingTasks(List.of());

        assertEquals("Here are the matching tasks in your list:", message);
    }
}
