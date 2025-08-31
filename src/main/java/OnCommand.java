import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/** Shows tasks happening on a specific date. Usage: on yyyy-MM-dd */
public class OnCommand extends Command {
    private final String arg;

    public OnCommand(String arg) {
        this.arg = arg == null ? "" : arg.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        if (arg.isEmpty()) {
            throw new CathyException("Use: on yyyy-MM-dd");
        }

        try {
            String dateStr = arg.replace("/", "-");
            LocalDate queryDate = LocalDate.parse(dateStr);

            ui.print("Tasks happening on " + queryDate + ":");
            boolean found = false;

            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                if (t instanceof Deadline d) {
                    if (d.getBy().toLocalDate().equals(queryDate)) {
                        ui.print("  " + d);
                        found = true;
                    }
                } else if (t instanceof Event e) {
                    if (occursOn(e, queryDate)) {
                        ui.print("  " + e);
                        found = true;
                    }
                }
            }

            if (!found) {
                ui.print("Nothing on that day. Must be nice to be free for once.");
            }
        } catch (DateTimeParseException e) {
            throw new CathyException("That date makes no sense. Use yyyy-MM-dd. Try again.");
        } finally {
            ui.showLine();
        }
    }

    private boolean occursOn(Event e, LocalDate date) {
        LocalDate start = e.getFrom().toLocalDate();
        LocalDate end = e.getTo().toLocalDate();
        return (date.isEqual(start) || date.isAfter(start))
                && (date.isEqual(end)   || date.isBefore(end));
    }
}