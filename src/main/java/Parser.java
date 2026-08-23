import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;

/**
 * Converts raw user input into values understood by the application.
 */
public final class Parser {
    private static final Set<String> TASK_COMMANDS =
            Set.of("todo", "deadline", "event");

    private Parser() {
        // Prevent construction of this utility class.
    }

    public static Task parseTask(String input) throws NeilException {
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
        if (!TASK_COMMANDS.contains(command)) {
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
                try {
                    LocalDate deadline = LocalDate.parse(deadlineParts[1]);
                    return new DeadlineTask(deadlineParts[0], deadline);
                } catch (DateTimeParseException e) {
                    throw new NeilException("Please provide a valid date in yyyy-MM-dd format");
                }

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

    public static int parseTaskNumber(String[] parts) throws NeilException {
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

        return taskNumber;
    }
}