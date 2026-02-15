# MemoMax User Guide

MemoMax is a task manager designed for users who prefer the speed of a **Command Line Interface (CLI)** while still enjoying a clean, modern Graphical User Interface (GUI). It is optimized for rapid task entry and organization, making it ideal for individuals who work primarily with their keyboards.

![Product Screenshot](./Ui.png)

## Quick Start

1.  Ensure you have **Java 17** or above installed on your computer.
2.  Download the latest `memomax.jar` from the [Releases](https://github.com/ths1959/ip/releases) page.
3.  Copy the file to the folder you want to use as the home folder for your data.
4.  Open a command terminal, `cd` into the folder where you placed the JAR file, and run the command:  
    `java -jar memomax.jar`
5.  Type a command in the input box and press **Enter** to execute it.

---

## Features

### Adding a Todo: `todo`
Adds a basic task without any date or time constraints.
* **Format**: `todo DESCRIPTION`
* **Example**: `todo Read library book`

### Adding a Deadline: `deadline`
Adds a task that needs to be completed by a specific date and time.
* **Format**: `deadline DESCRIPTION /by YYYY-MM-DD HHMM`
* **Example**: `deadline Submit weekly quiz /by 2026-02-20 2359`

### Adding an Event: `event`
Adds a task that occurs within a specific time interval.
* **Format**: `event DESCRIPTION /from YYYY-MM-DD HHMM /to YYYY-MM-DD HHMM`
* **Example**: `event Team meeting /from 2026-02-18 1400 /to 2026-02-18 1600`

### Locating Tasks: `find`
Finds tasks whose descriptions contain the given keyword.
* **Format**: `find KEYWORD`
* **Example**: `find quiz`

### Updating a Task: `update`
Edits the description of an existing task in the list.
* **Format**: `update INDEX NEW_DESCRIPTION`
* **Example**: `update 1 Read Chapter 5 of textbook`

---

## Command Summary

| Action | Format |
| :--- | :--- |
| **Todo** | `todo DESCRIPTION` |
| **Deadline** | `deadline DESCRIPTION /by YYYY-MM-DD HHMM` |
| **Event** | `event DESCRIPTION /from YYYY-MM-DD HHMM /to YYYY-MM-DD HHMM` |
| **List** | `list` |
| **Mark** | `mark INDEX` |
| **Unmark** | `unmark INDEX` |
| **Delete** | `delete INDEX` |
| **Update** | `update INDEX NEW_DESCRIPTION` |
| **Find** | `find KEYWORD` |
| **Exit** | `bye` |