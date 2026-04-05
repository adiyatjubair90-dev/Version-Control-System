import java.util.Stack;

/**
 * VersionControlSystem.java
 * 
 * The core logic of the version control system.
 * 
 * DATA STRUCTURE ROLES:
 *   - linkedList   : stores the full history of all file versions (linked list).
 *   - undoStack    : holds versions we can go back to (stack — LIFO order).
 *   - redoStack    : holds versions we can re-apply after an undo (stack — LIFO order).
 *   - currentVersion : a pointer to whichever version is currently "active".
 */
public class VersionControlSystem {

    private VersionLinkedList linkedList;       // Full version history
    private VersionNode currentVersion;         // Currently active version
    private Stack<VersionNode> undoStack;       // Stack of versions to undo to
    private Stack<VersionNode> redoStack;       // Stack of versions to redo

    /**
     * Constructor: sets up an empty system and creates the first (initial) version.
     */
    public VersionControlSystem() {
        linkedList = new VersionLinkedList();
        undoStack = new Stack<>();
        redoStack = new Stack<>();

        // Create an initial version so the system is never empty at start
        currentVersion = linkedList.addVersion("Initial file content");
        System.out.println("  System initialized. Starting with Version 1: \"Initial file content\"");
    }

    // -------------------------------------------------------------------------
    // 1. ADD NEW VERSION
    // -------------------------------------------------------------------------

    /**
     * Adds a new version to the linked list.
     * - Pushes the current version onto the undo stack (so we can undo back to it).
     * - Clears the redo stack (a new change invalidates future redo history).
     * - Updates the current version pointer to the new node.
     *
     * TIME COMPLEXITY: O(1)
     */
    public void addNewVersion(String content) {
        // Save current state to undo stack before changing
        undoStack.push(currentVersion);

        // Clear redo history — a new change means no more "future" to redo
        redoStack.clear();

        // Add to linked list and update current pointer
        currentVersion = linkedList.addVersion(content);

        System.out.println("  Version " + currentVersion.versionNumber + " added successfully.");
    }

    // -------------------------------------------------------------------------
    // 2. VIEW CURRENT VERSION
    // -------------------------------------------------------------------------

    /**
     * Displays the currently active version's number and content.
     */
    public void viewCurrentVersion() {
        if (currentVersion == null) {
            System.out.println("  No current version found.");
        } else {
            System.out.println("  Current Version: Version " + currentVersion.versionNumber);
            System.out.println("  Content        : " + currentVersion.content);
        }
    }

    // -------------------------------------------------------------------------
    // 3. VIEW ALL VERSIONS
    // -------------------------------------------------------------------------

    /**
     * Traverses the entire linked list and prints all versions.
     * Highlights which version is currently active.
     *
     * TIME COMPLEXITY: O(n)
     */
    public void viewAllVersions() {
        System.out.println("  All Versions:");
        System.out.println("  " + "-".repeat(50));
        linkedList.displayAllVersions(currentVersion);
        System.out.println("  " + "-".repeat(50));
    }

    // -------------------------------------------------------------------------
    // 4. UNDO
    // -------------------------------------------------------------------------

    /**
     * Undoes the most recent change.
     * - Pushes the current version onto the redo stack (so it can be redone later).
     * - Pops the top of the undo stack and makes it the new current version.
     *
     * TIME COMPLEXITY: O(1) — stack push/pop are constant time.
     */
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("  No more actions to undo.");
            return;
        }

        // Save current version to redo stack before going back
        redoStack.push(currentVersion);

        // Restore the previous version from the undo stack
        currentVersion = undoStack.pop();

        System.out.println("  Undo successful. Current version is now Version " + currentVersion.versionNumber + ".");
    }

    // -------------------------------------------------------------------------
    // 5. REDO
    // -------------------------------------------------------------------------

    /**
     * Redoes a previously undone change.
     * - Pushes the current version onto the undo stack (so it can be undone again).
     * - Pops the top of the redo stack and makes it the new current version.
     *
     * TIME COMPLEXITY: O(1) — stack push/pop are constant time.
     */
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("  No more actions to redo.");
            return;
        }

        // Save current version to undo stack before moving forward
        undoStack.push(currentVersion);

        // Restore the undone version from the redo stack
        currentVersion = redoStack.pop();

        System.out.println("  Redo successful. Current version is now Version " + currentVersion.versionNumber + ".");
    }

    // -------------------------------------------------------------------------
    // 6. REVERT TO SPECIFIC VERSION
    // -------------------------------------------------------------------------

    /**
     * Searches the linked list for a specific version number and makes it current.
     * - Pushes the old current version onto the undo stack.
     * - Clears the redo stack (reverting is a "new action").
     *
     * TIME COMPLEXITY: O(n) — requires searching the linked list.
     */
    public void revertToVersion(int versionNumber) {
        // Search the linked list for the requested version
        VersionNode target = linkedList.findVersion(versionNumber);

        if (target == null) {
            System.out.println("  Version " + versionNumber + " not found.");
            return;
        }

        if (target == currentVersion) {
            System.out.println("  Version " + versionNumber + " is already the current version.");
            return;
        }

        // Save current version before reverting
        undoStack.push(currentVersion);

        // Clear redo — reverting counts as a new action
        redoStack.clear();

        // Set the target as the new current version
        currentVersion = target;

        System.out.println("  Reverted successfully. Current version is now Version " + currentVersion.versionNumber + ".");
    }
}
