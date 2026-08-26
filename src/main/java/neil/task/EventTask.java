package neil.task;

/**
 * Represents a task that takes place between a start and end time.
 */
public class EventTask extends Task {
    private final String from;
    private final String to;
    /**
     * Creates an incomplete event task with the specified time range.
     *
     * @param description Description of the event.
     * @param from Start time of the event.
     * @param to End time of the event.
     */
    public EventTask(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (from: %s to: %s)", this.from, this.to);
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
