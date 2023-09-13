import java.util.List;

/**
 * Defines the contract for generating a loading plan for a container ship.
 * Implementations should provide a way to generate a plan and write it to a CSV file.
 */
public interface LoadingPlanGenerator {

    /**
     * Generates a loading plan for the given container ship and list of containers.
     * Writes the plan to a CSV file.
     *
     * @param ship        The container ship to be loaded.
     * @param containers  The list of containers to be loaded onto the ship.
     * @param csvFilePath The path to the CSV file where the plan will be written.
     */
    void generatePlan(ContainerShip ship, List<Container> containers, String csvFilePath);
}
