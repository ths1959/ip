import java.util.Scanner;

public class MemoMax {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello! I'm MemoMax\nWhat can I do for you?" + logo);

        while(true) {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) { // Exit condition
                break;
            } else {
                System.out.println(userInput); // Echos user inputted commands
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
