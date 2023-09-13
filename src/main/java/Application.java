import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // Erstelle zwei Containerschiffe und zwei ContainerTerminals
        ContainerShip ship1 = new ContainerShip(20, 4, 35);
        ContainerShip ship2 = new ContainerShip(20, 4, 35);
        ContainerTerminal terminal1 = new ContainerTerminal(75, 90);
        ContainerTerminal terminal2 = new ContainerTerminal(75, 90);

        // Platziere 4000 zufällig generierte Container in beide Terminals
        terminal1.placeRandomContainers(4000);
        terminal2.placeRandomContainers(4000);

        // Beziehe alle Container aus beiden Terminals
        List<Container> containerList1 = terminal1.getAllContainers();
        List<Container> containerList2 = terminal2.getAllContainers();

        // Erstelle eine Liste der Generatoren um alle durchzulaufen
        List<LoadingPlanGenerator> planGenerators = Arrays.asList(
                new GreedyBalancingLoadingPlanGenerator(),
                new GreedySimplePlanGenerator()
        );

        // Erstelle eine Liste der Schiffe und Terminals
        List<ContainerShip> ships = Arrays.asList(ship1, ship2);
        List<ContainerTerminal> terminals = Arrays.asList(terminal1, terminal2);
        List<List<Container>> containerLists = Arrays.asList(containerList1, containerList2);

        // Gehe die Liste durch und führe jeden Algorithmus aus
        for (int i = 0; i < planGenerators.size(); i++) {
            LoadingPlanGenerator planGenerator = planGenerators.get(i);
            System.out.println("Aktiver Algorithmus: " + planGenerator.getClass().getSimpleName());
            System.out.println("=====================================");
            ContainerShip ship = ships.get(i);
            ContainerTerminal terminal = terminals.get(i);
            List<Container> containerList = containerLists.get(i);

            String csvFilePath = "loading_plan_" + planGenerator.getClass().getSimpleName() + ".csv";
            planGenerator.generatePlan(ship, containerList, csvFilePath);

            ShipLoader loader = new ShipLoader();
            loader.loadShipFromCSV(ship, terminal, csvFilePath);

            ship.printLoadDistribution();
            System.out.println("=====================================");
        }
    }

}
