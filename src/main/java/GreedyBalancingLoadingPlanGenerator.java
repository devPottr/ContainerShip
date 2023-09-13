import java.util.*;

/**
 * Implements the LoadingPlanGenerator interface to generate a loading plan for a container ship.
 * Uses a greedy algorithm to balance the weight of containers across the ship.
 */
public class GreedyBalancingLoadingPlanGenerator implements LoadingPlanGenerator {
    /**
     * Maximum number of iterations to avoid infinite loops
     */
    private static final int MAX_ITERATIONS = 10_000_000;

    /**
     * Use the loadingManager to load the ship
     */
    LoadingManager loadingManager = new LoadingManager();

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
        containers.sort(Comparator.comparing(Container::getWeight).reversed());
        Map<UUID, UUID> loadingPlan = createLoadingPlan(ship, containers);
        loadingManager.writePlanToCSV(loadingPlan, csvFilePath);
    }

     /**
     * Creates a loading plan using a greedy algorithm.
     *
     * @param ship       The container ship to be loaded.
     * @param containers The list of containers to be loaded onto the ship.
     * @return A map where the key is the container ID and the value is the storage area ID.
     */
    private Map<UUID, UUID> createLoadingPlan(ContainerShip ship, List<Container> containers) {
        // Initialisiere die benötigten Datenstrukturen
        Map<UUID, UUID> loadingPlan = new HashMap<>();
        Map<UUID, Double> simulatedWeights = new HashMap<>();
        Map<UUID, Double> sectionDifferences = new HashMap<>();
        int iterations = 0;

        // Iteriere durch jeden Container
        for (Container container : containers) {
            if (iterations >= MAX_ITERATIONS) {
                break;
            }

            // Finde den besten Lagerbereich für jeden Container
            Pair<UUID, Double> bestOption = findBestStorageArea(ship, container, simulatedWeights, sectionDifferences);

            if (bestOption != null) {
                UUID bestStorageAreaId = bestOption.getKey();
                simulateContainerAddition(bestStorageAreaId, container, simulatedWeights);
                loadingPlan.put(container.getId(), bestStorageAreaId);
            } else {
                // Behandle den Fall, dass kein geeigneter Lagerbereich gefunden wird
            }

            iterations++;
        }
        return loadingPlan;
    }

    /**
     * Calculates the new weight difference for a given section after adding a new container.
     *
     * @param section   The section where the container will be added.
     * @param newWeight The weight of the new container.
     * @return The new weight difference for the section.
     */
    private double calculateNewDifference(Section section, double newWeight) {
        // Berechne die neue Gewichtsdifferenz für die Sektion
        return Math.abs(section.getLeftTotalWeight() + section.getRightTotalWeight() - 2 * newWeight);
    }

    /**
     * Finds the best storage area to place a container in, aiming to minimize the weight difference
     * between the left and right sides of the ship.
     *
     * @param ship              The container ship to be loaded.
     * @param container         The container to be placed.
     * @param simulatedWeights  A map containing the simulated weights for each storage area.
     * @param sectionDifferences A map containing the weight differences for each section.
     * @return A Pair containing the best storage area ID and the new global weight difference, or null if no suitable area is found.
     */
    private Pair<UUID, Double> findBestStorageArea(ContainerShip ship, Container container,
                                                   Map<UUID, Double> simulatedWeights,
                                                   Map<UUID, Double> sectionDifferences) {
        UUID bestStorageAreaId = null;
        double bestGlobalDifference = Double.MAX_VALUE;

        // Gehe jede Sektion des Schiffes durch
        for (Section section : ship.getSections()) {
            // Gehe jeden Lagerbereich der Sektion durch
            for (StorageArea storageArea : section.getAllStorageAreas()) {
                UUID storageAreaId = storageArea.getId();
                double newWeight = simulatedWeights.getOrDefault(storageAreaId, 0.0) + container.getWeight();
                double newLocalDifference = calculateNewDifference(section, newWeight);
                double newGlobalDifference = Math.abs(ship.getTotalLeftWeight() + ship.getTotalRightWeight() - 2 * newLocalDifference);

                // Aktualisiere die beste Option wenn eine kleinere globale Differenz gefunden wurde
                if (newGlobalDifference < bestGlobalDifference) {
                    bestGlobalDifference = newGlobalDifference;
                    bestStorageAreaId = storageAreaId;
                    sectionDifferences.put(section.getId(), newLocalDifference);
                }
            }
        }
        return bestStorageAreaId != null ? new Pair<>(bestStorageAreaId, bestGlobalDifference) : null;
    }

    /**
     * Simulates the addition of a container to a storage area by updating the simulated weight map.
     *
     * @param storageAreaId    The ID of the storage area where the container will be added.
     * @param container        The container to be added.
     * @param simulatedWeights A map containing the simulated weights for each storage area.
     */
    private void simulateContainerAddition(UUID storageAreaId, Container container,
                                           Map<UUID, Double> simulatedWeights) {
        // Beziehe das aktuell simulierte Gewicht für den Lagerbereich
        double currentWeight = simulatedWeights.getOrDefault(storageAreaId, 0.0);

        // Aktualisiere das simulierte Gewicht für den Lagerbereich
        simulatedWeights.put(storageAreaId, currentWeight + container.getWeight());
    }
}
