import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Storage class handles saving and loading tasks to and from a file.
 * <p>
 * Supports loading tasks at startup and saving tasks whenever the list changes.
 * Level 7 minimal implementation.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage object with the given file path.
     * Creates the parent folder if it does not exist.
     *
     * @param filePath the path to the file where tasks will be saved
     */
    public Storage(String filePath) {
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
                    String[] parts = line.split(" \\| ");
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];

                    Task t;
                    switch (type) {
                    case "T":
                        t = new ToDo(description);
                        break;
                    case "D":
                        if (parts.length >= 4) {
                            String byIso = parts[3];
                            t = new Deadline(description, byIso);
                        } else {
                            continue; // corrupted line, skip
                        }
                        break;
                    case "E":
                        if (parts.length >= 5) {
                            String fromIso = parts[3];
                            String toIso = parts[4];
                            t = new Event(description, fromIso, toIso);
                        } else {
                            continue; // corrupted line, skip
                        }
                        break;
                    default:
                        continue; // skip corrupted lines
                    }

                    if (isDone) t.markAsDone();
                    tasks.add(t);
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
