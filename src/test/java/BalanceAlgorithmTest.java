import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class to test the algorithms {@link ContainerShip}
 */
public class BalanceAlgorithmTest {

    @TestFactory
    Collection<DynamicTest> dynamicTests() {
        double acceptableThreshold = 6250;
        return Arrays.asList(
                dynamicTest("GreedyBalancingLoadingAlgorithm", new GreedyBalancingLoadingPlanGenerator(), acceptableThreshold),
                dynamicTest("GreedySimpleAlgorithm", new GreedySimplePlanGenerator(), acceptableThreshold)
        );
    }

    private DynamicTest dynamicTest(String testName, LoadingPlanGenerator planGenerator, double acceptableThreshold) {
        return DynamicTest.dynamicTest(testName, () -> {
            // Schritt 1: Initialisierung
            ContainerShip ship = new ContainerShip(20, 4, 35);
            ContainerTerminal terminal = new ContainerTerminal(75, 90);
            terminal.placeRandomContainers(4000);
            List<Container> containers = terminal.getAllContainers();

            // Schritt 2: Algorithmus zur Lastverteilung
            String csvFilePath = "balanced_loading_plan.csv";
            planGenerator.generatePlan(ship, containers, csvFilePath);

            // Schritt 3: Beladung des Schiffes
            ShipLoader loader = new ShipLoader();
            loader.loadShipFromCSV(ship, terminal, csvFilePath);

            // Schritt 4: Überprüfung
            double leftWeight = ship.getTotalLeftWeight();
            double rightWeight = ship.getTotalRightWeight();
            double imbalance = Math.abs(leftWeight - rightWeight);

            // Überprüfen, ob die Balance innerhalb eines akzeptablen Bereichs liegt
            assertTrue(imbalance <= acceptableThreshold);
        });
    }
}
