package cathy.task;

import java.util.ArrayList;

/**
 * A wrapper around an {@link ArrayList} of {@link Task} objects,
 * providing simple, focused operations for Cathy to use.
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

    /**
     * Returns the number of tasks in this list.
     *
     * @return the current size of the list
     */
    public int size() {
        return items.size();
    }

    /**
     * Returns the underlying list of tasks.
     * <p>
     * Note: this exposes the actual list object,
     * so modifications to it will affect this {@code TaskList}.
     *
     * @return the list of tasks
     */
    public ArrayList<Task> getTasks() {
        return items;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param t the task to add
     */
    public void add(Task t) {
        items.add(t);
    }

    /**
     * Replaces the task at the specified zero-based index.
     *
     * @param index0 the zero-based index of the task to replace
     * @param t      the new task
     */
    public void set(int index0, Task t) {
        items.set(index0, t);
    }

    /**
     * Removes and returns the task at the specified zero-based index.
     *
     * @param index0 the zero-based index of the task to remove
     * @return the removed task
     */
    public Task removeAt(int index0) {
        return items.remove(index0);
    }

    /**
     * Returns the task at the specified zero-based index.
     *
     * @param idx the zero-based index of the task to retrieve
     * @return the task at that index
     */
    public Task get(int idx) {
        return items.get(idx);
    }
}
