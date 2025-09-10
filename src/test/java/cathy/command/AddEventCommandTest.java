package cathy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

import cathy.Parser;
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
    void addEvent_withExplicitTimes_addsCorrectTask() throws Exception {
        Ui ui = new Ui();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());
        TaskList list = new TaskList();

        LocalDateTime from = LocalDateTime.of(2025, 9, 1, 14, 0);
        LocalDateTime to   = LocalDateTime.of(2025, 9, 1, 15, 30);

        new AddEventCommand("team sync", from, to).execute(list, ui, storage);

        assertEquals(1, list.size());
        assertInstanceOf(Event.class, list.get(0));
        Event e = (Event) list.get(0);
        assertEquals("team sync", e.getDescription());
        assertEquals(from, e.getFrom());
        assertEquals(to, e.getTo());
    }

    @Test
    void addEvent_dateOnly_defaultsToFullDay_viaParser() throws Exception {
        Ui ui = new Ui();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());
        TaskList list = new TaskList();

        // date-only from/to should default to 00:00 and 23:59
        Parser.parse("event hackathon /from 2025-09-10 /to 2025-09-10").execute(list, ui, storage);

        assertEquals(1, list.size());
        assertInstanceOf(Event.class, list.get(0));

        Event e = (Event) list.get(0);
        assertEquals("hackathon", e.getDescription());
        assertEquals(LocalDate.of(2025, 9, 10).atStartOfDay(), e.getFrom());
        assertEquals(LocalDate.of(2025, 9, 10).atTime(23, 59), e.getTo());
    }
}
