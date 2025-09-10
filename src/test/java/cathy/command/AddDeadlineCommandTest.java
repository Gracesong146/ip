package cathy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cathy.Parser;
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
    void addDeadlineWithExplicitTime() throws Exception {
        Ui ui = new Ui();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());
        TaskList list = new TaskList();

        // explicit time: 2025-09-10T16:00
        LocalDateTime by = LocalDateTime.of(2025, 9, 10, 16, 0);
        new AddDeadlineCommand("submit report", by).execute(list, ui, storage);

        assertEquals(1, list.size());
        assertInstanceOf(Deadline.class, list.get(0));
        assertEquals("submit report", list.get(0).getDescription());
        assertEquals(by, ((Deadline) list.get(0)).getBy());
    }

    @Test
    void addDeadlineDateOnly() throws Exception {
        Ui ui = new Ui();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());
        TaskList list = new TaskList();

        // date-only: should default to 23:59 by Parser logic
        Parser.parse("deadline pay bills /by 2025-09-10").execute(list, ui, storage);

        assertEquals(1, list.size());
        assertInstanceOf(Deadline.class, list.get(0));

        Deadline d = (Deadline) list.get(0);
        assertEquals("pay bills", d.getDescription());
        assertEquals(LocalDate.of(2025, 9, 10).atTime(23, 59), d.getBy());
    }
}
