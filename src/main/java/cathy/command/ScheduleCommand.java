package cathy.command;

import cathy.Ui;
import cathy.storage.Storage;
import cathy.task.Deadline;
import cathy.task.Event;
import cathy.task.Task;
import cathy.task.TaskList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Command that lists all tasks in the current {@link TaskList} that happens on specific date(s).
 *
 * <p><strong>Expected input</strong>:
 * <pre>{@code
 * cal yyyy-MM-dd
 * }</pre>
 */
public class ScheduleCommand extends Command {
    private final LocalDate date;

    /**
     * Creates an {@code CalendarCommand}.
     *
     * @param date the date string provided by the user (slashes "/" will be normalized to "-")
     */
    public ScheduleCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null && ui != null && storage != null : "Deps must be wired";

        // Build the list of tasks that occur on 'date', pretty-print them, and join with newlines.
        String lines = tasks.getTasks().stream()
                .filter(t -> occursOnDate(t, date))
                .sorted(taskOrder())
                .map(t -> "  " + formatForSchedule(t, date))
                .collect(Collectors.joining("\n"));

        if (lines.isEmpty()) {
            return "Nothing on " + date + ". Must be nice to be free for once.";
        }
        return "Schedule for " + date + ":\n" + lines;
    }

    /** Returns true if the task should appear on the given date. */
    private static boolean occursOnDate(Task t, LocalDate d) {
        if (t instanceof Deadline dl) {
            return dl.getBy().toLocalDate().isEqual(d);
        } else if (t instanceof Event ev) {
            LocalDate s = ev.getFrom().toLocalDate();
            LocalDate e = ev.getTo().toLocalDate();
            return !d.isBefore(s) && !d.isAfter(e); // inclusive span
        } else {
            // ToDo has no date; omit from single-day schedule (change if you want them shown)
            return false;
        }
    }

    /** Order: Event by start time, then Deadline by time, then ToDo by description. */
    private static Comparator<Task> taskOrder() {
        return (a, b) -> {
            int ra = rank(a), rb = rank(b);
            if (ra != rb) return Integer.compare(ra, rb);
            if (a instanceof Event ea && b instanceof Event eb) {
                return ea.getFrom().compareTo(eb.getFrom());
            }
            if (a instanceof Deadline da && b instanceof Deadline db) {
                return da.getBy().compareTo(db.getBy());
            }
            return a.getDescription().compareToIgnoreCase(b.getDescription());
        };
    }

    private static int rank(Task t) {
        if (t instanceof Event) return 0;
        if (t instanceof Deadline) return 1;
        return 2; // ToDo (not shown here, but keeps comparator generic)
    }

    /** Format one line for the schedule, showing times when available. */
    private static String formatForSchedule(Task t, LocalDate d) {
        if (t instanceof Event e) {
            LocalDateTime from = e.getFrom();
            LocalDateTime to = e.getTo();
            if (!from.toLocalDate().isEqual(to.toLocalDate())) {
                if (d.isEqual(from.toLocalDate())) {
                    return "[E] " + t.getDescription()
                            + " (from " + time(from) + " - 23:59)";
                } else if (d.isEqual(to.toLocalDate())) {
                    return "[E] " + t.getDescription()
                            + " (00:00 - " + time(to) + ")";
                } else {
                    return "[E] " + t.getDescription() + " (all day segment)";
                }
            }
            return "[E] " + t.getDescription() + " (" + time(from) + "-" + time(to) + ")";
        } else if (t instanceof Deadline dl) {
            return "[D] " + t.getDescription() + " (by " + time(dl.getBy()) + ")";
        } else {
            return "[T] " + t.getDescription();
        }
    }

    private static String time(LocalDateTime dt) {
        return String.format("%02d:%02d", dt.getHour(), dt.getMinute()); // 24h HH:mm
    }
}
