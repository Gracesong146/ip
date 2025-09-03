package cathy.command;

import cathy.Ui;
import cathy.exception.CathyException;
import cathy.storage.Storage;
import cathy.task.TaskList;
import cathy.task.ToDo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link MarkCommand} and {@link UnmarkCommand} classes.
 * <p>
 * Verifies that:
 * <ul>
 *   <li>Marking a task updates its status icon to "X".</li>
 *   <li>Unmarking a task resets its status icon to a blank space.</li>
 *   <li>Executing mark on an out-of-range index throws a {@link CathyException}.</li>
 * </ul>
 */
public class MarkUnmarkTest {

    @TempDir
    Path tmp;

    @Test
    void markUnmarkToggleStatus() throws Exception {
        var ui = new Ui();
        var storage = new Storage(tmp.resolve("tasks.txt").toString());
        var list = new TaskList();
        list.add(new ToDo("exercise"));

        new MarkCommand(1).execute(list, ui, storage);
        assertEquals("X", list.get(0).getStatusIcon());
        new UnmarkCommand(1).execute(list, ui, storage);
        assertEquals(" ", list.get(0).getStatusIcon()); // blank means not done
    }

    @Test
    void markOutOfRange() {
        var ui = new Ui();
        var storage = new Storage(tmp.resolve("tasks.txt").toString());
        var list = new TaskList();
        list.add(new ToDo("only one"));

        assertThrows(CathyException.class, () -> new MarkCommand(0).execute(list, ui, storage));
        assertThrows(CathyException.class, () -> new MarkCommand(2).execute(list, ui, storage));
    }
}
