package neil.task;

/**
 * Represents a task without an associated date or time.
 */
public class ToDoTask extends Task {
    /**
     * Creates an incomplete todo task with the specified description.
     *
     * @param description Description of the task.
     */
    public ToDoTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String encode() {
        return String.format(
                "T | %d | %s",
                isDone ? 1 : 0,
                description
        );
    }

}
