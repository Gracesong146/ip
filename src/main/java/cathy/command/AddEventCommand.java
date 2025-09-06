package cathy.command;

import cathy.Ui;
import cathy.exception.CathyException;
import cathy.exception.InvalidTaskTypeException;
import cathy.storage.Storage;
import cathy.task.Event;
import cathy.task.TaskList;
import cathy.task.TaskType;

/**
 * Command that adds an {@link Event} task to the task list.
 *
 * <p><strong>Expected input format</strong>:
 * <pre>{@code
 * event <desc> /from <start> /to <end>
 * }</pre>
 */
public class AddEventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    /**
     * Creates an {@code AddEventCommand}.
     *
     * @param description the event description (trimmed during execution);
     *                    if blank, execution throws an {@link InvalidTaskTypeException}.
     * @param from        the start date/time string (trimmed during execution);
     *                    if blank, execution throws a {@link CathyException}.
     * @param to          the end date/time string (trimmed during execution);
     *                    if blank, execution throws a {@link CathyException}.
     */
    public AddEventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidTaskTypeException(TaskType.EVENT);
        }
        if (from == null || from.trim().isEmpty() || to == null || to.trim().isEmpty()) {
            throw new CathyException("Invalid event format. Did you even try?\n"
                    + "Use: event <desc> /from <start> /to <end> — it's not that hard.");
        }

        Event e = new Event(description.trim(), from.trim(), to.trim());
        tasks.add(e);
        storage.save(tasks);
        return ui.showAdd(e, tasks.size());
    }
}
