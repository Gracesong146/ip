/**
 * Represents a simple {@link Task} with only a description.
 * <p>
 * A {@code ToDo} task does not have deadlines or time ranges,
 * unlike {@link Deadline} and {@link Event}.
 */
public class ToDo extends Task {

    protected TaskType type;

    public ToDo(String description) {
        super(description);
        this.type = TaskType.TODO;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}