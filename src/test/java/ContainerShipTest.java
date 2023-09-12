import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

public class ContainerShipTest {

    @TestFactory
    Stream<DynamicTest> testContainerShipInitialization() {
        return Stream.of(
                new int[] {20, 4, 35},
                new int[] {10, 2, 20},
                new int[] {5, 1, 10}
        ).map(config -> DynamicTest.dynamicTest(
                "Test for ship with " + config[0] + " sections, " + config[1] + " storage areas, and max stack size of " + config[2],
                () -> {
                    ContainerShip ship = new ContainerShip(config[0], config[1], config[2]);
                    assertNotNull(ship.getId());
                    assertEquals(config[0], ship.getSections().size());

                    Section section = ship.getSections().get(0);
                    assertNotNull(section.getId());
                    assertEquals(config[1], section.getLeftStorageAreas().size());
                    assertEquals(config[1], section.getRightStorageAreas().size());

                    StorageArea area = section.getLeftStorageAreas().get(0);
                    assertNotNull(area.getId());
                    assertEquals(config[2], area.getMaxStackSize());
                }
        ));
    }
}
