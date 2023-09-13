import java.io.*;
import java.util.Map;
import java.util.UUID;

/**
 * Responsible for loading containers onto a ship from a terminal based on a CSV file.
 */
public class LoadingManager {

    /**
     * Writes the loading plan to a CSV file.
     *
     * @param loadingPlan  The loading plan to be written to the CSV file.
     * @param csvFilePath  The path to the CSV file where the plan will be written.
     */
    public void writePlanToCSV(Map<UUID, UUID> loadingPlan, String csvFilePath) {
        try (PrintWriter writer = new PrintWriter(new File(csvFilePath))) {
            writer.write("ContainerUUID,StackUUID\n");  // Header line for the CSV file
            for (Map.Entry<UUID, UUID> entry : loadingPlan.entrySet()) {
                writer.write(entry.getKey().toString() + "," + entry.getValue().toString() + "\n");
            }
        } catch (FileNotFoundException e) {
            // logging message
            System.out.println("File not found");
        }
    }

}
