package cathy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cathy.Ui;
import cathy.storage.Storage;
import cathy.task.Deadline;
import cathy.task.TaskList;

/**
 * Unit tests for the AddDeadlineCommand class.
 * Verifies that executing the command adds a Deadline task
 * with the correct description and date/time to the TaskList.
 */
class AddDeadlineCommandTest {

    @TempDir
    Path tmp;

    @Test
    void addDeadline() throws Exception {
        var ui = new Ui();
        var storage = new Storage(tmp.resolve("tasks.txt").toString());
        var list = new TaskList();

        new AddDeadlineCommand("submit report", "2025-09-10T00:00").execute(list, ui, storage);

        assertEquals(1, list.size());
        assertInstanceOf(Deadline.class, list.get(0));
        assertEquals("submit report", list.get(0).getDescription());
    }
}
