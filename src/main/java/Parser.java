/**
 * Turns raw user input into a (command, args) pair. Keep it tiny first.
 */
public final class Parser {

    /**
     * All command kinds Cathy understands.
     */
    public enum Action {
        HELP, BYE, LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, ON, UNKNOWN
    }

    /**
     * Parsed result container.
     */

    public static final class Parsed {
        public final Action action;
        public final String raw;        // original input (trimmed)
        public final String description;
        public final String by;         // for deadline
        public final String from;       // for event
        public final String to;         // for event
        public final String index;      // for mark/unmark/delete (string so Cathy can error nicely)
        public final String dateStr;    // for "on <date>"
        public final String error;      // lightweight parse-time error (missing parts etc.)

        private Parsed(Action action, String raw, String description, String by,
                       String from, String to, String index, String dateStr, String error) {
            this.action = action;
            this.raw = raw;
            this.description = description;
            this.by = by;
            this.from = from;
            this.to = to;
            this.index = index;
            this.dateStr = dateStr;
            this.error = error;
        }

        public static Parsed of(Action a, String raw) {
            return new Parsed(a, raw, null, null, null, null, null, null, null);
        }

        public Parsed withDesc(String desc) {
            return new Parsed(this.action, this.raw, desc, this.by, this.from, this.to, this.index, this.dateStr, this.error);
        }

        public Parsed withBy(String by) {
            return new Parsed(this.action, this.raw, this.description, by, this.from, this.to, this.index, this.dateStr, this.error);
        }

        public Parsed withFromTo(String from, String to) {
            return new Parsed(this.action, this.raw, this.description, this.by, from, to, this.index, this.dateStr, this.error);
        }

        public Parsed withIndex(String idx) {
            return new Parsed(this.action, this.raw, this.description, this.by, this.from, this.to, idx, this.dateStr, this.error);
        }

        public Parsed withDate(String d) {
            return new Parsed(this.action, this.raw, this.description, this.by, this.from, this.to, this.index, d, this.error);
        }

        public Parsed withError(String e) {
            return new Parsed(this.action, this.raw, this.description, this.by, this.from, this.to, this.index, this.dateStr, e);
        }
    }

    private Parser() {
    } // prevent new Parser()

    /**
     * Parse a raw command line into a {@link Parsed} instruction.
     */
    public static Parsed parse(String input) {
        if (input == null) {
            input = "";
        }
        final String raw = input.trim();
        if (raw.isEmpty()) {
            return Parsed.of(Action.UNKNOWN, raw);
        }

        // first word is action
        String[] firstSplit = raw.split("\\s+", 2);
        String command = firstSplit[0].toLowerCase();
        String args = (firstSplit.length > 1) ? firstSplit[1].trim() : "";

        switch (command) {
        case "help":
            return Parsed.of(Action.HELP, raw);
        case "bye":
            return Parsed.of(Action.BYE, raw);
        case "list":
            return Parsed.of(Action.LIST, raw);
        case "todo":
            if (args.isEmpty()) {
                return Parsed.of(Action.TODO, raw).withError("Empty description for todo.");
            }
            return Parsed.of(Action.TODO, raw).withDesc(args);
        case "deadline":
            if (args.isEmpty()) {
                return Parsed.of(Action.DEADLINE, raw).withError("Empty description for deadline.");
            }
            String[] parts = args.split(" /by ", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                return Parsed.of(Action.DEADLINE, raw).withError("Use: deadline <desc> /by <date>");
            }
            return Parsed.of(Action.DEADLINE, raw)
                    .withDesc(parts[0].trim())
                    .withBy(parts[1].trim());
        case "event":
            if (args.isEmpty()) {
                return Parsed.of(Action.EVENT, raw).withError("Empty description/time for event.");
            }
            // expected: <desc> /from <start> /to <end>
            String[] a = args.split(" /from ", 2);
            if (a.length < 2 || a[0].trim().isEmpty()) {
                return Parsed.of(Action.EVENT, raw).withError("Use: event <desc> /from <start> /to <end>");
            }
            String desc = a[0].trim();
            String rest = a[1].trim();
            String[] b = rest.split(" /to ", 2);
            if (b.length < 2 || b[0].trim().isEmpty() || b[1].trim().isEmpty()) {
                return Parsed.of(Action.EVENT, raw).withError("Use: event <desc> /from <start> /to <end>");
            }
            return Parsed.of(Action.EVENT, raw)
                    .withDesc(desc)
                    .withFromTo(b[0].trim(), b[1].trim());
        case "mark":
            if (args.isEmpty()) {
                return Parsed.of(Action.MARK, raw).withError("Use: mark <number>");
            }
            return Parsed.of(Action.MARK, raw).withIndex(args);
        case "unmark":
            if (args.isEmpty()) {
                return Parsed.of(Action.UNMARK, raw).withError("Use: unmark <number>");
            }
            return Parsed.of(Action.UNMARK, raw).withIndex(args);
        case "delete":
            if (args.isEmpty()) {
                return Parsed.of(Action.DELETE, raw).withError("Use: delete <number>");
            }
            return Parsed.of(Action.DELETE, raw).withIndex(args);
        case "on":
            if (args.isEmpty()) {
                return Parsed.of(Action.ON, raw).withError("Use: on <yyyy-MM-dd>");
            }
            return Parsed.of(Action.ON, raw).withDate(args.replace("/", "-"));
        default:
            return Parsed.of(Action.UNKNOWN, raw);
        }
    }

}
