public class Neil {
    private final Storage storage;
    private final Ui ui;
    private final ToDoList toDoList;

    public Neil(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        this.toDoList = new ToDoList();
    }

    public void run() {
        try {
            for (Task task : storage.load()) {
                toDoList.add(task);
            }
        } catch (NeilException e) {
            System.out.println(e.getMessage());
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
                        ui.showMessage(String.format("Nice! I've marked this task as done:\n%s", task));
                        break;
                    }
                    case "unmark": {
                        int taskNumber = Parser.parseTaskNumber(parts);
                        Task task= toDoList.unmarkTask(taskNumber);
                        storage.save(toDoList.getTasks());
                        ui.showMessage(String.format("OK, I've marked this task as not done yet:\n%s", task));
                        break;
                    }
                    case "delete": {
                        int taskNumber = Parser.parseTaskNumber(parts);
                        Task task = toDoList.remove(taskNumber);
                        storage.save(toDoList.getTasks());
                        String msg = String.format(
                                "Noted. I've removed this task:\n%s\nNow you have %d tasks in this list.",
                                task,
                                toDoList.size()
                        );
                        ui.showMessage(msg);
                        break;
                    }
                    case "list": {
                        String msg = String.format("Here are the tasks in your list:\n%s", toDoList);
                        ui.showMessage(msg);
                        break;
                    }
                    default:
                        Task task = Parser.parseTask(input);
                        toDoList.add(task);
                        storage.save(toDoList.getTasks());
                        String msg = String.format("Got it. I've added this task:\n%s\n", task);
                        msg += "Now you have " + toDoList.size() + " tasks in the list.";
                        ui.showMessage(msg);
                }
            } catch (NeilException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.showGoodbye();
        ui.close();
    }

    public static void main(String[] args) {
        new Neil("data/neil.txt").run();
    }
}
