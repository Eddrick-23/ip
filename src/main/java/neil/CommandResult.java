package neil;

import java.util.Objects;

/**
 * Represents the text and outcome of processing a command.
 *
 * @param message user-facing response text.
 * @param isError whether the response describes an error.
 */
public record CommandResult(String message, boolean isError) {
    /**
     * Ensures every result contains response text.
     */
    public CommandResult {
        Objects.requireNonNull(message);
    }

    /**
     * Returns a normal command result containing the specified message.
     *
     * @param message user-facing response text.
     * @return normal command result.
     */
    public static CommandResult normal(String message) {
        return new CommandResult(message, false);
    }

    /**
     * Returns an error command result containing the specified message.
     *
     * @param message user-facing error text.
     * @return error command result.
     */
    public static CommandResult error(String message) {
        return new CommandResult(message, true);
    }
}
