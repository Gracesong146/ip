package cathy.exception;

import cathy.task.TaskType;

/**
 * Exception thrown when a task of a specific {@link TaskType}
 * is provided with an invalid or incomplete description.
 * <p>
 * This exception customizes its error message depending on
 * the type of task (TODO, DEADLINE, or EVENT) that triggered it.
 */
public class InvalidTaskTypeException extends CathyException {

    private final TaskType type;

    /**
     * Constructs a new {@code InvalidTaskTypeException} with the
     * specified {@link TaskType}.
     *
     * @param type the type of task that caused the exception
     */
    public InvalidTaskTypeException(TaskType type) {
        super();
        this.type = type;
    }

    /**
     * Returns a customized error message depending on the {@link TaskType}.
     *
     * <ul>
     *   <li>{@link TaskType#TODO}: complains about a missing description</li>
     *   <li>{@link TaskType#DEADLINE}: complains about a malformed deadline format</li>
     *   <li>{@link TaskType#EVENT}: complains about missing /from or /to details</li>
     *   <li>Default: generic gibberish-handling message</li>
     * </ul>
     *
     * @return a sarcastic, task-specific error message
     */
    @Override
    public String getMessage() {
        switch (type) {
        case TODO:
            return "Excuse you! Trying to add a todo with no description?\n"
                    + "Use: todo <desc> and try not to waste my time.";
        case DEADLINE:
            return "Wow. That's not even close to a proper deadline format.\n"
                    + "Use: deadline <desc> /by <date> and try not to waste my time.";
        case EVENT:
            return "'event'... and then silence. Inspiring.\n"
                    + "Try: event <desc> /from <start> /to <end> — give me *something* to work with.";
        default:
            return "Hmm... fascinating gibberish.\n"
                    + "Try again, or type \"help\" to see what I actually understand.";
        }
    }
}
