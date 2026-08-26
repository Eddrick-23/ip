package neil.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that must be completed by a specific date.
 */
public class DeadlineTask extends Task{
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
    private final LocalDate deadline;
    /**
     * Creates an incomplete deadline task with the specified description and date.
     *
     * @param description Description of the task.
     * @param deadline Date by which the task must be completed.
     */
    public DeadlineTask(String description, LocalDate deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", deadline.format(formatter)) ;
    }

    @Override
    public String encode() {
        return String.format(
                "D | %d | %s | %s",
                isDone ? 1 : 0,
                description,
                deadline
        );
    }
}
