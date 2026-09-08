package neil.ui;

import java.util.List;

import neil.task.Task;
import neil.task.ToDoList;

/**
 * Formats messages shown to the user.
 */
public class Ui {
    /**
     * Returns the welcome message.
     *
     * @return welcome message.
     */
    public String showWelcome() {
        String banner =
                "#   #  #####  #####  #    \n"
                        + "##  #  #        #    #    \n"
                        + "# # #  ####     #    #    \n"
                        + "#  ##  #        #    #    \n"
                        + "#   #  #####  #####  #####";

        return banner + "\n\nHello! I'm Neil.\nWhat can I do for you?\n"
                + "Type \"help\" to see available commands.";
    }

    /**
     * Returns guidance for using Neil's commands.
     *
     * @return command descriptions and examples.
     */
    public String showHelp() {
        return "AVAILABLE COMMANDS\n"
                + "==================\n"
                + "\n"
                + "ADD TASKS\n"
                + "---------\n"
                + "\n"
                + "todo DESCRIPTION\n"
                + "  Adds a todo task.\n"
                + "  Example: todo read book\n"
                + "\n"
                + "deadline DESCRIPTION /by YYYY-MM-DD\n"
                + "  Adds a deadline task.\n"
                + "  Example: deadline return book /by 2026-09-15\n"
                + "\n"
                + "event DESCRIPTION /from START /to END\n"
                + "  Adds an event task.\n"
                + "  Example: event meeting /from 2pm /to 3pm\n"
                + "\n"
                + "MANAGE TASKS\n"
                + "------------\n"
                + "\n"
                + "list\n"
                + "  Shows all tasks.\n"
                + "\n"
                + "mark NUMBER\n"
                + "  Marks a task as done.\n"
                + "  Example: mark 1\n"
                + "\n"
                + "unmark NUMBER\n"
                + "  Marks a task as not done.\n"
                + "  Example: unmark 1\n"
                + "\n"
                + "delete NUMBER\n"
                + "  Deletes a task.\n"
                + "  Example: delete 1\n"
                + "\n"
                + "find KEYWORD\n"
                + "  Finds matching tasks.\n"
                + "  Example: find book\n"
                + "\n"
                + "OTHER\n"
                + "-----\n"
                + "\n"
                + "help\n"
                + "  Shows this help message.\n"
                + "\n"
                + "bye\n"
                + "  Exits Neil.\n"
                + "\n"
                + "NUMBER is the task number shown by list.";
    }

    /**
     * Returns the goodbye message.
     *
     * @return goodbye message.
     */
    public String showGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns an error message.
     *
     * @param message error message to display.
     * @return error message.
     */
    public String showError(String message) {
        return message;
    }

    /**
     * Returns confirmation that a task was marked as completed.
     *
     * @param task task marked as completed.
     * @return task-marked confirmation.
     */
    public String showTaskMarked(Task task) {
        return String.format(
                "Nice! I've marked this task as done:\n%s",
                task
        );
    }

    /**
     * Returns confirmation that a task was marked as incomplete.
     *
     * @param task task marked as incomplete.
     * @return task-unmarked confirmation.
     */
    public String showTaskUnmarked(Task task) {
        return String.format(
                "OK, I've marked this task as not done yet:\n%s",
                task
        );
    }

    /**
     * Returns confirmation that a task was deleted.
     *
     * @param task deleted task.
     * @param taskCount number of tasks remaining.
     * @return task-deleted confirmation.
     */
    public String showTaskDeleted(Task task, int taskCount) {
        return String.format(
                "Noted. I've removed this task:\n%s\n"
                        + "Now you have %d tasks in this list.",
                task,
                taskCount
        );
    }

    /**
     * Returns the tasks in the specified list.
     *
     * @param toDoList task list to display.
     * @return numbered task list message.
     */
    public String showTaskList(ToDoList toDoList) {
        return String.format(
                "Here are the tasks in your list:\n%s",
                toDoList
        );
    }

    /**
     * Returns the tasks that match a find command.
     *
     * @param matchingTasks matching tasks to display.
     * @return numbered matching-task message.
     */
    public String showMatchingTasks(List<Task> matchingTasks) {
        StringBuilder message = new StringBuilder(
                "Here are the matching tasks in your list:");

        for (int i = 0; i < matchingTasks.size(); i++) {
            message.append(String.format(
                    "\n%d.%s",
                    i + 1,
                    matchingTasks.get(i)
            ));
        }

        return message.toString();
    }

    /**
     * Returns confirmation that a task was added.
     *
     * @param task added task.
     * @param taskCount number of tasks in the list.
     * @return task-added confirmation.
     */
    public String showTaskAdded(Task task, int taskCount) {
        return String.format(
                "Got it. I've added this task:\n%s\n"
                        + "Now you have %d tasks in the list.",
                task,
                taskCount
        );
    }
}
