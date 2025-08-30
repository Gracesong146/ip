/** Turns raw user input into a (command, args) pair. Keep it tiny first. */
public class Parser {
    public static String action(String input) {
        String s = input.trim().toLowerCase();
        if (s.equals("bye")) return "bye";
        if (s.equals("list")) return "list";
        if (s.startsWith("todo")) return "todo";
        if (s.startsWith("deadline")) return "deadline";
        if (s.startsWith("event")) return "event";
        if (s.startsWith("mark")) return "mark";
        if (s.startsWith("unmark")) return "unmark";
        if (s.startsWith("delete")) return "delete";
        if (s.startsWith("on")) return "on";
        if (s.equals("help")) return "help";
        return "unknown";
    }
}
