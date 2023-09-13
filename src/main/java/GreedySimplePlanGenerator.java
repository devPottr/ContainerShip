import java.util.*;

/**
 * Implements the LoadingPlanGenerator interface to generate a loading plan for a container ship.
 * Uses a greedy algorithm with a simple approach to balance the weight of containers across the ship.
 */
public class GreedySimplePlanGenerator implements LoadingPlanGenerator {
    /**
     * Use the loadingManager to load the ship
     */
    private final LoadingManager loadingManager = new LoadingManager();

    /**
     * Generates a loading plan for the given container ship and list of containers.
     * Writes the plan to a CSV file.
     *
     * @param ship        The container ship to be loaded.
     * @param containers  The list of containers to be loaded onto the ship.
     * @param csvFilePath The path to the CSV file where the plan will be written.
     */
    @Override
    public void generatePlan(ContainerShip ship, List<Container> containers, String csvFilePath) {
        // Kopiere das Schiff mit einem Deep-Clone
        ContainerShip shipTemplate = ship.clone();

        // Sortiere die Container nach dem Gewicht (abwaerts)
        containers.sort(Comparator.comparing(Container::getWeight).reversed());

        // Erstelle eine Map für den Ladeplan
        Map<UUID, UUID> loadingPlan = new HashMap<>();

        // Benutze lokale Variablen um das totale Gewicht von den linken und rechten Sektionen zu verfolgen
        double leftTotal = 0;
        double rightTotal = 0;

        // Uns ist die Reihenfolge der Container in den Sektionen oder Lagerbereichen egal
        // Uns ist nur wichtig, ob sie in den linken oder rechten Sektionen sind
        List<Container> leftContainers = new ArrayList<>();
        List<Container> rightContainers = new ArrayList<>();

        // Iteriere durch jeden Container und überprüfe welche Seite des Schiffes weniger wiegt
        for (Container container : containers) {
            if (leftTotal < rightTotal) {
                leftContainers.add(container);
                leftTotal += container.getWeight();
            } else {
                rightContainers.add(container);
                rightTotal += container.getWeight();
            }
        }

        Iterator<Container> leftIterator = leftContainers.iterator();

        while (leftIterator.hasNext()) {
            // Go through the containers that should be placed on the left side of the ship and place them there
            for (Section section : shipTemplate.getSections()) {
                for (StorageArea storageArea : section.getLeftStorageAreas()) {
                    while (storageArea.canAddContainer() && leftIterator.hasNext()) {
                        Container container = leftIterator.next();
                        // Add the container to the storage area
                        storageArea.addContainer(container);
                        // Add the combination of storage area uuid and container uuid to the plan
                        loadingPlan.put(container.getId(), storageArea.getId());
                        // Remove the reference to the container from the temporary list
                        leftIterator.remove();
                    }
                }
            }
        }

        Iterator<Container> rightIterator = rightContainers.iterator();

        while (rightIterator.hasNext()) {
            // Go through the containers that should be placed on the left side of the ship and place them there
            for (Section section : shipTemplate.getSections()) {
                for (StorageArea storageArea : section.getRightStorageAreas()) {
                    while (storageArea.canAddContainer() && rightIterator.hasNext()) {
                        Container container = rightIterator.next();
                        // Add the container to the storage area
                        storageArea.addContainer(container);
                        // Add the combination of storage area uuid and container uuid to the plan
                        loadingPlan.put(container.getId(), storageArea.getId());
                        // Remove the reference to the container from the temporary list
                        rightIterator.remove();
                    }
                }
            }
        }

        loadingManager.writePlanToCSV(loadingPlan, csvFilePath);
    }

}
