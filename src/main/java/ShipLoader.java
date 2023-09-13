import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Responsible for loading containers onto a ship from a terminal based on a CSV file.
 */
public class ShipLoader {

    /**
     * Loads containers from a terminal onto a ship based on a CSV file.
     *
     * @param ship        The ship to be loaded.
     * @param terminal    The terminal where the containers are located.
     * @param csvFilePath The path to the CSV file containing the loading plan.
     */
    public void loadShipFromCSV(ContainerShip ship, ContainerTerminal terminal, String csvFilePath) {
        // Map to hold the loading plan: Container ID -> Storage Area ID
        Map<UUID, UUID> loadingPlan = new HashMap<>();

        // Step 1: Read the CSV file to populate the loading plan
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            reader.readLine(); // Skip the header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                UUID containerId = UUID.fromString(parts[0]);
                UUID storageAreaId = UUID.fromString(parts[1]);
                loadingPlan.put(containerId, storageAreaId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Step 2: Move containers from the terminal to the ship based on the loading plan
        for (Map.Entry<UUID, UUID> entry : loadingPlan.entrySet()) {
            UUID containerId = entry.getKey();
            UUID storageAreaId = entry.getValue();

            // Assume that such a method exists in the ContainerTerminal class
            Container container = terminal.removeContainerById(containerId);
            if (container != null) {
                for (Section section : ship.getSections()) {
                    for (StorageArea storageArea : section.getAllStorageAreas()) {
                        if (storageArea.getId().equals(storageAreaId)) {
                            storageArea.addContainer(container);
                            break;
                        }
                    }
                }
            }
        }
    }
}
