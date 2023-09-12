import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class GreedyBalancingLoadingPlanGenerator implements LoadingPlanGenerator {
    private static final int MAX_ITERATIONS = 10_000_000;

    @Override
    public void generatePlan(ContainerShip ship, List<Container> containers, String csvFilePath) {
        containers.sort(Comparator.comparing(Container::getWeight).reversed());
        Map<UUID, UUID> loadingPlan = createLoadingPlan(ship, containers);
        writePlanToCSV(loadingPlan, csvFilePath);
    }

    private Map<UUID, UUID> createLoadingPlan(ContainerShip ship, List<Container> containers) {
        Map<UUID, UUID> loadingPlan = new HashMap<>();
        Map<UUID, Double> simulatedWeights = new HashMap<>();
        Map<UUID, Double> sectionDifferences = new HashMap<>();
        int iterations = 0;

        for (Container container : containers) {
            if (iterations >= MAX_ITERATIONS) {
                break;
            }

            Pair<UUID, Double> bestOption = findBestStorageArea(ship, container, simulatedWeights, sectionDifferences);

            if (bestOption != null) {
                UUID bestStorageAreaId = bestOption.getKey();
                simulateContainerAddition(bestStorageAreaId, container, simulatedWeights);
                loadingPlan.put(container.getId(), bestStorageAreaId);
            } else {
                // Throw a specific exception or use a logging framework
            }

            iterations++;
        }
        return loadingPlan;
    }

    private double calculateNewDifference(Section section, double newWeight) {
        return Math.abs(section.getLeftTotalWeight() + section.getRightTotalWeight() - 2 * newWeight);
    }

    private Pair<UUID, Double> findBestStorageArea(ContainerShip ship, Container container,
                                                   Map<UUID, Double> simulatedWeights,
                                                   Map<UUID, Double> sectionDifferences) {
        UUID bestStorageAreaId = null;
        double bestGlobalDifference = Double.MAX_VALUE;

        for (Section section : ship.getSections()) {
            for (StorageArea storageArea : section.getAllStorageAreas()) {
                UUID storageAreaId = storageArea.getId();
                double newWeight = simulatedWeights.getOrDefault(storageAreaId, 0.0) + container.getWeight();
                double newLocalDifference = calculateNewDifference(section, newWeight);
                double newGlobalDifference = Math.abs(ship.getTotalLeftWeight() + ship.getTotalRightWeight() - 2 * newLocalDifference);

                if (newGlobalDifference < bestGlobalDifference) {
                    bestGlobalDifference = newGlobalDifference;
                    bestStorageAreaId = storageAreaId;
                    sectionDifferences.put(section.getId(), newLocalDifference);
                }
            }
        }
        return bestStorageAreaId != null ? new Pair<>(bestStorageAreaId, bestGlobalDifference) : null;
    }

    private void simulateContainerAddition(UUID storageAreaId, Container container,
                                           Map<UUID, Double> simulatedWeights) {
        double currentWeight = simulatedWeights.getOrDefault(storageAreaId, 0.0);
        simulatedWeights.put(storageAreaId, currentWeight + container.getWeight());
    }

    private void writePlanToCSV(Map<UUID, UUID> loadingPlan, String csvFilePath) {
        try (PrintWriter writer = new PrintWriter(new File(csvFilePath))) {
            writer.write("ContainerUUID,StackUUID\n");
            for (Map.Entry<UUID, UUID> entry : loadingPlan.entrySet()) {
                writer.write(entry.getKey().toString() + "," + entry.getValue().toString() + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
