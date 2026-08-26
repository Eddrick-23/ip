package neil.task;

import java.util.ArrayList;
import java.util.List;

import neil.exception.NeilException;

/**
 * Stores and manages the user's tasks in display order.
 */
public class ToDoList {
    private ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
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

    /**
     * Adds a task to the end of the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified one-based position.
     *
     * @param id One-based position of the task.
     * @return Removed task.
     * @throws NeilException If no task exists at the specified position.
     */
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

    /**
     * Marks and returns the task at the specified one-based position as completed.
     *
     * @param id One-based position of the task.
     * @return Task that was marked as completed.
     * @throws NeilException If no task exists at the specified position.
     */
    public Task markTaskAsDone(int id) throws NeilException {
        if  (!this.taskExists(id)) {
            throw new NeilException("The task " + id + " does not exist");
        }
        this.tasks.get(id - 1).markAsDone();
        return this.tasks.get(id - 1);
    }

    /**
     * Marks and returns the task at the specified one-based position as incomplete.
     *
     * @param id One-based position of the task.
     * @return Task that was marked as incomplete.
     * @throws NeilException If no task exists at the specified position.
     */
    public Task unmarkTask(int id) throws NeilException {
        if  (!this.taskExists(id)) {
            throw new NeilException("The task " + id + " does not exist");
        }
        this.tasks.get(id - 1).unmark();
        return this.tasks.get(id - 1);
    }

    /**
     * Returns the number of tasks in this list.
     *
     * @return Number of tasks in this list.
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Returns an unmodifiable snapshot of the tasks in this list.
     *
     * @return Unmodifiable task list snapshot.
     */
    public List<Task> getTasks() {
        return List.copyOf(this.tasks);
    }
}
