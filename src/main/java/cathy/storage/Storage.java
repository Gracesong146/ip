package cathy.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import cathy.task.Deadline;
import cathy.task.Event;
import cathy.task.Task;
import cathy.task.TaskList;
import cathy.task.ToDo;

/**
 * Storage class handles saving and loading tasks to and from a file.
 * <p>
 * Supports loading tasks at startup and saving tasks whenever the list changes.
 * Level 7 minimal implementation.
 */
public class Storage {
    private static final String SEP = " \\| "; // Separation
    private final String filePath;

    /**
     * Constructs a Storage object with the given file path.
     * Creates the parent folder if it does not exist.
     *
     * @param filePath the path to the file where tasks will be saved
     */
    public Storage(String filePath) {
        assert filePath != null : "Storage: path provided";
        this.filePath = filePath;
        File file = new File(filePath);
        file.getParentFile().mkdirs();
    }

    /**
     * Loads tasks from the file.
     * Each task is reconstructed according to its type (ToDo, Deadline, Event) and done status.
     * Corrupted lines are skipped.
     *
     * @return an ArrayList of Task objects loaded from the file
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks; // file doesn't exist yet
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    tasks.add(parseLine(line));
                } catch (Exception e) {
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Helper for parsing lines
     *
     * @param line the line to be parsed
     * @return a static Task
     */
    private static Task parseLine(String line) {
        String[] t = line.split(SEP);
        String type = t[0];
        boolean isDone = "1".equals(t[1]);
        String desc = t[2];

        Task task;
        switch (type) {
        case "T":
            task = new ToDo(desc);
            break;
        case "D":
            assert t.length >= 4 : "Storage: Deadline needs by";
            task = new Deadline(desc, t[3]);
            break;
        case "E":
            assert t.length >= 5 : "Storage: Event needs from/to";
            task = new Event(desc, t[3], t[4]);
            break;
        default:
            throw new IllegalArgumentException("Unknown task type: " + type);
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Saves the given list of tasks to the file.
     * Each task is written in a simple format indicating type, done status, description, and dates/times.
     *
     * @param tasks an ArrayList of Task objects to save
     */
    public void save(TaskList tasks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Task t : tasks.getTasks()) {
                String line = "";
                String status = t.getStatusIcon().equals("X") ? "1" : "0";
                if (t instanceof ToDo) {
                    line = "T | " + status + " | " + t.getDescription();
                } else if (t instanceof Deadline) {
                    line = "D | " + status + " | " + t.getDescription() + " | "
                            + ((Deadline) t).getBy().toString();
                } else if (t instanceof Event) {
                    line = "E | " + status + " | " + t.getDescription() + " | "
                            + ((Event) t).getFrom().toString() + " | "
                            + ((Event) t).getTo().toString();
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
