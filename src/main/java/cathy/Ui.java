package cathy;

import java.util.Scanner;

import cathy.task.Task;
import cathy.task.TaskList;

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
    public String showWelcome() {
        String returnMessage = "";
        returnMessage += "Oh look, someone showed up.\n"
                + "I'm Cathy, your underappreciated task assistant.\n\n"
                + "Here's some quick commands:\n"
                + "- todo <task>\n"
                + "- deadline <task> /by <date> <time>\n"
                + "- event <task> /from <date> <time> /to <date> <time>\n"
                + "- list <number> : to see your tasks\n"
                + "- mark / unmark / delete <number> : to update tasks\n"
                + "- find <keyword> / on <date> <time> : to search\n"
                + "- sch <date> <time> : to see schedule on specific date\n"
                + "- bye : to leave me in peace\n\n"
                + "Type 'help' to see this list of commands again.\n\n"
                + "Note: <date> <time> is in the form of YYYY-MM-DD HH:MM.\n"
                + "Try not to mess it up.\n\n"
                + "Even I can't help the clueless sometimes.";
        return returnMessage;
    }

    /**
     * Displays the concise help block that lists available commands
     * and their expected formats.
     */
    public String showHelp() {
        return "Ugh... you again?\n"
                + "Fine, I'll repeat it. Pay attention this time.\n\n"
                + "I'm Cathy, your underappreciated task assistant.\n\n"
                + "Here's some quick commands:\n"
                + "- todo <task>\n"
                + "- deadline <task> /by <date> <time>\n"
                + "- event <task> /from <date> <time> /to <date> <time>\n"
                + "- list <number> : to see your tasks\n"
                + "- mark / unmark / delete <number> : to update tasks\n"
                + "- find <keyword> / on <date> <time> : to search\n"
                + "- sch <date> <time> : to see schedule on specific date\n"
                + "- bye : to leave me in peace\n\n"
                + "Type 'help' to see this list of commands again.\n\n"
                + "Note: <date> <time> is in the form of YYYY-MM-DD HH:MM.\n"
                + "Try not to mess it up.\n\n"
                + "And yes, I'll never repeat this again... so maybe try reading this carefully.";
    }

    /**
     * Displays all tasks in the given list.
     *
     * @param tasks the task list to display
     */
    public String showList(TaskList tasks) {
        StringBuilder returnMessage = new StringBuilder();
        if (tasks.isEmpty()) {
            return "Wow... nothing. Your life must be thrilling.";
        }
        returnMessage.append("Your tasks, in all their glory.\n" + "Don't pretend you didn't forget some:");
        for (int i = 0; i < tasks.size(); i++) {
            returnMessage.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        return returnMessage.toString();
    }

    /**
     * Displays an error message in the app's standard format.
     *
     * @param msg the error message
     */
    public String showError(String msg) {
        return "<ERROR>\n" + msg;
    }

    /**
     * Displays a farewell message when the user exits the program.
     */
    public String showBye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Displays confirmation when a task is added.
     *
     * @param t     the task that was added
     * @param count the new total number of tasks
     */
    public String showAdd(Task t, int count) {
        return "Fine, I've added to the list:\n"
                + "  " + t
                + "\nYou've got " + count + " tasks now. Try not to lose track this time.";
    }

    /**
     * Displays confirmation when a task is deleted.
     *
     * @param t     the task that was deleted
     * @param count the new total number of tasks
     */
    public String showDelete(Task t, int count) {
        return "Noted. I've removed this task:\n"
                + "   " + t
                + "\nOne less thing for you to forget."
                + "\nYou've got " + count + " tasks now.";
    }

    /**
     * Displays confirmation when a task is marked as done.
     *
     * @param t the task that was marked as done
     */
    public String showMark(Task t) {
        return "Marked as done. Go ahead, feel proud for once:\n   " + t;
    }

    /**
     * Displays confirmation when a task is unmarked (set back to not done).
     *
     * @param t the task that was unmarked
     */
    public String showUnmark(Task t) {
        return "Fine, it lives to torment you another day:\n   " + t;
    }

    /**
     * Displays the results of a 'find' command.
     *
     * @param matches list of tasks that match the search keyword
     */
    public String showFindResults(TaskList matches) {
        if (matches.isEmpty()) {
            return "No matching tasks. Guess your memory is as bad as your typing.";
        }
        StringBuilder returnMessage = new StringBuilder("Here's what I painfully dug up for you:");
        for (int i = 0; i < matches.size(); i++) {
            returnMessage.append("\n").append(i + 1).append(". ").append(matches.get(i));
        }
        return returnMessage.toString();
    }

    /**
     * Reads the next line of user input from standard input.
     *
     * @return the raw string entered by the user
     */
    public String readCommand() {
        return in.nextLine();
    }
}
