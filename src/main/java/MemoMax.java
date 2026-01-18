import java.util.Scanner;

public class MemoMax {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello! I'm MemoMax\nWhat can I do for you?" + logo);

        String[] tasks = new String[100]; // Task storage
        int taskCounter = 0; // Serial number for tasks
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) { // Exit condition
                break;
            } else if (userInput.equals("list")) { // List tasks
                if (taskCounter == 0) { // No task in storage
                    System.out.println("There are currently no tasks in your list");
                } else { // Print out all tasks
                    for (int i = 0; i < taskCounter; i++) {
                        System.out.println(i+1 + ". " + tasks[i]);
                    }
                }
            } else { // Add task to storage
                tasks[taskCounter] = userInput;
                System.out.println("added: " + userInput);
                taskCounter += 1;
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
