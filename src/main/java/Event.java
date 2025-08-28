/**
 * Represents an {@link Task} that occurs within a specific time range.
 * <p>
 * An {@code Event} task has a description (inherited from {@link Task})
 * along with a start time ({@code from}) and an end time ({@code to}).
 */
public class Event extends Task {

    protected String from;
    protected String to;
    protected TaskType type;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        this.type = TaskType.EVENT;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}