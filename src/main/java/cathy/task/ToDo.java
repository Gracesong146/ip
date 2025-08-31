package cathy.task;

/**
 * Represents a simple {@link Task} with only a description.
 *
 * <p>A {@code ToDo} task:
 * <ul>
 *   <li>has a textual description (inherited from {@link Task}),</li>
 *   <li>is tagged with {@link TaskType#TODO},</li>
 *   <li>does not have deadlines or time ranges (unlike {@link Deadline} and {@link Event}).</li>
 * </ul>
 */
public class ToDo extends Task {

    protected TaskType type;

    /**
     * Constructs a new {@code ToDo} task with the specified description.
     *
     * @param description the description of the task
     */
    public ToDo(String description) {
        super(description);
        this.type = TaskType.TODO;
    }

    /**
     * Returns a string representation of this {@code ToDo}.
     * The output includes the task type marker {@code [T]} followed by
     * the base {@link Task#toString()} output (description and status).
     *
     * @return formatted string for display
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}