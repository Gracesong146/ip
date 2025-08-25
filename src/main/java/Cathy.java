import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Cathy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        ArrayList<String> toDoList = new ArrayList<>();
        
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
        System.out.println(" Hello! I'm Cathy");
        System.out.println(" What can I do for you?");
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
                for (String item : toDoList) {
                    System.out.println(idx + ". " + item);
                    idx += 1;
                }
                System.out.println("____________________________________________________________\n");
            } else {
                toDoList.add(userInput);
                System.out.println("added: " + userInput);
                System.out.println("____________________________________________________________\n");
            }
        }

        scanner.close();
    }
}
