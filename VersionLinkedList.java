class VersionNode {

    int versionNumber; // The version identifier (1, 2, 3, ...)
    String content; // The file content at this version
    VersionNode next; // Pointer to the next version in the linked list

    public VersionNode(int versionNumber, String content) {
        this.versionNumber = versionNumber;
        this.content = content;
        this.next = null;
    }
}

public class VersionLinkedList {

    private VersionNode head; // First version in the list
    private VersionNode tail; // Last (most recent) version in the list
    private int Counter; // Tracks the next version number to assign

    public VersionLinkedList() {
        head = null;
        tail = null;
        Counter = 1;
    }

    // Adds a new version node to the linked list
    VersionNode addVersion(String content) {
        VersionNode newNode = new VersionNode(Counter, content);
        Counter++;

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        return newNode;
    }

    // Searches for a version node by version number
    VersionNode findVersion(int versionNumber) {
        VersionNode current = head;

        while (current != null) {
            if (current.versionNumber == versionNumber) {
                return current;
            }
            current = current.next;
        }

        return null;
    }

    // Displays all versions, marking the current one
    public void displayAllVersions(VersionNode currentVersion) {
        if (head == null) {
            System.out.println("No versions saved yet.");
            return;
        }

        VersionNode current = head;
        while (current != null) {
            String marker = (current == currentVersion) ? " <-- Current Version" : "";
            System.out.println("Version " + current.versionNumber + ": " + current.content + marker);
            current = current.next;
        }
    }

    VersionNode getHead() { return head; }
    VersionNode getTail() { return tail; }
}
