import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;

public class ContainerTerminalTest {
    private ContainerTerminal terminal;

    @BeforeEach
    public void setup() {
        terminal = new ContainerTerminal(75, 90);
    }

    @Test
    public void testAddContainer() {
        Container c = new Container(20000, false, ShippingCompany.OOCL);
        assertTrue(terminal.addContainer(0, 0, c));
        assertFalse(terminal.addContainer(0, 0, c));
    }

    @Test
    public void testRemoveContainer() {
        Container c = new Container(20000, false, ShippingCompany.OOCL);
        terminal.addContainer(0, 0, c);
        Container removed = terminal.removeContainer(0, 0);
        assertEquals(c, removed);
    }

    @Test
    public void testGetAllContainers() {
        Container c1 = new Container(20000, false, ShippingCompany.OOCL);
        Container c2 = new Container(25000, true, ShippingCompany.MSC);
        terminal.addContainer(0, 0, c1);
        terminal.addContainer(1, 1, c2);

        List<Container> containers = terminal.getAllContainers();
        assertEquals(2, containers.size());
    }

    @Test
    public void testPlaceRandomContainers() {
        terminal.placeRandomContainers(4000);
        List<Container> containers = terminal.getAllContainers();
        assertEquals(4000, containers.size());
    }

    @Test
    public void testRemoveContainerById() {
        Container c = new Container(20000, false, ShippingCompany.OOCL);
        terminal.addContainer(0, 0, c);
        UUID id = c.getId();

        Container removed = terminal.removeContainerById(id);
        assertEquals(c, removed);
    }
}
