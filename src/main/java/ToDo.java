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