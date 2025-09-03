package cathy.command;

import cathy.Ui;
import cathy.storage.Storage;
import cathy.task.Task;
import cathy.task.TaskList;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a command that searches the task list for matches.
 * <p>
 * A task is considered a match if its description contains the
 * specified keyword, ignoring case. The command produces a
 * filtered list of tasks and displays the results to the user.
 */
public class FindCommand extends Command {
    private final String keywordLower;

    public FindCommand(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword must not be empty.");
        }
        this.keywordLower = keyword.toLowerCase();
    }

    /**
     * Pure helper so tests can assert on results without parsing console output.
     */
    public TaskList filter(TaskList tasks) {
        TaskList matches = new TaskList();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getDescription().toLowerCase().contains(keywordLower)) {
                matches.add(t);
            }
        }
        return matches;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matches = filter(tasks);
        ui.showFindResults(matches);
    }
}
