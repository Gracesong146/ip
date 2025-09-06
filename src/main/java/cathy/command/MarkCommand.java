package cathy.command;

import cathy.Ui;
import cathy.exception.CathyException;
import cathy.storage.Storage;
import cathy.task.Task;
import cathy.task.TaskList;

/**
 * Command that marks a {@link Task} as done using a 1-based index.
 *
 * <p><strong>Expected input</strong>:
 * <pre>{@code
 * mark <task number>
 * }</pre>
 */
public class MarkCommand extends Command {
    private final int index; // 1-based index from user

    /**
     * Creates a {@code MarkCommand}.
     *
     * @param index the 1-based index of the task to mark as done
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Marks the specified task as done, persists the change via {@link Storage},
     * and shows a confirmation via {@link Ui}.
     *
     * @param tasks   the {@link TaskList} to operate on
     * @param ui      the {@link Ui} used to display feedback
     * @param storage the {@link Storage} used to save the updated list
     * @throws CathyException if the index is out of range or the task is already marked done
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        if (index <= 0 || index > tasks.size()) {
            throw new CathyException("Trying to mark task " + index + " as done? Cute.\n"
                    + "You can't just mark imaginary tasks to feel accomplished.");
        }
        Task t = tasks.get(index - 1);
        if (t.getStatusIcon().equals("X")) {
            throw new CathyException("Darling, that task's already done. No need to be an overachiever.");
        }
        t.markAsDone();
        storage.save(tasks);
        return ui.showMark(t);
    }
}
