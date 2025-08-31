/**
 * Marks a task as done.
 */
public class MarkCommand extends Command {
    private final int index; // 1-based index from user
    private final String description;

    public MarkCommand(int index) {
        this.index = index;
        this.description = null;
    }

    public MarkCommand(String description) {
        this.index = -1;
        this.description = description;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        if (description != null) {
            throw new CathyException("If you're going to mark something, at least give me something valid.\n" +
                    "     Give it in the following form: mark [number]");
        }
        if (index <= 0 || index > tasks.size()) {
            throw new CathyException("Trying to mark task " + index + " as done? Cute.\n" +
                    "     You can't just mark imaginary tasks to feel accomplished.");
        }
        Task t = tasks.get(index - 1);
        t.markAsDone();
        storage.save(tasks);
        ui.showMark(t);
    }
}
