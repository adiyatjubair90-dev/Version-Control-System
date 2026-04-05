/**
 * VersionNode.java
 * 
 * Represents a single version (node) in the linked list.
 * Each node stores a version number, the file content at that version,
 * and a reference to the next node in the list.
 */
public class VersionNode {

    int versionNumber;   // The version identifier (1, 2, 3, ...)
    String content;      // The file content at this version
    VersionNode next;    // Pointer to the next version in the linked list

    /**
     * Constructor: creates a new version node with the given number and content.
     */
    public VersionNode(int versionNumber, String content) {
        this.versionNumber = versionNumber;
        this.content = content;
        this.next = null;  // Next is null until another version is added after this one
    }
}
