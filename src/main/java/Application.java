import java.util.List;

public class Application {
    public static void main(String[] args) {
        // Create a ContainerShip with 20 sections, 4 storage areas per section, and a maximum stack size of 35
        ContainerShip ship = new ContainerShip(20, 4, 35);

        // Create a ContainerTerminal with 75 rows and 90 columns
        ContainerTerminal terminal = new ContainerTerminal(75, 90);

        // Place 4000 random containers in the terminal
        terminal.placeRandomContainers(4000);

        // Retrieve all containers from the terminal
        List<Container> containerList = terminal.getAllContainers();

        // Create an instance of GreedyBalancingLoadingPlanGenerator
        //LoadingPlanGenerator planGenerator = new GreedyBalancingLoadingPlanGenerator();
        LoadingPlanGenerator planGenerator = new GreedySimplePlanGenerator();

        // Generate the loading plan and save it to a CSV file
        String csvFilePath = "loading_plan.csv";
        planGenerator.generatePlan(ship, containerList, csvFilePath);

        // Optional: Display the load distribution (if such a method exists in the ContainerShip class)
        // ship.printLoadDistribution();

        // Create an instance of ShipLoader
        ShipLoader loader = new ShipLoader();

        // Load the ship based on the generated CSV file
        loader.loadShipFromCSV(ship, terminal, csvFilePath);

        // Optional: Display the load distribution again to see the changes
        ship.printLoadDistribution();
    }
}
