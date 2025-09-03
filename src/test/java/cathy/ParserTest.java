package cathy;

import cathy.command.Command;
import cathy.exception.CathyException;
import cathy.storage.Storage;
import cathy.task.TaskList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration tests for the Parser class.
 * Ensures that valid command strings are parsed and executed correctly,
 * and that invalid or malformed commands throw the expected exceptions.
 */
class ParserTest {

    @TempDir
    Path tmp;

    @Test
    void todoMarkDelete() throws Exception {
        var ui = new Ui();
        var storage = new Storage(tmp.resolve("tasks.txt").toString());
        var tasks = new TaskList();

        Command c1 = Parser.parse("todo read book");
        c1.execute(tasks, ui, storage);

        assertEquals(1, tasks.size());
        assertEquals("read book", tasks.get(0).getDescription());
        assertEquals(" ", tasks.get(0).getStatusIcon());

        Command c2 = Parser.parse("mark 1");
        c2.execute(tasks, ui, storage);
        assertEquals("X", tasks.get(0).getStatusIcon());

        Command c3 = Parser.parse("delete 1");
        c3.execute(tasks, ui, storage);
        assertEquals(0, tasks.size());
    }

    @Test
    void invalidCommand() {
        assertThrows(CathyException.class, () -> Parser.parse("delete"));
        assertThrows(CathyException.class, () -> Parser.parse("mark abc"));
    }
}