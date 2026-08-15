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

        System.out.print(welcomeMessage);

        // main loop, simply echoes user input with dividers
        // "bye" exits the loop.
        while (!input.equalsIgnoreCase("bye")) {
            input = scanner.nextLine();
            System.out.println(input);
            System.out.print(divider);
        }

        System.out.print(byeMessage + divider);
        scanner.close();

    }
}
