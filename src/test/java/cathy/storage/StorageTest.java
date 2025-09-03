package cathy.storage;

import cathy.task.Deadline;
import cathy.task.Event;
import cathy.task.Task;
import cathy.task.TaskList;
import cathy.task.ToDo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Tests for the Storage class, focusing on save and load.
 */
class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    void saveThenLoadPreservesSize() {
        String file = tempDir.resolve("tasks.txt").toString();
        Storage storage = new Storage(file);

        TaskList out = new TaskList();
        out.add(new ToDo("t1"));
        out.add(new Deadline("d1", "2025-09-10T00:00"));
        out.add(new Event("e1", "2025-09-01T14:00", "2025-09-01T15:30"));

        storage.save(out);

        TaskList in = new TaskList();
        for (Task t : storage.load()) in.add(t);

        assertEquals(3, in.size());
    }

    @Test
    void saveThenLoadPreservesToDoDescriptionAndStatus() {
        String file = tempDir.resolve("todo.txt").toString();
        Storage storage = new Storage(file);

        TaskList out = new TaskList();
        ToDo t = new ToDo("todo one");
        t.markAsDone();
        out.add(t);
        storage.save(out);

        TaskList in = new TaskList();
        for (Task k : storage.load()) in.add(k);

        assertEquals(1, in.size());
        assertInstanceOf(ToDo.class, in.get(0));
        assertEquals("todo one", in.get(0).getDescription());
        assertEquals("X", in.get(0).getStatusIcon());
    }

    @Test
    void saveThenLoadPreservesDeadlineTypeAndDescription() {
        String file = tempDir.resolve("deadline.txt").toString();
        Storage storage = new Storage(file);

        TaskList out = new TaskList();
        out.add(new Deadline("submit assignment", "2025-09-10T00:00"));
        storage.save(out);

        TaskList in = new TaskList();
        for (Task k : storage.load()) in.add(k);

        assertEquals(1, in.size());
        assertInstanceOf(Deadline.class, in.get(0));
        assertEquals("submit assignment", in.get(0).getDescription());
    }

    @Test
    void saveThenLoadPreservesEventTypeAndDescription() {
        String file = tempDir.resolve("event.txt").toString();
        Storage storage = new Storage(file);

        TaskList out = new TaskList();
        out.add(new Event("meeting", "2025-09-01T14:00", "2025-09-01T15:30"));
        storage.save(out);

        TaskList in = new TaskList();
        for (Task k : storage.load()) in.add(k);

        assertEquals(1, in.size());
        assertInstanceOf(Event.class, in.get(0));
        assertEquals("meeting", in.get(0).getDescription());
    }

    @Test
    void loadMissingFileReturnsEmptyList() {
        String file = tempDir.resolve("does-not-exist.txt").toString();
        Storage storage = new Storage(file);

        TaskList in = new TaskList();
        for (Task t : storage.load()) in.add(t);

        assertEquals(0, in.size());
    }

    @Test
    void loadEmptyFileReturnsEmptyList() throws Exception {
        Path p = tempDir.resolve("empty.txt");
        Files.createFile(p);
        Storage storage = new Storage(p.toString());

        TaskList in = new TaskList();
        for (Task t : storage.load()) in.add(t);

        assertEquals(0, in.size());
    }
}