public class DeadlineTask extends Task{
    private final String deadline;
    public DeadlineTask(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", this.deadline) ;
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
