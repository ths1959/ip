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
                try {
                    if (inputParts.length < 2) { // Invalid command due to unspecified task
                        throw new MemoMaxException("Task number not specified");
                    }
                    int taskNumber = Integer.parseInt(inputParts[1]);
                    if (taskNumber < 1 || taskNumber > taskCounter) { // Task number out of range
                        throw new MemoMaxException("Task does not exist");
                    }
                    int index = taskNumber - 1;
                    if (tasks[index].getStatusIcon().equals("[X]")) { // Task already marked
                        throw new MemoMaxException("Task is already marked as done");
                    }
                    tasks[index].mark();
                    ui.showMessage("Nice! I've marked this task as done:\n" + "  " +
                            tasks[index].toString());
                } catch (MemoMaxException e) {
                    ui.showErrorMessage(e.getMessage());
                }
                catch (NumberFormatException e) { // If task number has an invalid format
                    ui.showErrorMessage("'" + inputParts[1] + "' is not a valid number");
                }
            } else if (inputParts[0].equals("unmark")) { // Unmark done task
                try {
                    if (inputParts.length < 2) { // Invalid command due to unspecified task
                        throw new MemoMaxException("Task number not specified");
                    }
                    int taskNumber = Integer.parseInt(inputParts[1]);
                    if (taskNumber < 1 || taskNumber > taskCounter) { // Task number out of range
                        throw new MemoMaxException("Task does not exist");
                    }
                    int index = taskNumber - 1;
                    if (tasks[index].getStatusIcon().equals("[ ]")) { // Task already unmarked
                        throw new MemoMaxException("Task is already unmarked");
                    }
                    tasks[index].unmark();
                    ui.showMessage("OK, I've marked this task as not done yet:\n" + "  " +
                            tasks[index].toString());
                } catch (MemoMaxException e) {
                    ui.showErrorMessage(e.getMessage());
                }
                catch (NumberFormatException e) { // If task number has an invalid format
                    ui.showErrorMessage("'" + inputParts[1] + "' is not a valid number");
                }
            } else if (inputParts[0].equals("todo")) { // Process todo command
                try {
                    if (inputParts.length == 1) {
                        throw new MemoMaxException("Task description cannot be empty");
                    }
                    String description = userInput.substring("todo ".length());
                    tasks[taskCounter] = new Todo(description);
                    System.out.println("Got it. I've added this task:\n  " + tasks[taskCounter].toString());
                    taskCounter += 1;
                    ui.showMessage("Now you have " + taskCounter + " task(s) in the list.");
                } catch (MemoMaxException e) {
                    ui.showErrorMessage(e.getMessage());
                }
            } else if (inputParts[0].equals("deadline")) { // Process deadline command
                try {
                    if (inputParts.length == 1) {
                        throw new MemoMaxException("Incomplete command. It requires both" +
                                " description and 'by' date to be specified.");
                    }
                    if (!userInput.contains("/by")) {
                        throw new MemoMaxException("Incomplete command. It requires an additional" +
                                " 'by' date to be specified.");
                    }
                    String[] actionDate = userInput.split("/by", -1);
                    String action = actionDate[0].substring("deadline ".length()).trim();
                    String date = actionDate[1].trim();
                    if (action.isEmpty()) {
                        throw new MemoMaxException("Task description cannot be empty");
                    }
                    if (date.isEmpty()) {
                        throw new MemoMaxException("Task deadline has to be specified");
                    }
                    tasks[taskCounter] = new Deadline(action, date);
                    System.out.println("Got it. I've added this task:\n  " + tasks[taskCounter].toString());
                    taskCounter += 1;
                    ui.showMessage("Now you have " + taskCounter + " task(s) in the list.");
                } catch (MemoMaxException e) {
                    ui.showErrorMessage(e.getMessage());
                }
            } else if (inputParts[0].equals("event")) { // Process event command
                try {
                    if (inputParts.length == 1) {
                        throw new MemoMaxException("Incomplete command. It requires a description, " +
                                "'/from' time and '/to' time to be specified.");
                    }
                    boolean hasFrom = userInput.contains("/from");
                    boolean hasTo = userInput.contains("/to");
                    if (!hasFrom && !hasTo) {
                        throw new MemoMaxException("Incomplete command. It requires both '/from' time " +
                                "and '/to' time to be specified.");
                    }
                    if (!hasFrom) {
                        throw new MemoMaxException("Incomplete command. It requires an additional " +
                                "'/from' time to be specified.");
                    }
                    String[] eventDate = userInput.split("/from", -1);
                    String event = eventDate[0].substring("event ".length()).trim();
                    if (eventDate.length < 2) {
                        throw new MemoMaxException("Missing start time after '/from'.");
                    }
                    if (!hasTo) {
                        throw new MemoMaxException("Incomplete command. It requires an additional " +
                                "'/to' time to be specified.");
                    }
                    String[] fromTo = eventDate[1].split("/to", -1);
                    if (fromTo.length < 2) {
                        throw new MemoMaxException("Missing end time after '/to'.");
                    }
                    String from = fromTo[0].trim();
                    String to = fromTo[1].trim();
                    if (event.isEmpty()) {
                        throw new MemoMaxException("Task description cannot be empty");
                    }
                    if (from.isEmpty()) {
                        throw new MemoMaxException("Start time cannot be empty");
                    }
                    if (to.isEmpty()) {
                        throw new MemoMaxException("End time cannot be empty");
                    }
                    tasks[taskCounter] = new Event(event, from, to);
                    System.out.println("Got it. I've added this task:\n  " + tasks[taskCounter].toString());
                    taskCounter += 1;
                    ui.showMessage("Now you have " + taskCounter + " task(s) in the list.");
                } catch (MemoMaxException e) {
                    ui.showErrorMessage(e.getMessage());
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
            } else { // Unknown command entered
                ui.showMessage("Invalid command\n" +
                        "Type 'help' for more options");
            }
        }

        ui.showMessage("Bye. Hope to see you again soon!");
    }
}
