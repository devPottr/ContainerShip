import java.util.*;

public class GreedySimplePlanGenerator implements LoadingPlanGenerator {
    private final LoadingManager loadingManager = new LoadingManager();

    @Override
    public void generatePlan(ContainerShip ship, List<Container> containers, String csvFilePath) {
        // Clone the ship
        ContainerShip shipTemplate = ship.clone();

        // Initialize a map to simulate the weight distribution of StorageAreas
        // Map<UUID, Double> simulatedWeights = new HashMap<>();

        // Sort containers by weight
        containers.sort(Comparator.comparing(Container::getWeight).reversed());

        // Create a map to hold the loading plan
        Map<UUID, UUID> loadingPlan = new HashMap<>();

        // For simplicity use a local variable to track the total weight on the left and right
        double leftTotal = 0;
        double rightTotal = 0;

        // We don't care about the order of the containers in the different sections and storage areas
        // We only care if they are in left or right sections
        List<Container> leftContainers = new ArrayList<>();
        List<Container> rightContainers = new ArrayList<>();

        // Go through each container and check which side (either left or right) weighs less.
        // Then add the container to this side
        for (Container container : containers) {
            if (leftTotal < rightTotal) {
                leftContainers.add(container);
                leftTotal += container.getWeight();
            } else {
                rightContainers.add(container);
                rightTotal += container.getWeight();
            }
        }

        // TODO: the complexity for sorting the container beforehand is ???
        // NOTE: the complexity for the algorithm is O(n)
        // TODO: find out total complexity

        // TODO: is it possible that the capacity of one side is used up for 4000 containers and max. 2800 per side?

        // TODO: use a function for the DRY-Principle

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
        // After balancing, create CSV file

    }
}
