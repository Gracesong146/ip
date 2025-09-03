package cathy;

import cathy.task.TaskList;
import cathy.task.ToDo;
import cathy.task.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Unit tests for Cathy.
 * Verifies core operations such as adding, retrieving,
 * checking size, and removing tasks.
 */
public class CathyTest {

    @Test
    void addAndGetAndSizeWork() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());

        ToDo t1 = new ToDo("read a book");
        ToDo t2 = new ToDo("write tests");
        list.add(t1);
        list.add(t2);

        assertEquals(2, list.size());
        assertSame(t1, list.get(0));
        assertSame(t2, list.get(1));
    }

    @Test
    void removeTask() {
        TaskList list = new TaskList();
        list.add(new ToDo("A"));
        list.add(new ToDo("B"));
        list.add(new ToDo("C"));

        Task removed = list.removeAt(1);
        assertEquals("[T][ ] B", removed.toString());
        assertEquals(2, list.size());
        assertEquals("A", list.get(0).getDescription());
        assertEquals("C", list.get(1).getDescription());
    }
}
