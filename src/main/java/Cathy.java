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

public class Cathy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        Storage storage = new Storage("./data/cathy.txt");
        ArrayList<Task> toDoList = storage.load();
        int counter = toDoList.size();

        String logo = "      ____      _   _          \n"
                + "     / ___|__ _| |_| |__  _   _ \n"
                + "    | |   / _` | __| '_ \\| | | |\n"
                + "    | |__| (_| | |_| | | | |_| |\n"
                + "     \\____\\__,_|\\__|_| |_|\\__, |\n"
                + "                           __| |\n"
                + "                           |___/";
        System.out.println(logo);
        System.out.println("    ____________________________________________________________\n");
        System.out.println("     Oh look, someone showed up.");
        System.out.println("     I'm Cathy, your gloriously underappreciated task assistant.\n");
        System.out.println("     Here's what I *might* help you with (if you're lucky):\n");
        System.out.println("     - There are 3 types of tasks, and yes, you need to follow the format:");
        System.out.println("         1. ToDos: simple tasks with no date/time. Example command: todo visit new theme park");
        System.out.println("         2. Deadlines: tasks that must be done before a date/time. Example command: deadline submit report /by 11/10/2019 5pm");
        System.out.println("         3. Events: tasks with a start and end time. Example command: event team meeting /from 2/10/2019 2pm /to 2/10/2019 4pm");
        System.out.println("            (Notice the /from and /to – don’t forget them!)");
        System.out.println("     - Type 'list' to see your glorious pile of tasks.");
        System.out.println("     - Type 'mark <number>' to mark a task as done. Try not to mess it up.");
        System.out.println("     - Type 'unmark <number>' to undo a completed task.");
        System.out.println("     - Type 'help' to see this list of commands. Even I can’t help the clueless otherwise.");
        System.out.println("     - Type 'bye' to leave me in peace.");
        System.out.println("    ____________________________________________________________\n");
        while (true) {
            String userInput = scanner.nextLine();
            System.out.println("    ____________________________________________________________\n");

            if (userInput.equalsIgnoreCase("help")) {
                System.out.println("     Ugh… you again? Fine, I’ll repeat it. Pay attention this time.");
                System.out.println("     I'm Cathy, your gloriously underappreciated task assistant.\n");
                System.out.println("     Here’s what I *might* help you with (if your brain can handle it):\n");
                System.out.println("     - There are 3 types of tasks, so don’t mess up the format:");
                System.out.println("         1. ToDos: simple tasks with no date/time. Example: todo visit new theme park");
                System.out.println("         2. Deadlines: must be done before a date/time. Example: deadline submit report /by 11/10/2019 5pm");
                System.out.println("         3. Events: start and end times required. Example: event team meeting /from 2/10/2019 2pm /to 2/10/2019 4pm");
                System.out.println("            (Yes, /from and /to are mandatory. Try not to forget.)");
                System.out.println("     - Type 'list' to see your glorious pile of tasks (again).");
                System.out.println("     - Type 'mark <number>' to mark a task as done. Don’t screw it up.");
                System.out.println("     - Type 'unmark <number>' to undo a completed task. Not that you’ll remember.");
                System.out.println("     - Type 'help' to see this list again… seriously, pay attention.");
                System.out.println("     - Type 'bye' to finally leave me alone.");
                System.out.println("     And yes, I’ll never repeat this again… so maybe try reading this carefully.");
                System.out.println("    ____________________________________________________________\n");

            } else if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("     Finally! I was getting bored...");
                System.out.println("    ____________________________________________________________\n");
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                if (toDoList.isEmpty()) {
                    System.out.println("     Wow… nothing. Your life must be thrilling.");
                    System.out.println("    ____________________________________________________________\n");
                } else {
                    int idx = 1;
                    System.out.println("     Your tasks, in all their glory.");
                    System.out.println("     Don’t pretend you didn’t forget some:");
                    for (Task item : toDoList) {
                        System.out.println("     " + idx + ". " + item);
                        idx += 1;
                    }
                    System.out.println("    ____________________________________________________________\n");
                }
            } else if (userInput.toLowerCase().startsWith("mark")) {
                String[] parts = userInput.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        Task newT = toDoList.get(taskNumber - 1);
                        if (newT.getStatusIcon().equals("X")) {
                            System.out.println("     Darling, that task’s already done. No need to be an overachiever.");
                            System.out.println("    ____________________________________________________________\n");
                        } else {
                            newT.markAsDone();
                            toDoList.set(taskNumber - 1, newT);
                            System.out.println("     Marked as done. Go ahead, feel proud for once:");
                            System.out.println("        " + newT);
                            System.out.println("    ____________________________________________________________\n");
                            storage.save(toDoList);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("     Sweetie, numbers only. This isn’t a spelling bee");
                        System.out.println("    ____________________________________________________________\n");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("     Trying to mark task " + Integer.parseInt(parts[1]) + " as done? Cute.");
                        System.out.println("     You can't just mark imaginary tasks to feel accomplished.");
                        System.out.println("    ____________________________________________________________\n");
                    }
                } else {
                    System.out.println("     If you're going to mark something, at least give me something valid.");
                    System.out.println("     Give it in the following form: mark [number]");
                    System.out.println("    ____________________________________________________________\n");
                }
            } else if (userInput.toLowerCase().startsWith("unmark")) {
                String[] parts = userInput.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        Task newT = toDoList.get(taskNumber - 1);
                        if (newT.getStatusIcon().equals(" ")) {
                            System.out.println("     Task " + taskNumber + " is already unmarked.");
                            System.out.println("     Stop trying to double negative your way through life.");
                            System.out.println("    ____________________________________________________________\n");
                        } else {
                            newT.markAsNotDone();
                            toDoList.set(taskNumber - 1, newT);
                            System.out.println("     Fine, it lives to torment you another day:");
                            System.out.println("        " + newT);
                            System.out.println("    ____________________________________________________________\n");
                            storage.save(toDoList);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("     Sweetie, numbers only. This isn’t a spelling bee");
                        System.out.println("    ____________________________________________________________\n");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("     Ah, clever. But no, that task is imaginary.");
                        System.out.println("    ____________________________________________________________\n");
                    }
                } else {
                    System.out.println("     If you're going to unmark something, at least give me a valid number. I'm not psychic");
                    System.out.println("    ____________________________________________________________\n");
                }
            } else if (userInput.toLowerCase().startsWith("delete")) {
                String[] parts = userInput.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        Task newT = toDoList.get(taskNumber - 1);
                        toDoList.remove(taskNumber - 1);
                        counter -= 1;
                        System.out.println("     Noted. I've removed this task:");
                        System.out.println("        " + newT);
                        System.out.println("     One less thing for you to forget.");
                        System.out.println("     You’ve got " + counter + " tasks now.");
                        System.out.println("    ____________________________________________________________\n");
                    } catch (NumberFormatException e) {
                        System.out.println("     Sweetie, numbers only. This isn’t a spelling bee");
                        System.out.println("    ____________________________________________________________\n");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("     Nice try, but that task doesn't even exist.");
                        System.out.println("    ____________________________________________________________\n");
                        storage.save(toDoList);
                    }
                } else {
                    System.out.println("     If you're going to delete something, at least give me something valid.");
                    System.out.println("     Give it in the following form: delete [number]");
                    System.out.println("    ____________________________________________________________\n");
                }
            } else if (userInput.toLowerCase().startsWith("todo")) {
                try {
                    String description = userInput.length() > 4 ? userInput.substring(5).trim() : "";
                    if (description.isEmpty()) {
                        throw new InvalidTaskTypeException(TaskType.TODO);
                    }
                    Task t = new ToDo(description);
                    toDoList.add(t);
                    counter += 1;
                    System.out.println("     Fine, I've added to the list:");
                    System.out.println("       " + t);
                    System.out.println("     You’ve got " + counter + " tasks now. Try not to lose track this time.");
                    System.out.println("    ____________________________________________________________\n");
                    storage.save(toDoList);
                } catch (InvalidTaskTypeException e) {
                    System.out.println(e.getMessage());
                }
            } else if (userInput.toLowerCase().startsWith("deadline")) {
                try {
                    // Remove the command word "deadline " first
                    String details = userInput.length() > 8 ? userInput.substring(9).trim() : "";
                    if (details.isEmpty()) {
                        throw new InvalidTaskTypeException(TaskType.DEADLINE);
                    }

                    // Split description and due date
                    String[] parts = details.split(" /by ");

                    if (parts.length < 2) {
                        System.out.println("     Seriously? That deadline format is a mess.");
                        System.out.println("     Try again like you actually read the instructions: deadline <desc> /by <date>");
                        System.out.println("    ____________________________________________________________\n");
                    } else {
                        String description = parts[0]; // "return book"
                        String by = parts[1];          // "Sunday"

                        Task t = new Deadline(description, by);
                        toDoList.add(t);
                        counter += 1;
                        System.out.println("     Fine, I've added to the list:");
                        System.out.println("       " + t);
                        System.out.println("     You’ve got " + counter + " tasks now. Try not to lose track this time.");
                        System.out.println("    ____________________________________________________________\n");
                        storage.save(toDoList);
                    }
                } catch (InvalidTaskTypeException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidDateTimeException e) {
                    System.out.println("     Invalid date/time: " + e.getMessage());
                }

            } else if (userInput.toLowerCase().startsWith("event")) {
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
                        System.out.println("     Invalid event format. Did you even try?");
                        System.out.println("     Use: event <desc> /from <start> /to <end> — it's not that hard.");
                        System.out.println("    ____________________________________________________________\n");
                    } else {
                        String duration = parts[1].trim();
                        String[] parts2 = duration.split(" /to "); // note space before /to
                        if (parts2.length < 2) {
                            System.out.println("     Missing '/to <end>' in your event. Planning half an event now?");
                            System.out.println("     Use: event <desc> /from <start> /to <end> — complete it like a grown-up.");
                            System.out.println("    ____________________________________________________________\n");
                        } else {
                            String from = parts2[0].trim(); // "Mon 2pm"
                            String to = parts2[1].trim();   // "4pm"

                            Task t = new Event(description, from, to);
                            toDoList.add(t);
                            counter += 1;
                            System.out.println("     Fine, I've added to the list:");
                            System.out.println("       " + t);
                            System.out.println("     You’ve got " + counter + " tasks now. Try not to lose track this time.");
                            System.out.println("    ____________________________________________________________\n");
                            storage.save(toDoList);
                        }
                    }
                } catch (InvalidTaskTypeException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidDateTimeException e) {
                    System.out.println("     Invalid date/time: " + e.getMessage());
                }
            } else if (userInput.toLowerCase().startsWith("on")) {
                try {
                    String dateStr = userInput.substring(3).trim().replace("/", "-");
                    LocalDate queryDate = LocalDate.parse(dateStr);

                    System.out.println("     Tasks happening on " + queryDate + ":");
                    boolean found = false;

                    for (Task t : toDoList) {
                        if (t instanceof Deadline) {
                            Deadline d = (Deadline) t;
                            if (d.getBy().toLocalDate().equals(queryDate)) {
                                System.out.println("       " + d);
                                found = true;
                            }
                        } else if (t instanceof Event) {
                            Event e = (Event) t;
                            if (occursOn(e, queryDate)) {
                                System.out.println("       " + e);
                                found = true;
                            }
                        }
                    }

                    if (!found) {
                        System.out.println("     Nothing on that day. Must be nice to be free for once.");
                    }
                    System.out.println("    ____________________________________________________________\n");

                } catch (Exception e) {
                    System.out.println("     That date makes no sense. Use yyyy-MM-dd. Try again.");
                    System.out.println("    ____________________________________________________________\n");
                }
            } else {
                System.out.println("     Hmm… fascinating gibberish.");
                System.out.println("     Try again, or type \"help\" to see what I actually understand.");
                System.out.println("    ____________________________________________________________\n");
            }
        }

        scanner.close();
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
