package cathy;

import cathy.command.Command;
import cathy.exception.CathyException;
import cathy.exception.InvalidTaskTypeException;
import cathy.storage.Storage;
import cathy.task.Deadline;
import cathy.task.Event;
import cathy.task.TaskList;
import cathy.task.ToDo;
import javafx.application.Platform;

/**
 * The main class for the Cathy task assistant application.
 * <p>
 * This class provides a command-line interface for managing tasks of three types:
 * <ul>
 *   <li>{@link ToDo} – simple tasks without date/time.</li>
 *   <li>{@link Deadline} – tasks with a due date/time.</li>
 *   <li>{@link Event} – tasks with a start and end time.</li>
 * </ul>
 * <p>
 * Supported commands:
 * <ul>
 *   <li>todo &lt;description&gt;</li>
 *   <li>deadline &lt;description&gt; /by &lt;date&gt;</li>
 *   <li>event &lt;description&gt; /from &lt;start&gt; /to &lt;end&gt;</li>
 *   <li>list – display all tasks</li>
 *   <li>mark &lt;number&gt; – mark a task as done</li>
 *   <li>unmark &lt;number&gt; – mark a task as not done</li>
 *   <li>delete &lt;number&gt; – remove a task</li>
 *   <li><code>on &lt;date&gt;</code>
 *   – display all {@link Deadline} and {@link Event} tasks happening on a given date</li>
 *   <li>help – display instructions</li>
 *   <li>bye – exit the program</li>
 * </ul>
 * <p>
 * This class also handles invalid input with custom messages via {@link InvalidTaskTypeException}.
 */
public class Cathy {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private String returnMessage;

    /**
     * Constructs a new {@code Cathy} instance with a given file path for persistent storage.
     *
     * @param filePath path to the storage file
     */
    public Cathy(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main program loop:
     * <ol>
     *   <li>Displays a welcome message</li>
     *   <li>Reads user input in a loop</li>
     *   <li>Parses input into {@link Command} objects</li>
     *   <li>Executes the command against the {@link TaskList}</li>
     *   <li>Handles errors gracefully and displays helpful messages</li>
     *   <li>Terminates on an exit command</li>
     * </ol>
     */

    public String welcomeMessage() {
        return ui.showWelcome();
    }

    public String getLogo() {
        return "  ____      _   _          \n"
                + " / ___|__ _| |_| |__  _   _ \n"
                + "| |   / _` | __| '_ \\| | | |\n"
                + "| |__| (_| | |_| | | | |_| |\n"
                + " \\____\\__,_|\\__|_| |_|\\__, |\n"
                + "                       __| |\n"
                + "                       |___/\n\n";
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            String reply = c.execute(tasks, ui, storage);
            if (c.isExit()) {
                // Close the FX app after we return the goodbye text
                Platform.runLater(Platform::exit);
            }
            return reply;
        } catch (CathyException e) {
            return ui.showError(e.getMessage());
        } catch (Exception e) {
            return ui.showError("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Entry point of the program.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("Hello!");
    }
}
