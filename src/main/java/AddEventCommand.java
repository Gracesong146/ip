/**
 * Adds an Event task.
 * Expected format: event <desc> /from <...> /to <...>
 */
public class AddEventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;
    public AddEventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidTaskTypeException(TaskType.EVENT);
        }
        if (from == null || from.trim().isEmpty() || to == null || to.trim().isEmpty()) {
            throw new CathyException("Invalid event format. Did you even try?\n" +
                    "     Use: event <desc> /from <start> /to <end> — it's not that hard.");
        }
        try {
            Event e = new Event(description.trim(), from.trim(), to.trim());
            tasks.add(e);
            storage.save(tasks);
            ui.showAdd(e, tasks.size());
        } catch (InvalidDateTimeException e) {
            ui.showError("Invalid date/time: " + e.getMessage());
        }
    }
}
