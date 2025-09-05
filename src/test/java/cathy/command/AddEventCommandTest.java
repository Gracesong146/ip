package cathy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cathy.Ui;
import cathy.storage.Storage;
import cathy.task.Event;
import cathy.task.TaskList;

/**
 * Unit tests for the AddEventCommand class.
 * Verifies that executing the command adds an Event task
 * with the correct description and date/time to the TaskList.
 */
class AddEventCommandTest {

    @TempDir
    Path tmp;

    @Test
    void addEvent() throws Exception {
        var ui = new Ui();
        var storage = new Storage(tmp.resolve("tasks.txt").toString());
        var list = new TaskList();

        new AddEventCommand("team sync", "2025-09-01T14:00", "2025-09-01T15:30").execute(list, ui, storage);

        assertEquals(1, list.size());
        assertInstanceOf(Event.class, list.get(0));
        assertEquals("team sync", list.get(0).getDescription());
    }
}
