/**
 * Adds a ToDo task.
 */
public class AddToDoCommand extends Command {
    private final String description;
    public AddToDoCommand(String description) {
        this.description = description;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        if (description == null || description.trim().isEmpty()) {
            throw new CathyException("Todo description cannot be empty. Use: todo <description>");
        }
        ToDo t = new ToDo(description.trim());
        tasks.add(t);
        storage.save(tasks);
        ui.showAdd(t, tasks.size());
    }
}
