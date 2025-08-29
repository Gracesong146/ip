/**
 * Represents a general task with a description and a completion status.
 * <p>
 * This is the base class for more specific task types such as
 * {@link Deadline} and {@link Event}.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new {@code Task} with the given description.
     * By default, a task is not marked as done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon for the task.
     * <ul>
     *   <li>{@code "X"} if the task is marked as done.</li>
     *   <li>{@code " "} (a blank space) if the task is not done.</li>
     * </ul>
     *
     * @return The status icon representing completion state.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as done by setting its status to completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done by resetting its completion status.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of this task,
     * including its status icon and description.
     *
     * @return A formatted string in the form:
     *         {@code [status] description}.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}