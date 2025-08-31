/**
 * Marks a task as done.
 */
public class UnmarkCommand extends Command {
    private final int index; // 1-based index from user
    private final String description;

    public UnmarkCommand(int index) {
        this.index = index;
        this.description = null;
    }

    public UnmarkCommand(String description) {
        this.index = -1;
        this.description = description;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        if (description != null) {
            throw new CathyException("If you're going to unmark something, at least give me something valid.\n" +
                    "     Give it in the following form: unmark [number]");
        }
        if (index <= 0 || index > tasks.size()) {
            throw new CathyException("Ah, clever. But no, that task is imaginary.");
        }
        Task t = tasks.get(index - 1);
        t.markAsNotDone();
        storage.save(tasks);
        ui.showUnmark(t);
    }
}
