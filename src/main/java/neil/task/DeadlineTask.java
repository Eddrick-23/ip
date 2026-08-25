package neil.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task{
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
    private final LocalDate deadline;
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
