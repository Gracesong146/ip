package cathy;

import java.util.regex.*;
import cathy.command.*;
import cathy.exception.CathyException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses raw user input into {@link Command} objects for execution.
 *
 * <p>The {@code Parser} is responsible for:
 * <ul>
 *   <li>Splitting the input string into a command word and arguments</li>
 *   <li>Constructing the correct {@link Command} subclass (e.g., {@link AddToDoCommand}, {@link DeleteCommand})</li>
 *   <li>Throwing {@link CathyException} for invalid or unrecognized input</li>
 * </ul>
 */
public class Parser {

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
        String[] parts = trimmed.split("\s+", 2);
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
            // Split by "/by"
            String[] segs = args.split("\s+/by\s+", 2);
            if (segs.length < 2) {
                throw new CathyException("Seriously? That deadline format is a mess.\n" +
                        "     Try again like you actually read the instructions: deadline <desc> /by <date>");
            }
            return new AddDeadlineCommand(segs[0], segs[1]);
        }

        case "event": {
            // Split by "/from" and "/to"
            Pattern p = Pattern.compile("^(.*?)\s+/from\s+(.*?)\s+/to\s+(.*)$");
            Matcher m = p.matcher(args);
            if (!m.matches()) {
                throw new CathyException("Invalid event format. Did you even try?\n" +
                        "     Use: event <desc> /from <start> /to <end> — it's not that hard.");
            }
            return new AddEventCommand(m.group(1), m.group(2), m.group(3));
        }

        case "mark":
            return new MarkCommand(parseIndex(args));
        case "unmark":
            return new UnmarkCommand(parseIndex(args));
        case "delete":
            return new DeleteCommand(parseIndex(args));
        case "on":
            return new OnCommand(args);
        case "help":
            return new HelpCommand();
        default:
            throw new CathyException("Hmm... fascinating gibberish.\n" +
                    "     Try again, or type \"help\" to see what I actually understand.");
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
            throw new CathyException("Sweetie, numbers only. This isn't a spelling bee.\n" +
                    "     Use format: [command] [number]");
        }
    }
}
