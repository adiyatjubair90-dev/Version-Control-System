import java.util.Scanner;

/**
 * Main.java
 * 
 * Entry point of the Version Control System application.
 * 
 * Responsibilities:
 *   - Display the menu to the user.
 *   - Read user input using Scanner.
 *   - Call the appropriate method in VersionControlSystem.
 *   - Keep running in a loop until the user chooses to exit.
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        VersionControlSystem vcs = new VersionControlSystem();  // Initialize the system

        System.out.println();

        boolean running = true;

        // Main program loop — keeps showing the menu until user exits
        while (running) {

            printMenu();

            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim();

            System.out.println();

            // Validate that the input is a number
            if (!isInteger(input)) {
                System.out.println("  Invalid input. Please enter a number between 1 and 7.");
                System.out.println();
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {

                case 1:
                    // Add a new version
                    System.out.print("  Enter new file content: ");
                    String content = scanner.nextLine().trim();
                    if (content.isEmpty()) {
                        System.out.println("  Content cannot be empty. Version not added.");
                    } else {
                        vcs.addNewVersion(content);
                    }
                    break;

                case 2:
                    // View current version
                    vcs.viewCurrentVersion();
                    break;

                case 3:
                    // View all versions
                    vcs.viewAllVersions();
                    break;

                case 4:
                    // Undo
                    vcs.undo();
                    break;

                case 5:
                    // Redo
                    vcs.redo();
                    break;

                case 6:
                    // Revert to a specific version
                    System.out.print("  Enter version number to revert to: ");
                    String versionInput = scanner.nextLine().trim();
                    if (!isInteger(versionInput)) {
                        System.out.println("  Invalid input. Please enter a valid version number.");
                    } else {
                        int versionNumber = Integer.parseInt(versionInput);
                        vcs.revertToVersion(versionNumber);
                    }
                    break;

                case 7:
                    // Exit
                    System.out.println("  Exiting Version Control System. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("  Invalid choice. Please enter a number between 1 and 7.");
            }

            System.out.println();
        }

        scanner.close();
    }

    /**
     * Prints the main menu to the console.
     */
    private static void printMenu() {
        System.out.println("========================================");
        System.out.println("       Version Control System           ");
        System.out.println("========================================");
        System.out.println("  1. Add New Version");
        System.out.println("  2. View Current Version");
        System.out.println("  3. View All Versions");
        System.out.println("  4. Undo");
        System.out.println("  5. Redo");
        System.out.println("  6. Revert to Specific Version");
        System.out.println("  7. Exit");
        System.out.println("----------------------------------------");
    }

    /**
     * Helper method: checks if a string can be parsed as an integer.
     * Used to safely handle non-numeric user input without crashing.
     */
    private static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
