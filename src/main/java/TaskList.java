import java.util.ArrayList;

/**
 * TaskList wraps the underlying list of tasks and provides
 * simple, focused operations for Cathy to use.
 */
public class TaskList {
    private final ArrayList<Task> items;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.items = new ArrayList<>();
    }

    /**
     * Creates a task list seeded with existing tasks.
     */
    public TaskList(ArrayList<Task> existing) {
        this.items = (existing == null) ? new ArrayList<>() : existing;
    }

    /** Number of tasks. */
    public int size() {
        return items.size();
    }

    /** Read-only style access (still returns the list for iteration/printing). */
    public ArrayList<Task> getTasks() {
        return items;
    }

    /** Add a task to the end. */
    public void add(Task t) {
        items.add(t);
    }

    /** Replace task at index (zero-based). */
    public void set(int index0, Task t) {
        items.set(index0, t);
    }

    /** Remove task at zero-based index, returning the removed item. */
    public Task removeAt(int index0) {
        return items.remove(index0);
    }

    /**
     * Get task at zero-based index, returning the item at that location.
     */
    public Task get(int idx) {
        return items.get(idx);
    }
}
