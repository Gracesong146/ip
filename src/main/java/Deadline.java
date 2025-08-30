
/**
 * Represents a {@link Task} with a specific deadline.
 * <p>
 * A {@code Deadline} task has a description (inherited from {@link Task})
 * and an additional {@code by} field that indicates the due date or time.
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    protected TaskType type;

    /**
     * Constructs a new {@code Deadline} task with the specified description and deadline.
     *
     * @param description the description of the task
     * @param by the due date or time by which the task should be completed
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
        DateTimeFormatter[] patterns = new DateTimeFormatter[] {
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
            throw new InvalidDateTimeException("Your date/time format is wrong.\n" +
                    "     Use yyyy-MM-dd or yyyy-MM-dd HHmm. It's not that hard.\n" +
                    "    ____________________________________________________________\n");
        }
    }


    /**
     * Returns the due date/time of this deadline task.
     *
     * @return a String representing the due date/time
     */
    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
        return "[D]" + super.toString() + " (by: " + this.by.format(outputFormat) + ")";
    }
}