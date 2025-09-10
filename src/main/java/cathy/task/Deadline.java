package cathy.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cathy.exception.InvalidDateTimeException;

/**
 * Represents a {@link Task} with a specific deadline.
 *
 * <p>A {@code Deadline} task has:
 * <ul>
 *   <li>a description (inherited from {@link Task}), and</li>
 *   <li>a {@code by} field that stores the due date/time as a {@link LocalDateTime}.</li>
 * </ul>
 *
 * <p>The input string for {@code by} can be parsed in multiple formats:
 * <ul>
 *   <li>ISO-8601 (default, e.g. {@code 2025-09-10T23:59})</li>
 *   <li>{@code yyyy-MM-dd HHmm} (e.g. {@code 2025-09-10 2359})</li>
 *   <li>{@code yyyy-MM-dd} (date-only, defaulting to midnight)</li>
 * </ul>
 *
 * <p>Separators {@code "/"} are normalized to {@code "-"} before parsing.
 * If the string cannot be parsed, an {@link InvalidDateTimeException} is thrown.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    protected TaskType type;

    /**
     * Constructs a new {@code Deadline} task with the specified description and deadline.
     *
     * @param description the description of the task
     * @param by          the due date or time string. Accepts ISO-8601 or supported custom formats.
     * @throws InvalidDateTimeException if the {@code by} string cannot be parsed
     */
    public Deadline(String description, String by) {
        super(description);
        assert by != null : "Deadline: by must be parsed";

        // Normalize separators
        String s = by.trim().replace("/", "-");

        // Try ISO first
        try {
            this.by = LocalDateTime.parse(s); // ISO-8601
            this.type = TaskType.DEADLINE;
            return;
        } catch (Exception ignored) { }

        // Try explicit datetime patterns
        DateTimeFormatter[] dt = new DateTimeFormatter[] {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        };
        for (DateTimeFormatter f : dt) {
            try {
                this.by = LocalDateTime.parse(s, f);
                this.type = TaskType.DEADLINE;
                return;
            } catch (Exception ignored) { }
        }

        // Try date-only, default to 23:59
        try {
            LocalDate d = LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            this.by = d.atTime(23, 59);
            this.type = TaskType.DEADLINE;
            return;
        } catch (Exception ignored) { }

        throw new InvalidDateTimeException("Could not parse deadline date/time: " + by);
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        assert by != null : "Deadline: by must not be null";
        this.by = by;
        this.type = TaskType.DEADLINE;
    }

    /**
     * Returns the due date/time of this {@code Deadline}.
     *
     * @return the due date/time as a {@link LocalDateTime}
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of this {@code Deadline}.
     * The output includes the task type marker {@code [D]}, the description,
     * and the formatted due date/time.
     *
     * @return formatted string for display
     */
    @Override
    public String toString() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
        return "[D]" + super.toString() + " (by: " + this.by.format(outputFormat) + ")";
    }
}
