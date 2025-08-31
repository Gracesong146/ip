/**
 * Represents a user command.
 */
public abstract class Command {
    /**
     * Executes the command.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws CathyException;

    /**
     * Whether this command should exit the app.
     */
    public boolean isExit() {
        return false;
    }
}
