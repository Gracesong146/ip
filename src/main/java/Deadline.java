/**
 * Represents a {@link Task} with a specific deadline.
 * <p>
 * A {@code Deadline} task has a description (inherited from {@link Task})
 * and an additional {@code by} field that indicates the due date or time.
 */
public class Deadline extends Task {

    protected String by;
    protected TaskType type;

    /**
     * Constructs a new {@code Deadline} task with the specified description and deadline.
     *
     * @param description the description of the task
     * @param by the due date or time by which the task should be completed
     */
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