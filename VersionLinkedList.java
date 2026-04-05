/**
 * VersionLinkedList.java
 * 
 * Manages the linked list of file versions.
 * The linked list stores all versions in the order they were created.
 * 
 * DATA STRUCTURE ROLE:
 *   - The linked list is the "history log" of the version control system.
 *   - Each node = one version of the file.
 *   - Traversing from head to tail gives the full history in order.
 */
public class VersionLinkedList {

    private VersionNode head;       // First version in the list
    private VersionNode tail;       // Last (most recent) version in the list
    private int versionCounter;     // Tracks the next version number to assign

    /**
     * Constructor: starts with an empty list.
     */
    public VersionLinkedList() {
        head = null;
        tail = null;
        versionCounter = 1;
    }

    /**
     * Adds a new version node to the end of the linked list.
     * Returns the newly created node so it can be tracked as the current version.
     *
     * TIME COMPLEXITY: O(1) — we always have a reference to the tail.
     */
    public VersionNode addVersion(String content) {
        VersionNode newNode = new VersionNode(versionCounter, content);
        versionCounter++;

        if (head == null) {
            // List is empty — this is both head and tail
            head = newNode;
            tail = newNode;
        } else {
            // Append to the end of the list
            tail.next = newNode;
            tail = newNode;
        }

        return newNode;
    }

    /**
     * Searches the linked list for a version with the given number.
     * Returns the node if found, or null if not found.
     *
     * TIME COMPLEXITY: O(n) — we may need to traverse the whole list.
     */
    public VersionNode findVersion(int versionNumber) {
        VersionNode current = head;

        // Walk through each node and compare version numbers
        while (current != null) {
            if (current.versionNumber == versionNumber) {
                return current;  // Found it!
            }
            current = current.next;
        }

        return null;  // Not found
    }

    /**
     * Displays all versions in the linked list.
     * Marks the currently active version with "<-- Current Version".
     *
     * TIME COMPLEXITY: O(n) — visits every node once.
     */
    public void displayAllVersions(VersionNode currentVersion) {
        if (head == null) {
            System.out.println("  No versions saved yet.");
            return;
        }

        VersionNode current = head;
        while (current != null) {
            String marker = (current == currentVersion) ? "  <-- Current Version" : "";
            System.out.println("  Version " + current.versionNumber + ": " + current.content + marker);
            current = current.next;
        }
    }

    /**
     * Returns the head (first node) of the linked list.
     */
    public VersionNode getHead() {
        return head;
    }

    /**
     * Returns the tail (last node) of the linked list.
     */
    public VersionNode getTail() {
        return tail;
    }
}
