/**
 * Represents a {@link Task} with a specific deadline.
 * <p>
 * A {@code Deadline} task has a description (inherited from {@link Task})
 * and an additional {@code by} field that indicates the due date or time.
 */
public class Deadline extends Task {

    protected String by;
    protected TaskType type;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.type = TaskType.DEADLINE;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}