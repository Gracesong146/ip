/**
 * Deletes a task by 1-based index.
 */
public class DeleteCommand extends Command {
    private final int index;
    private final String description;

    public DeleteCommand(int index) {
        this.index = index;
        this.description = null;
    }

    public DeleteCommand(String description) {
        this.index = -1;
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        if (description != null) {
            throw new CathyException("If you're going to delete something, at least give me something valid.\n" +
                    "     Give it in the following form: delete [number]");
        }
        if (index <= 0 || index > tasks.size()) {
            throw new CathyException("Nice try, but that task doesn't even exist.");
        }
        Task removed = tasks.removeAt(index - 1);
        storage.save(tasks);
        ui.showDelete(removed, tasks.size());
    }
}
