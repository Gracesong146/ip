/**
 * Represents an {@link Task} that occurs within a specific time range.
 * <p>
 * An {@code Event} task has a description (inherited from {@link Task})
 * along with a start time ({@code from}) and an end time ({@code to}).
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;
    protected TaskType type;

    /**
     * Constructs a new {@code Event} task with the specified description,
     * start time, and end time.
     *
     * @param description the description of the task
     * @param from        the start time of the event
     * @param to          the end time of the event
     */
    public Event(String description, String from, String to) {
        super(description);

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
            throw new InvalidDateTimeException("Your date/time format is wrong.\n" +
                    "     Use yyyy-MM-dd or yyyy-MM-dd HHmm. It's not that hard.");
        }

        // 🔹 Enforce that 'from' is not after 'to'
        if (this.from.isAfter(this.to)) {
            throw new InvalidDateTimeException(
                    "Wow. You think time flows backwards? Cute.\n" +
                            "     The /from date has to come *before* the /to date.\n" +
                            "     Try again when you figure out how calendars work."
            );
        }

        this.type = TaskType.EVENT;
    }

    /**
     * Returns the start time of this event.
     *
     * @return a String representing the start time
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns the end time of this event.
     *
     * @return a String representing the end time
     */
    public LocalDateTime getFrom() {
        return from;
    }

    public void setTo(String to) {
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
        return "[E]" + super.toString() + " (from: " + this.from.format(outputFormat) + " to: " + this.to.format(outputFormat) + ")";
    }
}