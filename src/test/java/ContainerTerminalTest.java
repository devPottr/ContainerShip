import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test the class {@link ContainerTerminal}
 */
public class ContainerTerminalTest {

    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        List<DynamicTest> dynamicTests = new ArrayList<>();

        dynamicTests.add(DynamicTest.dynamicTest("Test Add Container", () -> {
            ContainerTerminal localTerminal = new ContainerTerminal(75, 90);
            Container c = new Container(20000, false, ShippingCompany.OOCL);
            assertTrue(localTerminal.addContainer(0, 0, c));
            assertFalse(localTerminal.addContainer(0, 0, c));
        }));

        dynamicTests.add(DynamicTest.dynamicTest("Test Remove Container", () -> {
            ContainerTerminal localTerminal = new ContainerTerminal(75, 90);
            Container c = new Container(20000, false, ShippingCompany.OOCL);
            localTerminal.addContainer(0, 0, c);
            UUID generatedId = c.getId();

            Container removed = localTerminal.removeContainerById(generatedId);
            assertEquals(c, removed);
        }));

        dynamicTests.add(DynamicTest.dynamicTest("Test Get All Containers", () -> {
            ContainerTerminal localTerminal = new ContainerTerminal(75, 90);
            Container c1 = new Container(20000, false, ShippingCompany.OOCL);
            Container c2 = new Container(25000, true, ShippingCompany.MSC);
            localTerminal.addContainer(0, 0, c1);
            localTerminal.addContainer(1, 1, c2);

            List<Container> containers = localTerminal.getAllContainers();
            assertEquals(2, containers.size());
        }));

        dynamicTests.add(DynamicTest.dynamicTest("Test Place Random Containers", () -> {
            ContainerTerminal localTerminal = new ContainerTerminal(75, 90);
            localTerminal.placeRandomContainers(4000);
            List<Container> containers = localTerminal.getAllContainers();
            assertEquals(4000, containers.size());
        }));

        dynamicTests.add(DynamicTest.dynamicTest("Test Remove Container By Id", () -> {
            ContainerTerminal localTerminal = new ContainerTerminal(75, 90);
            Container c = new Container(20000, false, ShippingCompany.OOCL);
            localTerminal.addContainer(0, 0, c);
            UUID id = c.getId();

            Container removed = localTerminal.removeContainerById(id);
            assertEquals(c, removed);
        }));

        return dynamicTests.stream();
    }
}
