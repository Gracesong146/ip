import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user I/O (printing + reading).
 */
public class Ui {
    private final Scanner in = new Scanner(System.in);

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
        System.out.println("     - Type 'list' to see your glorious pile of tasks.");
        System.out.println("     - Type 'mark <number>' to mark a task as done. Try not to mess it up.");
        System.out.println("     - Type 'unmark <number>' to undo a completed task.");
        System.out.println("     - Type 'help' to see this list of commands. Even I can’t help the clueless otherwise.");
        System.out.println("     - Type 'bye' to leave me in peace.");
        showLine();
    }
    public void showLine()    {
        System.out.println("    ____________________________________________________________\n");
    }

    /** Prints the concise help block. */
    public void showHelp() {
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
        showLine();
    }

    /**
     * Prints the task list, or a snarky empty message if there’s nothing.
     */
    public void showList(ArrayList<Task> list) {
        if (list.isEmpty()) {
            System.out.println("     Wow… nothing. Your life must be thrilling.");
            showLine();
            return;
        }
        int idx = 1;
        System.out.println("     Your tasks, in all their glory.");
        System.out.println("     Don’t pretend you didn’t forget some:");
        for (Task t : list) {
            System.out.println("     " + idx + ". " + t);
            idx++;
        }
        showLine();
    }

    /**
     * Reads the user's full command and returns it as-is.
     */
    public String readFullCommand() {
        return in.nextLine();
    }

    /**
     * Reads the user's input and returns only the first keyword
     * (the first word before any space).
     */
    public String readCommand(String inputLine) {
        if (inputLine.isEmpty()) {
            return ""; // no input given
        }
        return inputLine.split(" ", 2)[0].toLowerCase();
    }

    public void print(String message) {
        System.out.println("     " + message);
    }

    public void showError(String msg) {
        System.out.println("     " + msg);
        showLine();
    }

    public void showBye() {
        print("Bye. Hope to see you again soon!");
        showLine();
    }

    public void showAdd(Task t, int count) {
        print("Fine, I've added to the list:");
        print("  " + t);
        print("You’ve got " + count + " tasks now. Try not to lose track this time.");
        showLine();
    }

    public void showDelete(Task t, int count) {
        print("Noted. I've removed this task:");
        print("   " + t);
        print("One less thing for you to forget.");
        print("You’ve got " + count + " tasks now.");
        showLine();
    }

    public void showMark(Task t) {
        print("Marked as done. Go ahead, feel proud for once:");
        print("   " + t);
        showLine();
    }

    public void showUnmark(Task t) {
        print("Fine, it lives to torment you another day:");
        print("   " + t);
        showLine();
    }

    public String readCommand() {
        System.out.print("> ");
        return in.nextLine();
    }

    /* Close the Scanner */
    public void close() {
        in.close();
    }

}
