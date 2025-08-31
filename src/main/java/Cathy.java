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
 *   <li>help – display instructions</li>
 *   <li>bye – exit the program</li>
 * </ul>
 * <p>
 * This class also handles invalid input with custom messages via {@link InvalidTaskTypeException}.
 */

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Main entry for the Cathy task assistant.
 * I/O is handled by {@link Ui}. Persistence is via {@link Storage}.
 */
public class Cathy {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("./data/cathy.txt");
        TaskList toDoList = new TaskList(storage.load());
        int counter = toDoList.size();

        ui.showWelcome();

        label:
        while (true) {
            String userInput = ui.readFullCommand();
            Parser.Parsed p = Parser.parse(userInput);
            // String command = ui.readCommand(userInput);  // get just the first word
            ui.showLine();

            switch (p.action) {
            case HELP:
                ui.showHelp();
                break;
            case BYE:
                ui.print("Finally! I was getting bored...");
                ui.showLine();
                ui.close();
                return;
            case LIST:
                ui.showList(toDoList.getTasks());
                break;
            case MARK: {
                String[] parts = userInput.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        Task newT = toDoList.get(taskNumber - 1);
                        if (newT.getStatusIcon().equals("X")) {
                            ui.print("Darling, that task’s already done. No need to be an overachiever.");
                            ui.showLine();
                        } else {
                            newT.markAsDone();
                            toDoList.set(taskNumber - 1, newT);
                            ui.print("Marked as done. Go ahead, feel proud for once:");
                            ui.print("   " + newT);
                            ui.showLine();
                            storage.save(toDoList);
                        }
                    } catch (NumberFormatException e) {
                        ui.showError("Sweetie, numbers only. This isn’t a spelling bee");
                    } catch (IndexOutOfBoundsException e) {
                        ui.print("Trying to mark task " + Integer.parseInt(parts[1]) + " as done? Cute.");
                        ui.showError("You can't just mark imaginary tasks to feel accomplished.");
                    }
                } else {
                    ui.print("If you're going to mark something, at least give me something valid.");
                    ui.showError("Give it in the following form: mark [number]");
                }
                break;
            }
            case UNMARK: {
                String[] parts = userInput.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        Task newT = toDoList.get(taskNumber - 1);
                        if (newT.getStatusIcon().equals(" ")) {
                            ui.print("Task " + taskNumber + " is already unmarked.");
                            ui.showError("Stop trying to double negative your way through life.");
                        } else {
                            newT.markAsNotDone();
                            toDoList.set(taskNumber - 1, newT);
                            ui.print("Fine, it lives to torment you another day:");
                            ui.print("   " + newT);
                            ui.showLine();
                            storage.save(toDoList);
                        }
                    } catch (NumberFormatException e) {
                        ui.showError("Sweetie, numbers only. This isn’t a spelling bee");
                    } catch (IndexOutOfBoundsException e) {
                        ui.showError("Ah, clever. But no, that task is imaginary.");
                    }
                } else {
                    ui.showError("If you're going to unmark something, at least give me a valid number. I'm not psychic");
                }
                break;
            }
            case DELETE: {
                String[] parts = userInput.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        Task removed = toDoList.removeAt(taskNumber - 1);
                        counter--;
                        ui.print("Noted. I've removed this task:");
                        ui.print("   " + removed);
                        ui.print("One less thing for you to forget.");
                        ui.print("You’ve got " + counter + " tasks now.");
                        ui.showLine();
                    } catch (NumberFormatException e) {
                        ui.showError("Sweetie, numbers only. This isn’t a spelling bee");
                    } catch (IndexOutOfBoundsException e) {
                        ui.showError("Nice try, but that task doesn't even exist.");
                        storage.save(toDoList);
                    }
                } else {
                    ui.print("If you're going to delete something, at least give me something valid.");
                    ui.showError("Give it in the following form: delete [number]");
                }
                break;
            }
            case TODO:

                try {
                    String description = userInput.length() > 4 ? userInput.substring(5).trim() : "";
                    if (description.isEmpty()) {
                        throw new InvalidTaskTypeException(TaskType.TODO);
                    }
                    Task t = new ToDo(description);
                    toDoList.add(t);
                    counter++;
                    ui.print("Fine, I've added to the list:");
                    ui.print("  " + t);
                    ui.print("You’ve got " + counter + " tasks now. Try not to lose track this time.");
                    ui.showLine();
                    storage.save(toDoList);
                } catch (InvalidTaskTypeException e) {
                    ui.showError(e.getMessage());
                }
                break;
            case DEADLINE:
                try {
                    // Remove the command word "deadline " first
                    String details = userInput.length() > 8 ? userInput.substring(9).trim() : "";
                    if (details.isEmpty()) {
                        throw new InvalidTaskTypeException(TaskType.DEADLINE);
                    }

                    // Split description and due date
                    String[] parts = details.split(" /by ");

                    if (parts.length < 2) {
                        ui.print("Seriously? That deadline format is a mess.");
                        ui.showError("Try again like you actually read the instructions: deadline <desc> /by <date>");
                    } else {
                        String description = parts[0]; // "return book"
                        String by = parts[1];          // "Sunday"

                        Task t = new Deadline(description, by);
                        toDoList.add(t);
                        counter++;
                        ui.print("Fine, I've added to the list:");
                        ui.print("  " + t);
                        ui.print("You’ve got " + counter + " tasks now. Try not to lose track this time.");
                        ui.showLine();
                        storage.save(toDoList);
                    }
                } catch (InvalidTaskTypeException e) {
                    ui.showError(e.getMessage());
                } catch (InvalidDateTimeException e) {
                    ui.showError("Invalid date/time: " + e.getMessage());
                }

                break;
            case EVENT:
                try {
                    // Remove the command word "deadline " first
                    String details = userInput.length() > 5 ? userInput.substring(6).trim() : "";
                    if (details.isEmpty()) {
                        throw new InvalidTaskTypeException(TaskType.EVENT);
                    }
                    // example: event project meeting /from Mon 2pm /to 4pm
                    // Split description and due date
                    String[] parts = details.split(" /from ");
                    String description = parts[0].trim(); // "project meeting"

                    // Make sure /from was provided
                    if (parts.length < 2) {
                        ui.print("Invalid event format. Did you even try?");
                        ui.showError("Use: event <desc> /from <start> /to <end> — it's not that hard.");
                    } else {
                        String duration = parts[1].trim();
                        String[] parts2 = duration.split(" /to "); // note space before /to
                        if (parts2.length < 2) {
                            ui.print("Missing '/to <end>' in your event. Planning half an event now?");
                            ui.showError("Use: event <desc> /from <start> /to <end> — complete it like a grown-up.");
                        } else {
                            String from = parts2[0].trim(); // "Mon 2pm"
                            String to = parts2[1].trim();   // "4pm"

                            Task t = new Event(description, from, to);
                            toDoList.add(t);
                            counter++;
                            ui.print("Fine, I've added to the list:");
                            ui.print("  " + t);
                            ui.showError("You’ve got " + counter + " tasks now. Try not to lose track this time.");
                            storage.save(toDoList);
                        }
                    }
                } catch (InvalidTaskTypeException e) {
                    ui.showError(e.getMessage());
                } catch (InvalidDateTimeException e) {
                    ui.showError("Invalid date/time: " + e.getMessage());
                }
                break;
            case ON:
                try {
                    String dateStr = userInput.substring(3).trim().replace("/", "-");
                    LocalDate queryDate = LocalDate.parse(dateStr);

                    ui.print("Tasks happening on " + queryDate + ":");
                    boolean found = false;

                    for (Task t : toDoList.getTasks()) {
                        if (t instanceof Deadline d) {
                            if (d.getBy().toLocalDate().equals(queryDate)) {
                                ui.print("  " + d);
                                found = true;
                            }
                        } else if (t instanceof Event e) {
                            if (occursOn(e, queryDate)) {
                                ui.print("  " + e);
                                found = true;
                            }
                        }
                    }

                    if (!found) {
                        ui.print("Nothing on that day. Must be nice to be free for once.");
                    }
                    ui.showLine();

                } catch (Exception e) {
                    ui.showError("That date makes no sense. Use yyyy-MM-dd. Try again.");
                }
                break;
            case UNKNOWN:
            default:
                ui.print("Hmm… fascinating gibberish.");
                ui.showError("Try again, or type \"help\" to see what I actually understand.");
                break;
            }
        }

    }

    /**
     * Checks if the given event occurs on the specified date.
     * An event is considered to occur on that date if the date is between
     * (or equal to) its start and end dates.
     *
     * @param e the Event task to check
     * @param q the query date
     * @return true if the event spans the query date, false otherwise
     */
    private static boolean occursOn(Event e, LocalDate q) {
        LocalDate start = e.getFrom().toLocalDate();
        LocalDate end = e.getTo().toLocalDate();

        // Handle swapped ranges
        if (end.isBefore(start)) {
            LocalDate tmp = start;
            start = end;
            end = tmp;
        }
        // Check if q ∈ [start, end]
        return !(q.isBefore(start) || q.isAfter(end));
    }
}
