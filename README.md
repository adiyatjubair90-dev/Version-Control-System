

# Version Control System in Java

## 🎥 Demo

[![Demo](https://img.youtube.com/vi/YOUR_VIDEO_ID/0.jpg)](https://youtu.be/JQeuKI_IYII)

A simple **Version Control System** built in Java for a Data Structures course project.  
This program allows users to create and manage versions of a tracked text file using a **linked list** for version storage and **stacks** for undo/redo operations.

## Features

- Add and save new file versions
- View the current version
- View all saved versions
- Undo changes
- Redo changes
- Revert to a specific version
- Store the latest content in a tracked file
- Maintain a version logbook with timestamps

## Data Structures Used

This project demonstrates the use of core data structures:

- **Singly Linked List**
  - Stores all versions in order
  - Each node contains:
    - version number
    - file content
    - pointer to the next version

- **Stacks**
  - `undoStack` keeps track of previous versions for undo
  - `redoStack` keeps track of undone versions for redo

## Project Files

- `Main.java`  
  Contains the menu-driven user interface and handles user input.

- `VersionControlSystem.java`  
  Contains the main logic for adding versions, undo, redo, viewing versions, and reverting.

- `VersionLinkedList.java`  
  Implements the linked list structure used to store version history.

- `FileManager.java`  
  Handles reading/writing the tracked file and maintaining the version logbook.

## How It Works

### 1. Add New Version
When the user adds a new version:
- the content is stored as a new node in the linked list
- the current version is pushed onto the undo stack
- the redo stack is cleared
- the content is written to the tracked file
- the action is recorded in the logbook

### 2. Undo
Undo moves back to the previous version:
- the current version is pushed onto the redo stack
- the previous version is restored from the undo stack
- the tracked file is updated
- the action is logged

### 3. Redo
Redo restores a version that was undone:
- the current version is pushed onto the undo stack
- the next version is restored from the redo stack
- the tracked file is updated
- the action is logged

### 4. Revert to Specific Version
The user can enter a version number to restore that exact version as the current version.

## Output Files

The program creates and updates two text files automatically:

- `project_file.txt`  
  Stores the content of the current active version

- `version_logbook.txt`  
  Stores the full history of actions with timestamps

## Menu Options

When the program runs, the user can choose from:

1. Add New Version  
2. View Current Version  
3. View All Versions  
4. Undo  
5. Redo  
6. Revert to Specific Version  
7. View Version Logbook  
8. Exit  

## Example Run

```text
------------------------------------
       Version Control System
------------------------------------
1. Add New Version
2. View Current Version
3. View All Versions
4. Undo
5. Redo
6. Revert to Specific Version
7. View Version Logbook
8. Exit
------------------------------------
Enter an integer:
