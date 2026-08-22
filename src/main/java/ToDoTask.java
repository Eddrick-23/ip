public class ToDoTask extends Task {
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
