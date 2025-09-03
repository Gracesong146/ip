package cathy.command;

import cathy.Ui;
import cathy.storage.Storage;
import cathy.task.TaskList;
import cathy.task.ToDo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link FindCommand} class.
 * Verifies case-insensitive substring matching and that execution does not mutate the list.
 */
class FindCommandTest {

    @TempDir
    Path tmp;

    @Test
    void filterMatchesSubstring() {
        TaskList list = new TaskList();
        list.add(new ToDo("Read Algorithms"));
        list.add(new ToDo("buy milk"));
        list.add(new ToDo("ALGO practice"));

        FindCommand cmd = new FindCommand("algo");
        TaskList matches = cmd.filter(list);

        assertEquals(2, matches.size());
        assertEquals("Read Algorithms", matches.get(0).getDescription());
        assertEquals("ALGO practice", matches.get(1).getDescription());
    }

    @Test
    void doesNotMutateList() throws Exception {
        var ui = new Ui();
        var storage = new Storage(tmp.resolve("tasks.txt").toString());
        TaskList list = new TaskList();
        list.add(new ToDo("a"));
        list.add(new ToDo("b"));

        new FindCommand("a").execute(list, ui, storage);

        assertEquals(2, list.size());
        assertEquals("a", list.get(0).getDescription());
        assertEquals("b", list.get(1).getDescription());
    }

    @Test
    void rejectsEmptyKeyword() {
        assertThrows(IllegalArgumentException.class, () -> new FindCommand(""));
        assertThrows(IllegalArgumentException.class, () -> new FindCommand("   "));
        assertThrows(IllegalArgumentException.class, () -> new FindCommand(null));
    }
}
