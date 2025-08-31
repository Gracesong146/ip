import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
/**
 * Adds a Deadline task.
 * Expected format: deadline <desc> /by <yyyy-MM-ddTHH:mm> or any format your parser/storage supports.
 */
public class AddDeadlineCommand extends Command {
    private final String description;
    private final String by;
    public AddDeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidTaskTypeException(TaskType.DEADLINE);
        }
        if (by == null || by.trim().isEmpty()) {
            throw new CathyException("Seriously? That deadline format is a mess.\n" +
                    "     Try again like you actually read the instructions: deadline <desc> /by <date>");
        }
        try {
            Deadline d = new Deadline(description.trim(), by.trim());
            tasks.add(d);
            storage.save(tasks);
            ui.showAdd(d, tasks.size());
        } catch (InvalidDateTimeException e) {
            ui.showError("Invalid date/time: " + e.getMessage());
        }
    }
}
