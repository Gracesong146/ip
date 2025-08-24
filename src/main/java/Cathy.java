import java.util.Scanner;
import java.util.Random;

public class Cathy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

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
        System.out.println("____________________________________________");
        System.out.println(" Hello! I'm Cathy");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________");while (true) {
            String userInput = scanner.nextLine();
            System.out.println("____________________________________________________________");

            if (userInput.equalsIgnoreCase("bye")) {
                int idx = rand.nextInt(goodbyes.length);
                System.out.println(" " + goodbyes[idx]);
                System.out.println("____________________________________________________________");
                break;
            } else {
                System.out.println(" " + userInput);
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }
}
