import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return description;
    }
}


public class Cathy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        ArrayList<Task> toDoList = new ArrayList<>();
        
        String[] goodbyes = {
                "Finally! I was getting bored...",
                "Leaving already? I barely started rolling my eyes.",
                "Oh joy, another person abandoning me.",
                "Bye then. Try not to miss me too much."
        };

        String logo = "  ____      _   _          \n"
                + " / ___|__ _| |_| |__  _   _ \n"
                + "| |   / _` | __| '_ \\| | | |\n"
                + "| |__| (_| | |_| | | | |_| |\n"
                + " \\____\\__,_|\\__|_| |_|\\__, |\n"
                + "                       |___/ \n";
        System.out.println(logo);
        System.out.println("____________________________________________\n");
        System.out.println(" Oh look, someone showed up.\n");
        System.out.println(" I'm Cathy, your gloriously underappreciated task assistant.\n");
        System.out.println(" Here's what I *might* help you with (if you're lucky):");
        System.out.println(" - Type anything to add it to your to-do list.");
        System.out.println(" - Type 'list' to see your glorious pile of tasks.");
        System.out.println(" - Type 'mark [number]' to mark a task as done. Try not to mess it up.");
        System.out.println(" - Type 'bye' to leave me in peace.");
        System.out.println("____________________________________________\n");
        while (true) {
            String userInput = scanner.nextLine();
            System.out.println("____________________________________________________________\n");

            if (userInput.equalsIgnoreCase("bye")) {
                int idx = rand.nextInt(goodbyes.length);
                System.out.println(" " + goodbyes[idx]);
                System.out.println("____________________________________________________________\n");
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                int idx = 1;
                System.out.println("list:");
                for (Task item : toDoList) {
                    System.out.println(idx + ". [" + item.getStatusIcon() + "] " + item);
                    idx += 1;
                }
                System.out.println("____________________________________________________________\n");
            } else if (userInput.toLowerCase().startsWith("mark")) {
                String[] parts = userInput.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        Task newT = toDoList.get(taskNumber - 1);
                        if (newT.getStatusIcon().equals("X")) {
                            System.out.println("Darling, that task’s already done. No need to be an overachiever.");
                            System.out.println("____________________________________________________________\n");
                        } else {
                            newT.markAsDone();
                            toDoList.set(taskNumber - 1, newT);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Sweetie, numbers only. This isn’t a spelling bee");
                        System.out.println("____________________________________________________________\n");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Nice try, but that task doesn't even exist.");
                        System.out.println("____________________________________________________________\n");
                    }
                } else {
                    System.out.println("If you're going to mark something, at least give me a valid number. I'm not psychic");
                }
            } else if (userInput.toLowerCase().startsWith("unmark")) {
                String[] parts = userInput.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        Task newT = toDoList.get(taskNumber - 1);
                        if (newT.getStatusIcon().equals(" ")) {
                            System.out.println("Task " + taskNumber + " is already unmarked. Stop trying to double negative your way through life.");
                            System.out.println("____________________________________________________________\n");
                        } else {
                            newT.markAsNotDone();
                            toDoList.set(taskNumber - 1, newT);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Sweetie, numbers only. This isn’t a spelling bee");
                        System.out.println("____________________________________________________________\n");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Nice try, but that task doesn't even exist.");
                        System.out.println("____________________________________________________________\n");
                    }
                } else {
                    System.out.println("If you're going to mark something, at least give me a valid number. I'm not psychic");
                }
                
            } else {
                Task t = new Task(userInput);
                toDoList.add(t);
                System.out.println("added: " + userInput);
                System.out.println("____________________________________________________________\n");
            }
        }

        scanner.close();
    }
}
