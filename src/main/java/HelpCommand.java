/**
 * Returns a list of commands
 */
public class HelpCommand extends Command {
    public HelpCommand() {
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CathyException {
        ui.showHelp();
    }
}
