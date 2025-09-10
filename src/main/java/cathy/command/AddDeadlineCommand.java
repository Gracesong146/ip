package cathy.command;

import cathy.Ui;
import cathy.exception.CathyException;
import cathy.exception.InvalidTaskTypeException;
import cathy.storage.Storage;
import cathy.task.Deadline;
import cathy.task.TaskList;
import cathy.task.TaskType;

import java.time.LocalDateTime;

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
    private final LocalDateTime by;

    /**
     * Creates an {@code AddDeadlineCommand}.
     *
     * @param description the task description (trimmed during execution).
     *                    If blank, execution throws an {@link InvalidTaskTypeException}.
     * @param by          the DateTime following {@code /by} (trimmed during execution).
     *                    If blank, execution throws a {@link CathyException}.
     */
    public AddDeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }


    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidTaskTypeException(TaskType.DEADLINE);
        }
        if (by == null) {
            throw new CathyException("Seriously? That deadline format is a mess.\n"
                    + "Try again like you actually read the instructions: deadline <desc> /by <date>");
        }
        assert tasks != null : "Command: tasks must not be null";
        assert ui != null : "Command: ui must not be null";
        assert storage != null : "Command: storage must not be null";
        Deadline d = new Deadline(description.trim(), by);
        tasks.add(d);
        storage.save(tasks);
        return ui.showAdd(d, tasks.size());
    }
}
