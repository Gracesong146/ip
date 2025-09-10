package cathy.command;

import cathy.Ui;
import cathy.exception.CathyException;
import cathy.storage.Storage;
import cathy.task.Task;
import cathy.task.TaskList;

/**
 * Command that deletes a {@link Task} from the task list by its 1-based index.
 *
 * <p><strong>Expected input format</strong>:
 * <pre>{@code
 * delete <task number>
 * }</pre>
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Creates a {@code DeleteCommand}.
     *
     * @param index the 1-based index of the task to delete.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Removes the task at the specified index from the {@link TaskList},
     * saves the updated list via {@link Storage}, and shows a confirmation via {@link Ui}.
     *
     * @param tasks   the {@link TaskList} to modify
     * @param ui      the {@link Ui} used to display feedback
     * @param storage the {@link Storage} used to save the updated list
     * @throws CathyException if the index is out of range (≤ 0 or greater than the current list size)
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        assert tasks != null : "Command: tasks must not be null";
        assert ui != null : "Command: ui must not be null";
        assert storage != null : "Command: storage must not be null";
        if (index <= 0 || index > tasks.size()) {
            throw new CathyException("Nice try, but that task doesn't even exist.");
        }
        Task removed = tasks.removeAt(index - 1);
        storage.save(tasks);
        return ui.showDelete(removed, tasks.size());
    }
}
