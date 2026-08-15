import java.util.ArrayList;

public class ToDoList {
    private ArrayList<Task> tasks;

    public ToDoList() {
        this.tasks = new ArrayList<>();
    }

    @Override
    public String toString() {
        String output = "";

        int count = 1;
        for (Task task : this.tasks) {
            output += String.format("%s.%s\n", count++, task);
        }

        return output;
    }

    public void add(String entry) {
        this.tasks.add(new Task(entry));
    }

    public boolean taskExists(int id) {
        int idx = id - 1;
        if (idx < 0 || idx >= tasks.size()) {
            return false;
        }
        return this.tasks.get(idx) != null;
    }

    public String markTaskAsDone(int id) {
        this.tasks.get(id - 1).markAsDone();
        return this.tasks.get(id - 1).toString();
    }

    public String unmarkTask(int id) {
        this.tasks.get(id - 1).unmark();
        return this.tasks.get(id - 1).toString();
    }
}
