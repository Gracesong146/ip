/**
 * Command that marks a {@link Task} as not done using a 1-based index.
 *
 * <p><strong>Expected input</strong>:
 * <pre>{@code
 * unmark <task number>
 * }</pre>
 */
public class UnmarkCommand extends Command {
    private final int index; // 1-based index from user

    /**
     * Creates a {@code UnmarkCommand}.
     *
     * @param index the 1-based index of the task to mark as not done
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Marks the specified task as NOT done, persists the change via {@link Storage},
     * and shows a confirmation via {@link Ui}.
     *
     * @param tasks   the {@link TaskList} to operate on
     * @param ui      the {@link Ui} used to display feedback
     * @param storage the {@link Storage} used to save the updated list
     * @throws CathyException if the index is out of range or the task is already marked as not done
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        if (index <= 0 || index > tasks.size()) {
            throw new CathyException("Ah, clever. But no, that task is imaginary.");
        }
        Task t = tasks.get(index - 1);
        if (t.getStatusIcon().equals(" ")) {
            throw new CathyException("Task " + index + " is already unmarked..\n" +
                    "     Stop trying to double negative your way through life.");
        }
        t.markAsNotDone();
        storage.save(tasks);
        ui.showUnmark(t);
    }
}
