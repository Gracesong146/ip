package cathy.command;

import cathy.Ui;
import cathy.storage.Storage;
import cathy.task.Deadline;
import cathy.task.TaskList;
import cathy.task.TaskType;
import cathy.exception.CathyException;
import cathy.exception.InvalidTaskTypeException;

/**
 * Command that adds a {@link Deadline} task to the task list.
 *
 * <p><strong>Expected input format</strong>:
 * <pre>{@code
 * deadline <desc> /by <yyyy-MM-ddTHH:mm>
 * }</pre>
 * The actual parsing/validation of the "by" string is delegated to the {@link Deadline} constructor.
 */
public class AddDeadlineCommand extends Command {
    private final String description;
    private final String by;

    /**
     * Creates an {@code AddDeadlineCommand}.
     *
     * @param description the task description (trimmed during execution).
     *                    If blank, execution throws an {@link InvalidTaskTypeException}.
     * @param by the date/time string following {@code /by} (trimmed during execution).
     *           If blank, execution throws a {@link CathyException}.
     */
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
        Deadline d = new Deadline(description.trim(), by.trim());
        tasks.add(d);
        storage.save(tasks);
        ui.showAdd(d, tasks.size());

    }
}
