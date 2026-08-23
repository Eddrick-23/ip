import java.util.Scanner;

/**
 * Handles input from and output to the user.
 */
public class Ui {
    private static final String DIVIDER =
            "____________________________________________________________";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showWelcome() {
        String banner =
                "#   #  #####  #####  #    \n"
                        + "##  #  #        #    #    \n"
                        + "# # #  ####     #    #    \n"
                        + "#  ##  #        #    #    \n"
                        + "#   #  #####  #####  #####";

        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println();
        System.out.println("Hello! I'm Neil.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);
    }

    public void showGoodbye() {
        System.out.println(
                "Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }

    public void showError(String message) {
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    public void showMessage(String message) {
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    public void close() {
        scanner.close();
    }
}