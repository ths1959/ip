import java.util.Scanner;

public class MemoMax {
    public static void main(String[] args) {
        Ui ui = new Ui();
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        ui.showMessage("Hello! I'm MemoMax\nWhat can I do for you?" + logo);

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
                    ui.showMessage("There are currently no tasks in your list");
                } else { // Print out all tasks
                    System.out.println("Here is/are the task(s) in your list:");
                    for (int i = 0; i < taskCounter; i++) {
                        System.out.println(i+1 + "." + tasks[i].toString());
                    }
                    ui.showMessage("You have " + taskCounter + " task(s).");
                }
            } else if (inputParts[0].equals("mark")) { // Mark task as done
                if (inputParts.length < 2) { // Invalid command due to unspecified task
                    ui.showMessage("Task number not specified\n" +
                            "Type 'help' for more options");
                    isValid = false;
                }
                if (isValid) { // Valid command
                    try {
                        int taskNumber = Integer.parseInt(inputParts[1]);
                        if (taskNumber < 1 || taskNumber > taskCounter) { // Task number out of range
                            ui.showMessage("Task does not exist\n" +
                                    "Type 'help' for more options");
                        } else {
                            int index = taskNumber - 1;
                            if (tasks[index].getStatusIcon().equals("[X]")) { // Task already marked
                                ui.showMessage("Task already marked as done");
                            } else {
                                tasks[index].mark();
                                ui.showMessage("Nice! I've marked this task as done:\n" + "  " +
                                        tasks[index].toString());
                            }
                        }
                    } catch (NumberFormatException e) { // If task number has an invalid format
                        ui.showMessage("Task number is incorrectly specified\n" +
                                "Type 'help' for more options");
                    }
                }
            } else if (inputParts[0].equals("unmark")) { // Unmark done task
                if (inputParts.length < 2) { // Invalid command due to unspecified task
                    ui.showMessage("Task number not specified\n" +
                            "Type 'help' for more options");
                    isValid = false;
                }
                if (isValid) { // Valid command
                    try {
                        int taskNumber = Integer.parseInt(inputParts[1]);
                        if (taskNumber < 1 || taskNumber > taskCounter) { // Task number out of range
                            ui.showMessage("Task does not exist");
                        } else {
                            int index = taskNumber - 1;
                            if (tasks[index].getStatusIcon().equals("[ ]")) { // Task already unmarked
                                ui.showMessage("Task is already unmarked");
                            } else {
                                tasks[index].unmark();
                                ui.showMessage("OK, I've marked this task as not done yet:\n" + "  " +
                                        tasks[index].toString());
                            }
                        }
                    } catch (NumberFormatException e) { // If task number has an invalid format
                        ui.showMessage("Task not specified as a number");
                    }
                }
            } else if (inputParts[0].equals("todo")) { // Process todo command
                if (inputParts.length == 1) {
                    ui.showMessage("Incomplete command\n" +
                            "Type 'help' for more options");
                } else {
                    String description = userInput.substring("todo ".length());
                    if (description.isEmpty()) {
                        ui.showMessage("Task description cannot be empty\n" +
                                "Type 'help' for more options");
                    } else {
                        tasks[taskCounter] = new Todo(description);
                        System.out.println("Got it. I've added this task:\n  " + tasks[taskCounter].toString());
                        taskCounter += 1;
                        ui.showMessage("Now you have " + taskCounter + " task(s) in the list.");
                    }
                }
            } else if (inputParts[0].equals("deadline")) { // Process deadline command
                if (inputParts.length == 1 || !userInput.contains("/by")) {
                    ui.showMessage("Incomplete command\n" +
                            "Type 'help' for more options");
                } else {
                    String[] actionDate = userInput.split("/by", -1);
                    String action = actionDate[0].substring("deadline ".length()).trim();
                    String date = actionDate[1].trim();
                    if (action.isEmpty()) {
                        ui.showMessage("Task description cannot be empty\n" +
                                "Type 'help' for more options");
                    } else if (date.isEmpty()) {
                        ui.showMessage("Task deadline has to be specified\n" +
                                "Type 'help' for more options");
                    } else {
                        tasks[taskCounter] = new Deadline(action, date);
                        System.out.println("Got it. I've added this task:\n  " + tasks[taskCounter].toString());
                        taskCounter += 1;
                        ui.showMessage("Now you have " + taskCounter + " task(s) in the list.");
                    }
                }
            } else if (inputParts[0].equals("event")) { // Process event command
                if (inputParts.length == 1 || !userInput.contains("/from") || !userInput.contains("/to")) {
                    ui.showMessage("Incomplete command\n" +
                            "Type 'help' for more options");
                } else {
                    String[] eventDate = userInput.split("/from", -1);
                    String event = eventDate[0].substring("event ".length()).trim();
                    if (eventDate.length < 2) {
                        ui.showMessage("Missing start time after /from\n" +
                                "Type 'help' for more options");
                    } else {
                        String[] fromTo = eventDate[1].split("/to", -1);
                        String from = fromTo[0].trim();
                        String to = fromTo[1].trim();
                        if (event.isEmpty()) {
                            ui.showMessage("Task description cannot be empty\n" +
                                    "Type 'help' for more options");
                        } else if (from.isEmpty()) {
                            ui.showMessage("Start time cannot be empty\n" +
                                    "Type 'help' for more options");
                        } else if (to.isEmpty()) {
                            ui.showMessage("End time cannot be empty\n" +
                                    "Type 'help' for more options");
                        } else {
                            tasks[taskCounter] = new Event(event, from, to);
                            System.out.println("Got it. I've added this task:\n  " + tasks[taskCounter].toString());
                            taskCounter += 1;
                            ui.showMessage("Now you have " + taskCounter + " task(s) in the list.");
                        }
                    }
                }
            } else if (inputParts[0].equals("help")) { // Process help command
                if (inputParts.length != 1) {
                    ui.showMessage("Invalid command\n" +
                            "Type 'help' for more options");
                } else {
                    ui.showMessage("""
                            Here's what I can help with:
                            1. Add a task: todo <description>
                            2. Add a deadline: deadline <task> /by <date>
                            3. Add an event: event <task> /from <start> /to <end>
                            4. See all tasks: list
                            5. Mark as done: mark <number>
                            6. Mark as not done: unmark <number>
                            7. Say goodbye: bye""");
                }
            }
            else { // Unknown command entered
                ui.showMessage("Invalid command\n" +
                        "Type 'help' for more options");
            }
        }

        ui.showMessage("Bye. Hope to see you again soon!");
    }
}
