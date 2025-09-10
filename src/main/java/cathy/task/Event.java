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

        try {
            this.from = LocalDateTime.parse(from); // default = ISO-8601
            this.to = LocalDateTime.parse(to);
            this.type = TaskType.EVENT;
            return;
        } catch (Exception ignore) {
            // fall through to user formats
        }

        // Normalize separators: replace "/" with "-"
        String normalizedFrom = from.replace("/", "-");
        String normalizedTo = to.replace("/", "-");

        // Try patterns in order: datetime first, then date-only
        DateTimeFormatter[] patterns = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        };

        boolean parsed = false;
        for (DateTimeFormatter fmt : patterns) {
            try {
                if (fmt.toString().contains("HH")) {
                    this.from = LocalDateTime.parse(normalizedFrom, fmt);
                    this.to = LocalDateTime.parse(normalizedTo, fmt);
                } else {
                    LocalDate dateFrom = LocalDate.parse(normalizedFrom, fmt);
                    LocalDate dateTo = LocalDate.parse(normalizedTo, fmt);
                    this.from = dateFrom.atStartOfDay();
                    this.to = dateTo.atStartOfDay();
                }
                parsed = true;
                break;
            } catch (Exception e) {
                // keep trying next pattern
            }
        }

        if (!parsed) {
            throw new InvalidDateTimeException();
        }

        // 🔹 Enforce that 'from' is not after 'to'
        if (this.from.isAfter(this.to)) {
            throw new InvalidDateTimeException(
                    "Wow. You think time flows backwards? Cute.\n"
                            + "The /from date has to come *before* the /to date.\n"
                            + "Try again when you figure out how calendars work."
            );
        }

        this.type = TaskType.EVENT;
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
