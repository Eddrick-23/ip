package neil;

import neil.exception.NeilException;
import neil.parser.Parser;
import neil.storage.Storage;
import neil.task.Task;
import neil.task.ToDoList;
import neil.ui.Ui;

/**
 * Processes commands for the Neil task-management application.
 */
public class Neil {
    private final Storage storage;
    private final Ui ui;
    private final ToDoList toDoList;
    private final String startupErrorMessage;

    /**
     * Creates the application using the specified storage file.
     *
     * @param filePath path to the task storage file.
     */
    public Neil(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        this.toDoList = new ToDoList();
        String loadingError = null;

        try {
            for (Task task : storage.load()) {
                toDoList.add(task);
            }
        } catch (NeilException e) {
            loadingError = ui.showError(e.getMessage());
        }

        this.startupErrorMessage = loadingError;
    }

    /**
     * Returns the message shown when the application starts.
     *
     * @return welcome message, or a storage error if saved tasks could not be loaded.
     */
    public String getWelcomeMessage() {
        if (startupErrorMessage != null) {
            return startupErrorMessage;
        }
        return ui.showWelcome();
    }

    /**
     * Processes one user command and returns Neil's response.
     *
     * @param input raw user command.
     * @return response to display in the user interface.
     */
    public String getResponse(String input) {
        if (isExitCommand(input)) {
            return ui.showGoodbye();
        }

        if (startupErrorMessage != null) {
            return startupErrorMessage;
        }

        String[] parts = input.trim().split("\\s+");
        try {
            switch (parts[0]) {
                case "mark": {
                    int taskNumber = Parser.parseTaskNumber(parts);
                    Task task = toDoList.markTaskAsDone(taskNumber);
                    storage.save(toDoList.getTasks());
                    return ui.showTaskMarked(task);
                }
                case "unmark": {
                    int taskNumber = Parser.parseTaskNumber(parts);
                    Task task = toDoList.unmarkTask(taskNumber);
                    storage.save(toDoList.getTasks());
                    return ui.showTaskUnmarked(task);
                }
                case "delete": {
                    int taskNumber = Parser.parseTaskNumber(parts);
                    Task task = toDoList.remove(taskNumber);
                    storage.save(toDoList.getTasks());
                    return ui.showTaskDeleted(task, toDoList.size());
                }
                case "list":
                    return ui.showTaskList(toDoList);
                case "find": {
                    String keyword = Parser.parseFindKeyword(input);
                    return ui.showMatchingTasks(toDoList.findTasks(keyword));
                }
                default:
                    Task task = Parser.parseTask(input);
                    toDoList.add(task);
                    storage.save(toDoList.getTasks());
                    return ui.showTaskAdded(task, toDoList.size());
            }
        } catch (NeilException e) {
            return ui.showError(e.getMessage());
        }
    }

    /**
     * Returns whether the specified command should exit the application.
     *
     * @param input raw user command.
     * @return true if the command is {@code bye}, ignoring letter case.
     */
    public boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("bye");
    }
}
