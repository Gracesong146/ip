package cathy.command;

import cathy.Ui;
import cathy.storage.Storage;
import cathy.task.TaskList;
import cathy.task.ToDo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class AddToDoCommandTest {

    @TempDir Path tmp;

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
