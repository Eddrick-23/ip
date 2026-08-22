import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void add(Task task) {
        this.tasks.add(task);
    }

    public Task remove(int id) throws NeilException{
        if  (!this.taskExists(id)) {
            throw new NeilException("The task " + id + " does not exist");
        }
        return this.tasks.remove(id - 1);
    }

    private boolean taskExists(int id) {
        int idx = id - 1;
        if (idx < 0 || idx >= tasks.size()) {
            return false;
        }
        return this.tasks.get(idx) != null;
    }

    public Task markTaskAsDone(int id) throws NeilException {
        if  (!this.taskExists(id)) {
            throw new NeilException("The task " + id + " does not exist");
        }
        this.tasks.get(id - 1).markAsDone();
        return this.tasks.get(id - 1);
    }

    public Task unmarkTask(int id) throws NeilException {
        if  (!this.taskExists(id)) {
            throw new NeilException("The task " + id + " does not exist");
        }
        this.tasks.get(id - 1).unmark();
        return this.tasks.get(id - 1);
    }

    public int size() {
        return this.tasks.size();
    }

    public List<Task> getTasks() {
        return List.copyOf(this.tasks);
    }
}
