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

    @Override
    public String getMessage() {
        switch (type) {
        case TODO:
            return "Excuse you—trying to add a todo with no description?\n" +
                    "     Use: todo <desc> and try not to waste my time.";
        case DEADLINE:
            return "Wow. That’s not even close to a proper deadline format.\n" +
                    "     Use: deadline <desc> /by <date> and try not to waste my time.";
        case EVENT:
            return "'event'... and then silence. Inspiring.\n" +
                    "     Try: event <desc> /from <start> /to <end> — give me *something* to work with.";
        default:
            return "Hmm… fascinating gibberish.\n" +
                    "     Try again, or type \"help\" to see what I actually understand.";
        }
    }
}