package neil.task;

/**
 * Represents a task scheduled between a start and end time.
 */
public class EventTask extends Task {
    private final String from;
    private final String to;

    /**
     * Creates an event task with the specified description and schedule.
     *
     * @param description description of the task.
     * @param from start time of the event.
     * @param to end time of the event.
     */
    public EventTask(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + String.format(" (from: %s to: %s)", from, to);
    }

    @Override
    public String encode() {
        return String.format(
                "E | %d | %s | %s | %s",
                isDone ? 1 : 0,
                description,
                from,
                to
        );
    }
}
