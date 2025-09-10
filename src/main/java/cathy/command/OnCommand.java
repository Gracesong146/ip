package cathy.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

import cathy.Ui;
import cathy.exception.CathyException;
import cathy.storage.Storage;
import cathy.task.Deadline;
import cathy.task.Event;
import cathy.task.Task;
import cathy.task.TaskList;

/**
 * Command that lists all {@link Deadline} and {@link Event} tasks occurring on a specific date.
 *
 * <p><strong>Expected input</strong>:
 * <pre>{@code
 * on yyyy-MM-dd
 * }</pre>
 */
public class OnCommand extends Command {
    private final String arg;

    /**
     * Creates an {@code OnCommand}.
     *
     * @param arg the date string provided by the user (slashes "/" will be normalized to "-")
     */
    public OnCommand(String arg) {
        this.arg = arg == null ? "" : arg.trim();
    }

    /**
     * Displays all {@link Deadline} and {@link Event} tasks that occur on the given date.
     *
     * @param tasks   the {@link TaskList} to search
     * @param ui      the {@link Ui} used to display results
     * @param storage the {@link Storage} (unused, since no changes are made)
     * @throws CathyException if the argument is missing or cannot be parsed into a valid date
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        StringBuilder returnMessage = new StringBuilder();
        if (arg.isEmpty()) {
            throw new CathyException("Use: on yyyy-MM-dd");
        }
        try {
            assert tasks != null : "Command: tasks must not be null";
            assert ui != null : "Command: ui must not be null";
            assert storage != null : "Command: storage must not be null";
            String dateStr = arg.replace("/", "-");
            LocalDate queryDate = LocalDate.parse(dateStr);

            String matches = tasks.getTasks().stream()
                    .filter(t -> (t instanceof Deadline
                            && ((Deadline) t).getBy().toLocalDate().isEqual(queryDate))
                            || (t instanceof Event && occursOn((Event) t, queryDate)))
                    .map(Task::toString)
                    .collect(Collectors.joining("\n  "));

            if (matches.isEmpty()) {
                return "Nothing on that day. Must be nice to be free for once.";
            }

            return "Tasks happening on " + queryDate + ":\n  " + matches;

        } catch (DateTimeParseException e) {
            throw new CathyException("That date makes no sense. Use yyyy-MM-dd. Try again.");
        }
    }

    private boolean occursOn(Event e, LocalDate date) {
        LocalDate start = e.getFrom().toLocalDate();
        LocalDate end = e.getTo().toLocalDate();
        return (date.isEqual(start) || date.isAfter(start))
                && (date.isEqual(end) || date.isBefore(end));
    }
}
