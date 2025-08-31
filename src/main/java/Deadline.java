import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

        try {
            this.by = LocalDateTime.parse(by); // default = ISO-8601
            this.type = TaskType.DEADLINE;
            return;
        } catch (Exception ignore) {
            // fall through to user formats
        }

        this.type = TaskType.DEADLINE;
        // Normalize separators: replace "/" with "-"
        String normalized = by.replace("/", "-");

        // Try patterns in order: datetime first, then date-only
        DateTimeFormatter[] patterns = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        };

        boolean parsed = false;
        for (DateTimeFormatter fmt : patterns) {
            try {
                if (fmt.toString().contains("HH")) {
                    this.by = LocalDateTime.parse(normalized, fmt);
                } else {
                    LocalDate date = LocalDate.parse(normalized, fmt);
                    this.by = date.atStartOfDay();
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