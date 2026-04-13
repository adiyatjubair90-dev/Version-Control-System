import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        VersionControlSystem vcs = new VersionControlSystem();

        System.out.println();
        System.out.println("Tracked file  : " + FileManager.getTrackedFileName()); //Displays the name of tracked file name
        System.out.println("Version logbook: " + FileManager.getLogbookFileName()); //Displays the name of logbook
        System.out.println();

        boolean running = true;

        while (running) {

            System.out.println("------------------------------------");
            System.out.println("       Version Control System       ");
            System.out.println("------------------------------------");
            System.out.println("1. Add New Version");
            System.out.println("2. View Current Version");
            System.out.println("3. View All Versions");
            System.out.println("4. Undo");
            System.out.println("5. Redo");
            System.out.println("6. Revert to Specific Version");
            System.out.println("7. View Version Logbook");
            System.out.println("8. Exit");
            System.out.println("------------------------------------");

            System.out.print("Enter an integer: ");
            String input = scanner.nextLine().trim();
            System.out.println();

            if (!isInteger(input)) {
                System.out.println("Invalid, please choose between 1 and 8.");
                System.out.println();
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {

                case 1: // Add New Version
                    System.out.println("Current file content:");
                    System.out.println(FileManager.readFromFile());
                    System.out.println();
                    System.out.print("Enter new file content (or type 'cnc' to cancel): ");
                    String content = scanner.nextLine().trim();

                    if (content.equalsIgnoreCase("cnc")) {
                        System.out.println("Cancelled.");
                        break;
                    }

                    while (content.isEmpty()) {
                        System.out.println("Content cannot be empty, please try again.");
                        content = scanner.nextLine().trim();
                        if (content.equalsIgnoreCase("cnc")) {
                            break;
                        }
                    }

                    if (!content.equalsIgnoreCase("cnc")) {
                        vcs.addNewVersion(content);
                    }
                    break;

                case 2: // View Current Version
                    vcs.viewCurrentVersion();
                    break;

                case 3: // View All Versions
                    vcs.viewAllVersions();
                    break;

                case 4: // Undo
                    vcs.undo();
                    break;

                case 5: // Redo
                    vcs.redo();
                    break;

                case 6: // Revert to Specific Version
                    System.out.print("Enter version number to revert to (or 'cnc' to cancel): ");
                    String versionInput = scanner.nextLine().trim();

                    if (versionInput.equalsIgnoreCase("cnc")) {
                        System.out.println("Cancel Successful.");
                        break;
                    }

                    while (!isInteger(versionInput)) {
                        System.out.println("Invalid input. Please enter a valid version number.");
                        versionInput = scanner.nextLine().trim();
                        if (versionInput.equalsIgnoreCase("cnc")) {
                            break;
                        }
                    }

                    if (!versionInput.equalsIgnoreCase("cnc")) {
                        int versionNumber = Integer.parseInt(versionInput);
                        vcs.revertToVersion(versionNumber);
                    }
                    break;

                case 7: // View Logbook
                    viewLogbook();
                    break;

                case 8: // Exit
                    System.out.println("Exiting Version Control System. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void viewLogbook() { // Displays the full version logbook so the user can review all the past actions
        String logContent = FileManager.readLogbook(); //This line reads all the saved log entries from the logbook files
        System.out.println("----- VERSION LOGBOOK ------");
        System.out.println(logContent); //Displays all the logged actions
        System.out.println("----------------------------");
    }

    private static boolean isInteger(String str) { 
        if (str == null || str.isEmpty()) return false; // This line sees if the input is null or empty, it cant be a number
        try {
            Integer.parseInt(str); // converts string into integer
            return true; // if converted then input is valid
        } catch (NumberFormatException e) {
            return false; // if an error accours then input not valid
        }
    }
}
