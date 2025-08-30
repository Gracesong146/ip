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

    /**
     * Constructs a new {@code Event} task with the specified description,
     * start time, and end time.
     *
     * @param description the description of the task
     * @param from the start time of the event
     * @param to the end time of the event
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        this.type = TaskType.EVENT;
    }

    /**
     * Returns the start time of this event.
     *
     * @return a String representing the start time
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns the end time of this event.
     *
     * @return a String representing the end time
     */
    public String getFrom() {
        return from;
    }
    public void setTo(String to) {}

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}