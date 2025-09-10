package cathy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import cathy.command.AddDeadlineCommand;
import cathy.command.AddEventCommand;
import cathy.command.AddToDoCommand;
import cathy.command.ScheduleCommand;
import cathy.command.Command;
import cathy.command.DeleteCommand;
import cathy.command.ExitCommand;
import cathy.command.HelpCommand;
import cathy.command.ListCommand;
import cathy.command.MarkCommand;
import cathy.command.OnCommand;
import cathy.command.UnmarkCommand;
import cathy.exception.CathyException;
import cathy.exception.InvalidDateTimeException;

/**
 * Parses raw user input into {@link Command} objects for execution.
 *
 * <p>The {@code Parser} is responsible for:
 * <ul>
 *   <li>Splitting the input string into a command word and arguments</li>
 *   <li>Constructing the correct {@link Command} subclass
 *   (e.g., {@link AddToDoCommand}, {@link DeleteCommand})</li>
 *   <li>Throwing {@link CathyException} for invalid or unrecognized input</li>
 * </ul>
 */
public class Parser {

    private static final DateTimeFormatter[] DT_PATTERNS = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,           // 2025-09-15T23:59
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),  // 2025-09-15 2359
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")  // 2025-09-15 23:59
            // date-only handled separately below
    };

    /**
     * Parses a raw user input string and returns the corresponding {@link Command}.
     *
     * @param input the raw input string typed by the user
     * @return a {@link Command} representing the user's intent
     * @throws CathyException if the input is null, empty, malformed, or unrecognized
     */
    public static Command parse(String input) throws CathyException {
        if (input == null) {
            throw new CathyException("My brain can't read your mind. Type something.");
        }
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            throw new CathyException("My brain can't read your mind. Type something.");
        }

        // Split into command word and arguments
        String[] parts = trimmed.split("\\s+", 2);
        String cmd = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";

        switch (cmd) {
        case "bye":
            return new ExitCommand();

        case "list":
            return new ListCommand();

        case "todo":
            return new AddToDoCommand(args);

        case "deadline": {
            int byIndex = args.indexOf("/by");
            if (byIndex < 0) throw new CathyException("Need '/by'. Try: deadline <desc> /by <date or date time>");

            String desc  = args.substring(0, byIndex).trim();
            String byStr = args.substring(byIndex + 3).trim();
            if (desc.isEmpty() || byStr.isEmpty()) throw new CathyException("Fill in both description and /by.");

            ParsedDT p = parseDateTimeOrDate(byStr);
            LocalDateTime by = p.hasTime()
                    ? LocalDateTime.of(p.date(), p.time())
                    : p.date().atTime(23, 59);                 // default for date-only deadlines

            return new AddDeadlineCommand(desc, by);
        }

        case "event": {
            int fromAt = args.indexOf("/from");
            int toAt   = args.indexOf("/to");
            if (fromAt < 0 || toAt < 0 || toAt < fromAt) {
                throw new CathyException("Use: event <desc> /from <date [time]> /to <date [time]>");
            }

            String desc    = args.substring(0, fromAt).trim();
            String fromStr = args.substring(fromAt + 6, toAt).trim();
            String toStr   = args.substring(toAt + 3).trim();
            if (desc.isEmpty() || fromStr.isEmpty() || toStr.isEmpty()) {
                throw new CathyException("Missing description/from/to.");
            }

            ParsedDT pf = parseDateTimeOrDate(fromStr);
            ParsedDT pt = parseDateTimeOrDate(toStr);

            LocalDateTime from = pf.hasTime() ? LocalDateTime.of(pf.date(), pf.time())
                    : pf.date().atStartOfDay();       // 00:00
            LocalDateTime to   = pt.hasTime() ? LocalDateTime.of(pt.date(), pt.time())
                    : pt.date().atTime(23, 59);       // 23:59

            if (to.isBefore(from)) {
                throw new InvalidDateTimeException(
                        "Wow. You think time flows backwards? Cute.\n"
                                + "The /from date has to come *before* the /to date.\n"
                                + "Try again when you figure out how calendars work.");
            }

            return new AddEventCommand(desc, from, to);
        }
        case "mark":
            return new MarkCommand(parseIndex(args));
        case "unmark":
            return new UnmarkCommand(parseIndex(args));
        case "delete":
            return new DeleteCommand(parseIndex(args));
        case "on":
            return new OnCommand(args);
        case "find":
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new CathyException("Pro tip: 'find' only works if you give me something to find.");
            }
            String keyword = parts[1].trim();
            return new cathy.command.FindCommand(keyword);
        case "sch":
            if (args.isBlank()) {
                throw new CathyException("'sch' needs a date, not empty air.\n"
                        + "Try: sch YYYY-MM-DD or sch today");
            }
            LocalDate date;
            if (args.equals("today")) {
                date = LocalDate.now();
            } else {
                date = LocalDate.parse(args.trim().replace("/", "-"));
            }
            return new ScheduleCommand(date);
        case "help":
            return new HelpCommand();
        default:
            throw new CathyException("Hmm... fascinating gibberish.\n"
                    + "Try again, or type \"help\" to see what I actually understand.");
        }
    }

    /**
     * Parses a string argument into a task index.
     *
     * @param args the raw string expected to contain a number
     * @return the parsed integer index
     * @throws CathyException if the argument is missing or not a valid integer
     */
    private static int parseIndex(String args) throws CathyException {
        try {
            return Integer.parseInt(args.trim());
        } catch (Exception e) {
            throw new CathyException("Sweetie, numbers only. This isn't a spelling bee.\n"
                    + "Use format: [command] [number]");
        }
    }

    private static record ParsedDT(LocalDate date, LocalTime time, boolean hasTime) {}

    private static ParsedDT parseDateTimeOrDate(String raw) throws CathyException {
        String s = raw.trim().replace("/", "-");
        // Try date+time formats first
        for (DateTimeFormatter f : DT_PATTERNS) {
            try {
                LocalDateTime dt = LocalDateTime.parse(s, f);
                return new ParsedDT(dt.toLocalDate(), dt.toLocalTime(), true);
            } catch (DateTimeParseException ignored) {}
        }
        // Fall back to date-only (ISO date)
        try {
            LocalDate d = LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            return new ParsedDT(d, LocalTime.MIDNIGHT, false);
        } catch (DateTimeParseException e) {
            throw new CathyException("Nope. '" + raw + "' is not a valid date/time.\n"
                    + "Try yyyy-MM-dd HHmm, yyyy-MM-dd HH:mm, or just yyyy-MM-dd.");
        }
    }
}
