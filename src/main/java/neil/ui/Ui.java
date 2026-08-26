package neil.ui;

import java.util.List;
import java.util.Scanner;

import neil.task.Task;
import neil.task.ToDoList;

/**
 * Handles input from and output to the user.
 */
public class Ui {
    private static final String DIVIDER =
            "____________________________________________________________";

    private final Scanner scanner;

    /**
     * Creates a user interface that reads from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the next command entered by the user.
     *
     * @return user command.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        String banner =
                "#   #  #####  #####  #    \n"
                        + "##  #  #        #    #    \n"
                        + "# # #  ####     #    #    \n"
                        + "#  ##  #        #    #    \n"
                        + "#   #  #####  #####  #####";

        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println();
        System.out.println("Hello! I'm Neil.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println(
                "Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }

    /**
     * Displays an error message.
     *
     * @param message error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    /**
     * Displays confirmation that a task was marked as completed.
     *
     * @param task task marked as completed.
     */
    public void showTaskMarked(Task task) {
        showMessage(String.format(
                "Nice! I've marked this task as done:\n%s",
                task
        ));
    }

    /**
     * Displays confirmation that a task was marked as incomplete.
     *
     * @param task task marked as incomplete.
     */
    public void showTaskUnmarked(Task task) {
        showMessage(String.format(
                "OK, I've marked this task as not done yet:\n%s",
                task
        ));
    }

    /**
     * Displays confirmation that a task was deleted.
     *
     * @param task deleted task.
     * @param taskCount number of tasks remaining.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        showMessage(String.format(
                "Noted. I've removed this task:\n%s\n"
                        + "Now you have %d tasks in this list.",
                task,
                taskCount
        ));
    }

    /**
     * Displays the tasks in the specified list.
     *
     * @param toDoList task list to display.
     */
    public void showTaskList(ToDoList toDoList) {
        showMessage(String.format(
                "Here are the tasks in your list:\n%s",
                toDoList
        ));
    }

    /**
     * Displays the tasks that match a find command.
     *
     * @param matchingTasks matching tasks to display.
     */
    public void showMatchingTasks(List<Task> matchingTasks) {
        StringBuilder message = new StringBuilder(
                "Here are the matching tasks in your list:");

        for (int i = 0; i < matchingTasks.size(); i++) {
            message.append(String.format(
                    "\n%d.%s",
                    i + 1,
                    matchingTasks.get(i)
            ));
        }

        showMessage(message.toString());
    }

    /**
     * Displays confirmation that a task was added.
     *
     * @param task added task.
     * @param taskCount number of tasks in the list.
     */
    public void showTaskAdded(Task task, int taskCount) {
        showMessage(String.format(
                "Got it. I've added this task:\n%s\n"
                        + "Now you have %d tasks in the list.",
                task,
                taskCount
        ));
    }

    private void showMessage(String message) {
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    /**
     * Releases resources used to read user input.
     */
    public void close() {
        scanner.close();
    }
}
