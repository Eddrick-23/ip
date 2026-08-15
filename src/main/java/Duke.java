import java.util.Scanner;

public class Duke {
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
                    System.out.println("added: " + input);
                    toDoList.add(input);
            }
            System.out.print(divider);
        }

        System.out.print(byeMessage + divider);
        scanner.close();

    }
}
