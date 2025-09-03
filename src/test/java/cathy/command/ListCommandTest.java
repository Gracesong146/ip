package cathy.command;

import cathy.Ui;
import cathy.storage.Storage;
import cathy.task.TaskList;
import cathy.task.ToDo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCommandTest {

    @TempDir Path tmp;

    @Test
    void executeDoesNotMutateList() throws Exception {
        var ui = new Ui();
        var storage = new Storage(tmp.resolve("tasks.txt").toString());
        var list = new TaskList();
        list.add(new ToDo("a"));
        list.add(new ToDo("b"));

        new ListCommand().execute(list, ui, storage);

        assertEquals(2, list.size());
        assertEquals("a", list.get(0).getDescription());
        assertEquals("b", list.get(1).getDescription());
    }
}
