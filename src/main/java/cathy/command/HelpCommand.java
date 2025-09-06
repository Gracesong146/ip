package cathy.command;

import cathy.Ui;
import cathy.exception.CathyException;
import cathy.storage.Storage;
import cathy.task.TaskList;

/**
 * Command that displays help/usage information for the Cathy application.
 *
 * <p><strong>Expected input</strong>:
 * <pre>{@code
 * help
 * }</pre>
 *
 * <p>This command does not modify the {@link TaskList} and does not persist anything to {@link Storage}.
 */
public class HelpCommand extends Command {

    /**
     * Shows a concise list of supported commands and their usage via {@link Ui#showHelp()}.
     *
     * @param tasks   the current task list (unused)
     * @param ui      the user interface used to display help text
     * @param storage the storage handler (unused)
     * @throws CathyException never thrown in normal operation
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        return ui.showHelp();
    }
}
