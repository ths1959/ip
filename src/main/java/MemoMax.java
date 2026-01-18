import java.util.Scanner;

public class MemoMax {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello! I'm MemoMax\nWhat can I do for you?" + logo);

        Task[] tasks = new Task[100]; // Task storage
        int taskCounter = 0; // Serial number for tasks
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String userInput = scanner.nextLine();
            String[] inputParts = userInput.split(" ");
            boolean isValid = true;
            if (userInput.equals("bye")) { // Exit condition
                break;
            } else if (userInput.equals("list")) { // List tasks
                if (taskCounter == 0) { // No task in storage
                    System.out.println("There are currently no tasks in your list");
                } else { // Print out all tasks
                    for (int i = 0; i < taskCounter; i++) {
                        System.out.println(i+1 + ". " + tasks[i].toString());
                    }
                }
            } else if (inputParts[0].equals("mark")) { // Mark task as done
                if (inputParts.length < 2) { // Invalid command due to unspecified task
                    System.out.println("Task not specified");
                    isValid = false;
                }
                if (isValid) { // Valid command
                    try {
                        int taskNumber = Integer.parseInt(inputParts[1]);
                        if (taskNumber < 1 || taskNumber > taskCounter) { // Task number out of range
                            System.out.println("Task does not exist");
                        } else {
                            int index = taskNumber - 1;
                            if (tasks[index].getStatusIcon().equals("[X]")) { // Task already marked
                                System.out.println("Task already marked as done");
                            } else {
                                tasks[index].mark();
                                System.out.println("Nice! I've marked this as done:\n" + "  " +
                                        tasks[index].toString());
                            }
                        }
                    } catch (NumberFormatException e) { // If task number has an invalid format
                        System.out.println("Task not specified as a number");
                    }
                }
            } else if (inputParts[0].equals("unmark")) { // Unmark done task
                if (inputParts.length < 2) { // Invalid command due to unspecified task
                    System.out.println("Task not specified");
                    isValid = false;
                }
                if (isValid) { // Valid command
                    try {
                        int taskNumber = Integer.parseInt(inputParts[1]);
                        if (taskNumber < 1 || taskNumber > taskCounter) { // Task number out of range
                            System.out.println("Task does not exist");
                        } else {
                            int index = taskNumber - 1;
                            if (tasks[index].getStatusIcon().equals("[ ]")) { // Task already unmarked
                                System.out.println("Task is already unmarked");
                            } else {
                                tasks[index].unmark();
                                System.out.println("OK, I've marked this task as not done yet:\n" + "  " +
                                        tasks[index].toString());
                            }
                        }
                    } catch (NumberFormatException e) { // If task number has an invalid format
                        System.out.println("Task not specified as a number");
                    }
                }
            } else { // Add task to storage
                tasks[taskCounter] = new Task(userInput);
                System.out.println("added: " + userInput);
                taskCounter += 1;
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
