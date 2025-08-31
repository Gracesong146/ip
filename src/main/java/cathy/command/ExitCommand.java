package cathy.command;

import cathy.Ui;
import cathy.storage.Storage;
import cathy.task.TaskList;

/**
 * Command that exits the Cathy application.
 *
 * <p><strong>Expected input</strong>:
 * <pre>{@code
 * bye
 * }</pre>
 */
public class ExitCommand extends Command {

    /**
     * Shows a farewell message via {@link Ui}. No changes are made to {@link TaskList}
     * and nothing is persisted to {@link Storage}.
     *
     * @param tasks   the current task list (unused)
     * @param ui      the user interface for displaying the goodbye message
     * @param storage the storage handler (unused)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    /**
     * Indicates that the application should terminate after this command runs.
     *
     * @return {@code true}, always
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
