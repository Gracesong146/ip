package cathy.task;

/**
 * Represents the different types of tasks supported by the system.
 * <p>
 * Each {@code TaskType} corresponds to a specific task format:
 * <ul>
 *   <li>{@link #TODO} – A simple task with only a description.</li>
 *   <li>{@link #DEADLINE} – A task that must be completed by a specific date or time.</li>
 *   <li>{@link #EVENT} – A task that occurs within a specific time range.</li>
 * </ul>
 */
public enum TaskType {
    TODO,
    DEADLINE,
    EVENT
}
