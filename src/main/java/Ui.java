import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user-facing input and output (I/O).
 *
 * <p>The {@code Ui} class is responsible for:
 * <ul>
 *   <li>Printing messages, banners, errors, and task lists in a consistent style</li>
 *   <li>Displaying sarcastic/helpful feedback to the user</li>
 *   <li>Reading raw user input from standard input</li>
 * </ul>
 *
 * <p>This class acts as the primary interaction layer between the user
 * and the application.
 */
public class Ui {
    private final Scanner in = new Scanner(System.in);

    /**
     * Displays the welcome banner, ASCII logo, and a quick-start guide
     * describing the available commands.
     */
    public void showWelcome() {
        String logo = "      ____      _   _          \n"
                + "     / ___|__ _| |_| |__  _   _ \n"
                + "    | |   / _` | __| '_ \\| | | |\n"
                + "    | |__| (_| | |_| | | | |_| |\n"
                + "     \\____\\__,_|\\__|_| |_|\\__, |\n"
                + "                           __| |\n"
                + "                           |___/";
        System.out.println(logo);
        showLine();
        System.out.println("     Oh look, someone showed up.");
        System.out.println("     I'm Cathy, your gloriously underappreciated task assistant.\n");
        System.out.println("     Here's what I *might* help you with (if you're lucky):\n");
        System.out.println("     - There are 3 types of tasks, and yes, you need to follow the format:");
        System.out.println("         1. ToDos: simple tasks with no date/time. Example command: todo visit new theme park");
        System.out.println("         2. Deadlines: tasks that must be done before a date/time. Example command: deadline submit report /by 11/10/2019 5pm");
        System.out.println("         3. Events: tasks with a start and end time. Example command: event team meeting /from 2/10/2019 2pm /to 2/10/2019 4pm");
        System.out.println("            (Notice the /from and /to – don’t forget them!)");
        System.out.println("     - Type 'on <yyyy-MM-dd>' to see deadlines/events happening on that date.");
        System.out.println("     - Type 'list' to see your glorious pile of tasks.");
        System.out.println("     - Type 'mark <number>' to mark a task as done. Try not to mess it up.");
        System.out.println("     - Type 'unmark <number>' to undo a completed task.");
        System.out.println("     - Type 'help' to see this list of commands. Even I can’t help the clueless otherwise.");
        System.out.println("     - Type 'bye' to leave me in peace.");
        showLine();
    }

    /**
     * Prints a divider line to separate command outputs.
     */
    public void showLine()    {
        System.out.println("    ____________________________________________________________\n");
    }

    /**
     * Displays the concise help block that lists available commands
     * and their expected formats.
     */
    public void showHelp() {
        System.out.println("     Ugh… you again? Fine, I’ll repeat it. Pay attention this time.");
        System.out.println("     I'm Cathy, your gloriously underappreciated task assistant.\n");
        System.out.println("     Here’s what I *might* help you with (if your brain can handle it):\n");
        System.out.println("     - There are 3 types of tasks, so don’t mess up the format:");
        System.out.println("         1. ToDos: simple tasks with no date/time. Example: todo visit new theme park");
        System.out.println("         2. Deadlines: must be done before a date/time. Example: deadline submit report /by 11/10/2019 5pm");
        System.out.println("         3. Events: start and end times required. Example: event team meeting /from 2/10/2019 2pm /to 2/10/2019 4pm");
        System.out.println("            (Yes, /from and /to are mandatory. Try not to forget.)");
        System.out.println("     - Type 'on <yyyy-MM-dd>' to see deadlines/events happening on that date.");
        System.out.println("     - Type 'list' to see your glorious pile of tasks (again).");
        System.out.println("     - Type 'mark <number>' to mark a task as done. Don’t screw it up.");
        System.out.println("     - Type 'unmark <number>' to undo a completed task. Not that you’ll remember.");
        System.out.println("     - Type 'help' to see this list again… seriously, pay attention.");
        System.out.println("     - Type 'bye' to finally leave me alone.");
        System.out.println("     And yes, I’ll never repeat this again… so maybe try reading this carefully.");
    }

    /**
     * Displays all tasks in the given list.
     *
     * @param tasks the task list to display
     */
    public void showList(TaskList tasks) {
        if (tasks.size() == 0) {
            print("Wow… nothing. Your life must be thrilling.");
            return;
        }
        print("Your tasks, in all their glory.");
        print("Don’t pretend you didn’t forget some:");
        for (int i = 0; i < tasks.size(); i++) {
            print((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Prints a message with the app’s left margin formatting.
     *
     * @param message the message to print
     */
    public void print(String message) {
        System.out.println("     " + message);
    }

    /**
     * Displays an error message in the app’s standard format.
     *
     * @param msg the error message
     */
    public void showError(String msg) {
        System.out.println("     " + msg);
    }

    /**
     * Displays a farewell message when the user exits the program.
     */
    public void showBye() {
        print("Bye. Hope to see you again soon!");
    }

    /**
     * Displays confirmation when a task is added.
     *
     * @param t     the task that was added
     * @param count the new total number of tasks
     */
    public void showAdd(Task t, int count) {
        print("Fine, I've added to the list:");
        print("  " + t);
        print("You’ve got " + count + " tasks now. Try not to lose track this time.");
    }

    /**
     * Displays confirmation when a task is deleted.
     *
     * @param t     the task that was deleted
     * @param count the new total number of tasks
     */
    public void showDelete(Task t, int count) {
        print("Noted. I've removed this task:");
        print("   " + t);
        print("One less thing for you to forget.");
        print("You’ve got " + count + " tasks now.");
    }

    /**
     * Displays confirmation when a task is marked as done.
     *
     * @param t the task that was marked as done
     */
    public void showMark(Task t) {
        print("Marked as done. Go ahead, feel proud for once:");
        print("   " + t);
    }

    /**
     * Displays confirmation when a task is unmarked (set back to not done).
     *
     * @param t the task that was unmarked
     */
    public void showUnmark(Task t) {
        print("Fine, it lives to torment you another day:");
        print("   " + t);
    }

    /**
     * Reads the next line of user input from standard input.
     *
     * @return the raw string entered by the user
     */
    public String readCommand() {
        System.out.print("> ");
        return in.nextLine();
    }
}
