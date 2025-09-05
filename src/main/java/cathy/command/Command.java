package cathy.command;

import cathy.Ui;
import cathy.exception.CathyException;
import cathy.storage.Storage;
import cathy.task.TaskList;

/**
 * Represents a user command in the Cathy task assistant application.
 *
 * <p>All user commands (e.g., {@code AddTodoCommand}, {@code DeleteCommand}, {@code ExitCommand})
 * extend this abstract class and implement the {@link #execute(TaskList, Ui, Storage)} method.
 *
 * <p>The {@code Parser} is responsible for constructing the correct {@code Command} subclass
 * from raw user input. Then, the main loop calls {@link #execute(TaskList, Ui, Storage)} to
 * perform the action.
 */
public abstract class Command {

    /**
     * Executes the command against the provided {@link TaskList}, using {@link Ui}
     * to display results and {@link Storage} to persist changes.
     *
     * @param tasks   the task list to operate on
     * @param ui      the user interface for showing feedback
     * @param storage the storage handler for saving/loading tasks
     * @throws CathyException if execution fails due to invalid input or storage issues
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws CathyException;

    /**
     * Indicates whether this command should terminate the application.
     *
     * <p>By default, returns {@code false}. Commands that exit the program (such as
     * {@code ExitCommand}) override this to return {@code true}.
     *
     * @return {@code true} if the application should exit after executing this command;
     *     {@code false} otherwise
     */
    public boolean isExit() {
        return false;
    }
}
