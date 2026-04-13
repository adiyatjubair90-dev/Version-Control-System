import java.util.Stack;

public class VersionControlSystem {

    private VersionLinkedList linkedList;   // Linked list holding all version nodes
    private VersionNode currentVersion;     // Pointer to the active version
    private Stack<VersionNode> undoStack;   // Undo history
    private Stack<VersionNode> redoStack;   // Redo history

    // Constructor: initialises the system with empty structures
    public VersionControlSystem() {
        linkedList   = new VersionLinkedList();
        undoStack    = new Stack<>();
        redoStack    = new Stack<>();
        currentVersion = null;
    }


    // Add
    public void addNewVersion(String content) {

        undoStack.push(currentVersion); // Save current state so it can be undone
        redoStack.clear(); // New change invalidates any future redo states

        currentVersion = linkedList.addVersion(content);

        // Persist to actual file
        FileManager.writeToFile(content);

        FileManager.logToLogbook("Added", currentVersion.versionNumber, content); //This line logs the new version details into the logbook to track

        System.out.println("Version " + currentVersion.versionNumber + " added."); // confirm that a new version was succesfully added
        System.out.println("Saved to: " + FileManager.getTrackedFileName()); //Shows which file the content was saved
        System.out.println("Logged in: " + FileManager.getLogbookFileName()); //Prints where the action was recorded
    }

    // View
    public void viewCurrentVersion() {
        if (currentVersion == null) {
            System.out.println("No current version found.");
        }
        else {
            System.out.println("Current Version: Version " + currentVersion.versionNumber); //show the version number if it exists
            System.out.println("File content (" + FileManager.getTrackedFileName() + "):"); //Prints which file we are reading from
            System.out.println(FileManager.readFromFile()); // prints the content of the current version 
        }
    }

    // View All
    public void viewAllVersions() {
        System.out.println("All Versions:");
        System.out.println("-------------------------------------");
        linkedList.displayAllVersions(currentVersion);
        System.out.println("-------------------------------------");
    }

    // Undo
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("No more actions to undo.");
            return;
        }

        redoStack.push(currentVersion); // Current state is now redo-able
        currentVersion = undoStack.pop(); // Restore previous state

        if (currentVersion == null) {
            FileManager.writeToFile(""); // clears the file by writing empty content since there's no version 
            FileManager.logUndoToInitial(); // log that we reverted back to the initial empty state
            System.out.println("Undo successful. No current version (reverted to initial state)."); // Lets the user know that undo was successful and no version exists
        } else {
            FileManager.writeToFile(currentVersion.content); // This line restores the file with the content of the previous version
            FileManager.logToLogbook("Undo", currentVersion.versionNumber, currentVersion.content); // Logs the undo action with version number and its content
            System.out.println("Undo successful. Current version: Version " + currentVersion.versionNumber); // after the undo, this line informs the user which version is active
        }

        System.out.println("Logged in: " + FileManager.getLogbookFileName()); //Displays the logbook's name where the action was recorded 
    }

    // Redo
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("No more actions to redo.");
            return;
        }

        undoStack.push(currentVersion); // Current state is now undo-able again
        currentVersion = redoStack.pop(); // Move forward

        FileManager.writeToFile(currentVersion.content); //Restores the file content to match the version that was redid
        FileManager.logToLogbook("Redo", currentVersion.versionNumber, currentVersion.content); // Logs the redo action so its recorded in the history with the version details

        System.out.println("Redo successful. Current version: Version " + currentVersion.versionNumber); // This line lets the user know that the redo was successful and shows the current version number 
        System.out.println("Logged in: " + FileManager.getLogbookFileName()); // This line shows where the redo action was recorded
    }

    // Revert
    public void revertToVersion(int versionNumber) {

        VersionNode targetVersion = linkedList.findVersion(versionNumber);

        if (targetVersion == null) {
            System.out.println("Version " + versionNumber + " not found.");
            return;
        }

        if (targetVersion == currentVersion) {
            System.out.println("Version " + versionNumber + " is already the current version.");
            return;
        }

        undoStack.push(currentVersion); // Allow this revert to be undone
        redoStack.clear(); // Revert creates a new branch, clear redo

        currentVersion = targetVersion;

        FileManager.writeToFile(currentVersion.content); //Updates the file so now it matches the version we reverted to
        FileManager.logToLogbook("Reverted", currentVersion.versionNumber, currentVersion.content);

        System.out.println("Reverted successfully. Current version: Version " + currentVersion.versionNumber);
        System.out.println("Saved to: " + FileManager.getTrackedFileName()); // Shows which file was updated with this operation
        System.out.println("Logged in: " + FileManager.getLogbookFileName()); // Shows where the revert operation was logged
    }
}
