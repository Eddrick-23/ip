package neil.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import neil.task.Task;
import neil.task.ToDoTask;

/**
 * Tests for displaying messages to the user.
 */
class UiTest {
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
