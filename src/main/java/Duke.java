import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    public static class ToDoList {
        private ArrayList<String> entries;

        public ToDoList() {
            this.entries = new ArrayList<>();
        }

        @Override
        public String toString() {
            String output = "";

            int count = 1;
            for (String entry : entries) {
                output += String.format("%s. %s\n", count++, entry);
            }

            return output;
        }

        public void add(String entry) {
            entries.add(entry);
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

            switch (input) {
                case "list":
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
