/**
 * Command that adds a {@link ToDo} task to the task list.
 *
 * <p><strong>Expected input format</strong>:
 * <pre>{@code
 * todo <description>
 * }</pre>
 */
public class AddToDoCommand extends Command {
    private final String description;

    /**
     * Creates an {@code AddToDoCommand}.
     *
     * @param description the ToDo description (trimmed during execution).
     *                    If blank, {@link #execute(TaskList, Ui, Storage)} will throw
     *                    an {@link InvalidTaskTypeException} with {@link TaskType#TODO}.
     */
    public AddToDoCommand(String description) {
        this.description = description;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidTaskTypeException(TaskType.TODO);
        }
        ToDo t = new ToDo(description.trim());
        tasks.add(t);
        storage.save(tasks);
        ui.showAdd(t, tasks.size());
    }
}
