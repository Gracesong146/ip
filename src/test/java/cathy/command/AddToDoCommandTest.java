package cathy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cathy.Ui;
import cathy.storage.Storage;
import cathy.task.TaskList;
import cathy.task.ToDo;

/**
 * Unit tests for the AddToDoCommand class.
 * Verifies that executing the command adds a ToDo task.
 */
class AddToDoCommandTest {

    @TempDir
    Path tmp;

    @Test
    public void todoTooStringCorrectFormat() {
        ToDo t = new ToDo("read book");
        assertEquals("[T][ ] read book", t.toString());
        t.markAsDone();
        assertEquals("[T][X] read book", t.toString());
    }

    @Test
    void addTodo() throws Exception {
        var ui = new Ui();
        var storage = new Storage(tmp.resolve("tasks.txt").toString());
        var list = new TaskList();

        new AddToDoCommand("read book").execute(list, ui, storage);

        assertEquals(1, list.size());
        assertInstanceOf(ToDo.class, list.get(0));
        assertEquals("read book", list.get(0).getDescription());
    }
}
