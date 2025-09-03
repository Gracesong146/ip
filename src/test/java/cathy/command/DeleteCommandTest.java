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

class DeleteCommandTest {

    @TempDir Path tmp;

    @Test
    void deleteRemovesItem() throws Exception {
        var ui = new Ui();
        var storage = new Storage(tmp.resolve("tasks.txt").toString());
        var list = new TaskList();
        list.add(new ToDo("A"));
        list.add(new ToDo("B"));

        new DeleteCommand(2).execute(list, ui, storage);

        assertEquals(1, list.size());
        assertEquals("A", list.get(0).getDescription());
    }

    @Test
    void deleteOutOfRange() {
        var ui = new Ui();
        var storage = new Storage(tmp.resolve("tasks.txt").toString());
        var list = new TaskList();
        list.add(new ToDo("only"));

        assertThrows(CathyException.class, () -> new DeleteCommand(0).execute(list, ui, storage));
        assertThrows(CathyException.class, () -> new DeleteCommand(2).execute(list, ui, storage));
    }
}
