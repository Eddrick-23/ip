import java.util.Scanner;

public class Neil {
    private static Storage storage = new Storage("./data/neil.txt");
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

        try {
            for (Task task : storage.load()) {
                toDoList.add(task);
            }
        } catch (NeilException e) {
            System.out.println(e.getMessage());
            return;
        }

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
                        int taskNumber = Parser.parseTaskNumber(parts);
                        Task task = toDoList.markTaskAsDone(taskNumber);
                        storage.save(toDoList.getTasks());
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(task);
                        break;
                    }
                    case "unmark": {
                        int taskNumber = Parser.parseTaskNumber(parts);
                        Task task= toDoList.unmarkTask(taskNumber);
                        storage.save(toDoList.getTasks());
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println(task);
                        break;
                    }
                    case "delete": {
                        int taskNumber = Parser.parseTaskNumber(parts);
                        Task task = toDoList.remove(taskNumber);
                        storage.save(toDoList.getTasks());
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
                        Task task = Parser.parseTask(input);
                        toDoList.add(task);
                        storage.save(toDoList.getTasks());
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
