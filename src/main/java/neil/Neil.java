package neil;

import neil.exception.NeilException;
import neil.parser.Parser;
import neil.storage.Storage;
import neil.task.Task;
import neil.task.ToDoList;
import neil.ui.Ui;

/**
 * Coordinates user interaction, task management, and persistent storage.
 */
public class Neil {
    private final Storage storage;
    private final Ui ui;
    private final ToDoList toDoList;

    /**
     * Creates a Neil application that stores tasks at the specified file path.
     *
     * @param filePath Path of the file used to store tasks.
     */
    public Neil(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        this.toDoList = new ToDoList();
    }

    /**
     * Runs the application until the user enters the {@code bye} command.
     */
    public void run() {
        try {
            for (Task task : storage.load()) {
                toDoList.add(task);
            }
        } catch (NeilException e) {
            ui.showError(e.getMessage());
            return;
        }

        ui.showWelcome();
        while (true) {
            String input = ui.readCommand();

            if (input.equalsIgnoreCase("bye")) {
                break;
            }

            String[] parts = input.trim().split("\\s+");
            try {
                switch (parts[0]) {
                    case "mark": {
                        int taskNumber = Parser.parseTaskNumber(parts);
                        Task task = toDoList.markTaskAsDone(taskNumber);
                        storage.save(toDoList.getTasks());
                        ui.showTaskMarked(task);
                        break;
                    }
                    case "unmark": {
                        int taskNumber = Parser.parseTaskNumber(parts);
                        Task task= toDoList.unmarkTask(taskNumber);
                        storage.save(toDoList.getTasks());
                        ui.showTaskUnmarked(task);
                        break;
                    }
                    case "delete": {
                        int taskNumber = Parser.parseTaskNumber(parts);
                        Task task = toDoList.remove(taskNumber);
                        storage.save(toDoList.getTasks());
                        ui.showTaskDeleted(task, toDoList.size());
                        break;
                    }
                    case "list": {
                        ui.showTaskList(toDoList);
                        break;
                    }
                    default:
                        Task task = Parser.parseTask(input);
                        toDoList.add(task);
                        storage.save(toDoList.getTasks());
                        ui.showTaskAdded(task, toDoList.size());
                }
            } catch (NeilException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.showGoodbye();
        ui.close();
    }

    /**
     * Starts Neil using the default task storage file.
     *
     * @param args Command-line arguments; unused.
     */
    public static void main(String[] args) {
        new Neil("data/neil.txt").run();
    }
}
