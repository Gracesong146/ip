package cathy.command;

import cathy.Ui;
import cathy.storage.Storage;
import cathy.task.TaskList;

/**
 * Command that lists all tasks in the current {@link TaskList}.
 *
 * <p><strong>Expected input</strong>:
 * <pre>{@code
 * list
 * }</pre>
 *
 * <p>This command does not modify the task list and does not persist anything to {@link Storage}.
 */
public class ListCommand extends Command {

    /**
     * Displays the full task list via {@link Ui#showList(TaskList)}.
     *
     * @param tasks   the {@link TaskList} to display
     * @param ui      the user interface used to render the list
     * @param storage the storage handler (unused)
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "Command: tasks must not be null";
        assert ui != null : "Command: ui must not be null";
        assert storage != null : "Command: storage must not be null";
        return ui.showList(tasks);
    }
}
