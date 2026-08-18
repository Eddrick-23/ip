import java.util.Scanner;

public class Duke {
    private static Task parseTask(String input) throws IllegalArgumentException {
        // split to at most two parts
        // front is the command, remaining is the string to parse
        // to extract descriptions and times.
        String[] parts = input.trim().split("\\s+", 2);

        if (parts.length < 2) {
            throw new IllegalArgumentException("Please provide a task description.");
        }

        String command = parts[0];
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
                    throw new IllegalArgumentException(
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
                    throw new IllegalArgumentException(
                            "Use: event DESCRIPTION /from START /to END");
                }

                String[] toParts =
                        fromParts[1].split("\\s+/to\\s+", 2);

                if (toParts.length != 2
                        || fromParts[0].isBlank()
                        || toParts[0].isBlank()
                        || toParts[1].isBlank()) {
                    throw new IllegalArgumentException(
                            "Use: event DESCRIPTION /from START /to END");
                }

                return new EventTask(
                        fromParts[0].trim(),
                        toParts[0].trim(),
                        toParts[1].trim()
                );

                default:
                    throw new IllegalArgumentException("Unknown Task type");
        }

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
            switch (parts[0]) {
                case "mark":
                    if (parts.length != 2) {
                        System.out.println("specify a task number");
                        break;
                    }
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        if (!toDoList.taskExists(taskNumber)) {
                            System.out.println("That task number does not exist.");
                            break;
                        }
                        String output = toDoList.markTaskAsDone(taskNumber);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(output);
                    } catch (NumberFormatException e) {
                        System.out.println("That task number must be an integer.");
                    }
                    break;
                case "unmark":
                    if (parts.length != 2) {
                        System.out.println("specify a task number");
                        break;
                    }
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        if (!toDoList.taskExists(taskNumber)) {
                            System.out.println("That task number does not exist.");
                            break;
                        }
                        String output = toDoList.unmarkTask(taskNumber);
                        System.out.println("OK, I've marked this task as done:");
                        System.out.println(output);
                    } catch (NumberFormatException e) {
                        System.out.println("That task number must be an integer.");
                    }
                    break;
                case "list":
                    System.out.println("Here are the tasks in your list:");
                    System.out.print(toDoList);
                    break;
                default:
                    try {
                        Task task = parseTask(input);
                        toDoList.add(task);
                        System.out.println("Got it. I've added this task:\n " + task);
                        System.out.println("Now you have " + toDoList.size() + " tasks in this list.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
            }
            System.out.print(divider);
        }

        System.out.print(byeMessage + divider);
        scanner.close();

    }
}
