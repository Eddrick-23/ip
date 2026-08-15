public class Duke {
    public static void main(String[] args) {
        String banner =
                  "#   #  #####  #####  #    \n"
                + "##  #  #        #    #    \n"
                + "# # #  ####     #    #    \n"
                + "#  ##  #        #    #    \n"
                + "#   #  #####  #####  #####\n";

        String chatbotName = "Neil";
        String output = String.format(
                "____________________________________________________________\n" +
                "%s\n" +
                "Hello! I'm %s.\n" +
                "What can I do for you?\n" +
                "____________________________________________________________\n" +
                "Bye. Hope to see you again soon!\n" +
                "____________________________________________________________\n", banner, chatbotName);

        System.out.println(output);

    }
}
