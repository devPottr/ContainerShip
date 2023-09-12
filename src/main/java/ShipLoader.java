import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShipLoader {
    public void loadShipFromCSV(ContainerShip ship, ContainerTerminal terminal, String csvFilePath) {
        Map<UUID, UUID> loadingPlan = new HashMap<>();

        // Schritt 1: Lesen der CSV-Datei
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            reader.readLine(); // Überspringen der Kopfzeile
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                UUID containerId = UUID.fromString(parts[0]);
                UUID storageAreaId = UUID.fromString(parts[1]);
                loadingPlan.put(containerId, storageAreaId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Schritt 2: Verschieben der Container vom Terminal zum Schiff
        for (Map.Entry<UUID, UUID> entry : loadingPlan.entrySet()) {
            UUID containerId = entry.getKey();
            UUID storageAreaId = entry.getValue();

            Container container = terminal.removeContainerById(containerId); // Angenommen, dass eine solche Methode existiert
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
