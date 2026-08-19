import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Neil {
    private static HashSet<String> supportedCommands = new HashSet<>(Set.of("todo", "deadline", "event"));
    private static Task parseTask(String input) throws NeilException {
        // split to at most two parts
        // front is the command, remaining is the string to parse
        // to extract descriptions and times.

        // handle empty inputs
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            throw new NeilException("Please provide a command");
        }

        String[] parts = trimmedInput.split("\\s+", 2);
        String command = parts[0];

        // handle unsupported commands
        if (!supportedCommands.contains(command)) {
            throw new NeilException("command " + command + " not supported");
        }

        // handle missing descriptions
        if (parts.length < 2 || parts[1].isBlank()) {
            throw new NeilException("Please provide a task description");
        }

        String arguments = parts[1].trim();

        switch (command) {
            case "todo":
                return new ToDoTask(arguments);
            case "deadline":
                String[] deadlineParts =
                        arguments.split("\\s+/by\\s+", 2);
                if (deadlineParts.length != 2
                        || deadlineParts[0].isBlank()
                        || deadlineParts[1].isBlank()) {
                    throw new NeilException(
                            "Use: deadline DESCRIPTION /by DATE");
                }

                return new DeadlineTask(
                        deadlineParts[0].trim(),
                        deadlineParts[1].trim()
                );

            case "event":
                String[] fromParts =
                        arguments.split("\\s+/from\\s+", 2);

                if (fromParts.length != 2) {
                    throw new NeilException(
                            "Use: event DESCRIPTION /from START /to END");
                }

                String[] toParts =
                        fromParts[1].split("\\s+/to\\s+", 2);

                if (toParts.length != 2
                        || fromParts[0].isBlank()
                        || toParts[0].isBlank()
                        || toParts[1].isBlank()) {
                    throw new NeilException(
                            "Use: event DESCRIPTION /from START /to END");
                }

                return new EventTask(
                        fromParts[0].trim(),
                        toParts[0].trim(),
                        toParts[1].trim()
                );

            default:
                throw new NeilException("Unknown Task type");
        }

    }

    private static int parseTaskNumber(String[] parts , ToDoList toDoList) throws NeilException {
        if (parts.length != 2) {
            throw new NeilException("Please specify a task number.");
        }

        int taskNumber;
        try {
            taskNumber = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new NeilException("The task number must be a positive integer");
        }

        if (taskNumber <= 0) {
            throw new NeilException("The task number must be a positive integer");
        }

        if (!toDoList.taskExists(taskNumber)) {
            throw new NeilException("The task " + taskNumber + " does not exist");
        }

        return taskNumber;
    }
    public static void main(String[] args) {
        String banner = "#   #  #####  #####  #    \n"
                + "##  #  #        #    #    \n"
                + "# # #  ####     #    #    \n"
                + "#  ##  #        #    #    \n"
                + "#   #  #####  #####  #####\n";

        String chatbotName = "Neil";
        String divider = "____________________________________________________________\n";
        String byeMessage = "Bye. Hope to see you again soon!\n";
        String welcomeMessage = String.format(
                divider +
                        "%s\n" +
                        "Hello! I'm %s.\n" +
                        "What can I do for you?\n" +
                        divider, banner, chatbotName
        );

        Scanner scanner = new Scanner(System.in);
        String input = "";

        ToDoList toDoList = new ToDoList();
        System.out.print(welcomeMessage);

        // main loop, simply echoes user input with dividers
        // "bye" exits the loop.
        while (true) {
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.print(divider);
                break;
            }

            String[] parts = input.trim().split("\\s+");
            try {
                switch (parts[0]) {
                    case "mark": {
                        int taskNumber = parseTaskNumber(parts, toDoList);
                        Task task = toDoList.markTaskAsDone(taskNumber); System.out.println("Nice! I've marked this task as done:");
                        System.out.println(task);
                        break;
                    }
                    case "unmark": {
                        int taskNumber = parseTaskNumber(parts, toDoList);
                        Task task= toDoList.unmarkTask(taskNumber);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println(task);
                        break;
                    }
                    case "delete": {
                        int taskNumber = parseTaskNumber(parts, toDoList);
                        Task task = toDoList.remove(taskNumber);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println(task);
                        System.out.println("Now you have " + toDoList.size() + " tasks in the list.");
                        break;
                    }
                    case "list":
                        System.out.println("Here are the tasks in your list:");
                        System.out.print(toDoList);
                        break;
                    default:
                        Task task = parseTask(input);
                        toDoList.add(task);
                        System.out.println("Got it. I've added this task:\n " + task);
                        System.out.println("Now you have " + toDoList.size() + " tasks in the list.");
                }
            } catch (NeilException e) {
                System.out.println(e.getMessage());
            }
            System.out.print(divider);
        }

        System.out.print(byeMessage + divider);
        scanner.close();

    }
}
