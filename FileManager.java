import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {

   
    private static final String TRACKED_FILE = "project_file.txt"; // Stores current version content

    private static final String LOGBOOK_FILE = "version_logbook.txt"; // Keeps a logbook of versions

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Displays exact timestamps of version history


    public static void writeToFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRACKED_FILE))) { // Ensures writer is closed
            writer.write(content); // Writes the new content into the file
        } catch (IOException e) {
            System.out.println("Error writing to tracked file: " + e.getMessage());
        }
    }

    public static String readFromFile() { // this part of the code reads and returns the full content of the tracked file
        File file = new File(TRACKED_FILE);
        if (!file.exists()) { // Checks if file exists
            return "(No file content yet)";
        }

        StringBuilder sb = new StringBuilder(); // We use StringBuilder to efficiently build the full file content 
        try (BufferedReader reader = new BufferedReader(new FileReader(TRACKED_FILE))) { // This line reads the file line by line
            String line;
            while ((line = reader.readLine()) != null) { // Read each line until the end of the file
                sb.append(line).append("\n"); // This line appends each line with a newLine character
            }
        } catch (IOException e) {
            return "Error reading tracked file: " + e.getMessage(); // Error message is the reading failed
        }

        return sb.toString().trim(); // Gets rid of extra spacing
    }

    //Method to log versions in logbook
    public static void logToLogbook(String action, int versionNumber, String content) {
        String timestamp = LocalDateTime.now().format(FORMATTER); //Get the current timestamp and format it

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGBOOK_FILE, true))) {
            writer.write("---------------------------------------");
            writer.newLine();
            writer.write("Timestamp: " + timestamp);
            writer.newLine();
            writer.write("Action   : " + action);
            writer.newLine();
            writer.write("Version  : " + versionNumber);
            writer.newLine();
            writer.write("Content  : " + content);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to logbook: " + e.getMessage()); // Prints error if something goes wrong
        }
    }

    // Logs undo operations
    public static void logUndoToInitial() {
        String timestamp = LocalDateTime.now().format(FORMATTER); // Get the current timestamp and format it

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGBOOK_FILE, true))) {
            writer.write("---------------------------------------");
            writer.newLine();
            writer.write("Timestamp: " + timestamp);
            writer.newLine();
            writer.write("Action   : Undo (reverted to initial empty state)");
            writer.newLine();
            writer.write("Version  : N/A");
            writer.newLine();
            writer.write("Content  : (none)");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to logbook: " + e.getMessage()); // Prints error if something goes wrong
        }
    }


    public static String readLogbook() { // this part of the code reads and returns all entries from the logbook file
        File file = new File(LOGBOOK_FILE); // this line creates file object for logbook
        if (!file.exists()) {
            return "(No logbook entries yet. Add a version first.)"; //returns a helpful message if the logbook doesnt exist
        }

        StringBuilder sb = new StringBuilder(); // THis line builds the full logbook content
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGBOOK_FILE))) { //This line reads the logbook line by line
            String line;
            while ((line = reader.readLine()) != null) { // The while loopllops through each line in the file
                sb.append(line).append("\n"); // appends each line with a newLine
            }
        } catch (IOException e) {
            return "Error reading logbook: " + e.getMessage(); // This line returns error message if the reading fails
        }

        return sb.toString().trim(); // returns the full logbook content
    }

    // Returns the name of the tracked file (used for display messages)
    public static String getTrackedFileName() { return TRACKED_FILE; } // This line returns the tracked file name 

    // Returns the name of the logbook file (used for display messages)
    public static String getLogbookFileName() { return LOGBOOK_FILE; } // This line returns the logbook file name 
}
