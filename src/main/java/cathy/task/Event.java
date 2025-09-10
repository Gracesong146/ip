package cathy.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cathy.exception.InvalidDateTimeException;

/**
 * Represents a {@link Task} that occurs within a specific time range.
 *
 * <p>An {@code Event} has:
 * <ul>
 *   <li>a description (inherited from {@link Task}),</li>
 *   <li>a start date/time ({@code from}), and</li>
 *   <li>an end date/time ({@code to}).</li>
 * </ul>
 *
 * <p>Input strings for {@code from} and {@code to} can be parsed in multiple formats:
 * <ul>
 *   <li>ISO-8601 (default, e.g. {@code 2025-09-01T18:00})</li>
 *   <li>{@code yyyy-MM-dd HHmm} (e.g. {@code 2025-09-01 1800})</li>
 *   <li>{@code yyyy-MM-dd} (date-only, defaults to start of day)</li>
 * </ul>
 *
 * <p>If parsing fails, an {@link InvalidDateTimeException} is thrown.
 * If {@code from} is after {@code to}, another {@link InvalidDateTimeException} is thrown.
 */
public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;
    protected TaskType type;

    /**
     * Constructs a new {@code Event} with the given description, start time, and end time.
     *
     * @param description the description of the event
     * @param from        the start time string
     * @param to          the end time string
     * @throws InvalidDateTimeException if parsing fails or {@code from} is after {@code to}
     */
    public Event(String description, String from, String to) {
        super(description);
        assert from != null && to != null : "Event: time range must be parsed";

        this.from = parseFlexibleStart(from);
        this.to = parseFlexibleEnd(to);

        if (this.from.isAfter(this.to)) {
            throw new InvalidDateTimeException(
                    "Wow. You think time flows backwards? Cute.\n"
                            + "The /from date has to come *before* the /to date.\n"
                            + "Try again when you figure out how calendars work.");
        }
        this.type = TaskType.EVENT;
    }

    /**
     * Constructs a new {@code Event} with the given description, start time, and end time.
     *
     * @param description the description of the event
     * @param from        the start time LocalDateTime
     * @param to          the end time LocalDateTime
     * @throws InvalidDateTimeException if parsing fails or {@code from} is after {@code to}
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        assert from != null : "Event: from must not be null";
        assert to != null : "Event: to must not be null";
        this.from = from;
        this.to = to;
        this.type = TaskType.EVENT;
    }

    private static LocalDateTime parseFlexibleStart(String raw) {
        String s;
        s = raw.trim().replace("/", "-");
        // ISO datetime
        try {
            return LocalDateTime.parse(s);
        } catch (Exception ignored) {
            // ignored
        }
        // Explicit datetime
        DateTimeFormatter[] dt = new DateTimeFormatter[] {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        };
        for (DateTimeFormatter f : dt) {
            try {
                return LocalDateTime.parse(s, f);
            } catch (Exception ignored) {
                // ignore
            }
        }
        // Date-only -> 00:00
        try {
            LocalDate d = LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            return d.atStartOfDay();
        } catch (Exception e) {
            throw new InvalidDateTimeException("Could not parse event start: " + raw);
        }
    }

    private static LocalDateTime parseFlexibleEnd(String raw) {
        String s = raw.trim().replace("/", "-");
        // ISO datetime
        try {
            return LocalDateTime.parse(s);
        } catch (Exception ignored) {
            // ignore
        }
        // Explicit datetime
        DateTimeFormatter[] dt = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        };
        for (DateTimeFormatter f : dt) {
            try {
                return LocalDateTime.parse(s, f);
            } catch (Exception ignored) {
                // ignore
            }
        }
        // Date-only → 23:59
        try {
            LocalDate d = LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            return d.atTime(23, 59);
        } catch (Exception e) {
            throw new InvalidDateTimeException("Could not parse event end: " + raw);
        }
    }

    /**
     * Returns the start time of this event.
     *
     * @return the start time as a {@link LocalDateTime}
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns the end time of this event.
     *
     * @return the end time as a {@link LocalDateTime}
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns a string representation of this {@code Event}.
     * The output includes the task type marker {@code [E]}, the description,
     * and the formatted start and end date/times.
     *
     * @return formatted string for display
     */
    @Override
    public String toString() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
        return "[E]" + super.toString()
                + " (from: " + this.from.format(outputFormat) + " to: " + this.to.format(outputFormat) + ")";
    }
}
