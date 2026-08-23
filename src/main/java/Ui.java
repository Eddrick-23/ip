import java.util.Scanner;

/**
 * Handles input from and output to the user.
 */
public class Ui {
    private static final String DIVIDER =
            "____________________________________________________________";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

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

    public void showGoodbye() {
        System.out.println(
                "Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }

    public void showError(String message) {
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    public void showTaskMarked(Task task) {
        showMessage(String.format(
                "Nice! I've marked this task as done:\n%s",
                task
        ));
    }

    public void showTaskUnmarked(Task task) {
        showMessage(String.format(
                "OK, I've marked this task as not done yet:\n%s",
                task
        ));
    }

    public void showTaskDeleted(Task task, int taskCount) {
        showMessage(String.format(
                "Noted. I've removed this task:\n%s\n"
                        + "Now you have %d tasks in this list.",
                task,
                taskCount
        ));
    }

    public void showTaskList(ToDoList toDoList) {
        showMessage(String.format(
                "Here are the tasks in your list:\n%s",
                toDoList
        ));
    }

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

    public void close() {
        scanner.close();
    }
}
