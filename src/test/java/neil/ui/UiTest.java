package neil.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import neil.task.Task;
import neil.task.ToDoTask;

/**
 * Tests for displaying messages to the user.
 */
class UiTest {
    private static final String DIVIDER =
            "____________________________________________________________";

    private final PrintStream originalOutput = System.out;

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOutput);
    }

    @Test
    void showMatchingTasks_matchingTasks_tasksDisplayedWithNewNumbers() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
        Task firstTask = new ToDoTask("read book");
        Task secondTask = new ToDoTask("return book");
        secondTask.markAsDone();
        Ui ui = new Ui();

        ui.showMatchingTasks(List.of(firstTask, secondTask));

        String expectedOutput = "Here are the matching tasks in your list:\n"
                + "1.[T][ ] read book\n"
                + "2.[T][X] return book\n"
                + DIVIDER + "\n";
        assertEquals(expectedOutput, output.toString(StandardCharsets.UTF_8));
    }

    @Test
    void showMatchingTasks_noMatchingTasks_headingDisplayed() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
        Ui ui = new Ui();

        ui.showMatchingTasks(List.of());

        String expectedOutput = "Here are the matching tasks in your list:\n"
                + DIVIDER + "\n";
        assertEquals(expectedOutput, output.toString(StandardCharsets.UTF_8));
    }
}
