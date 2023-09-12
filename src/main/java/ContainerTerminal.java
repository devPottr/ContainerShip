import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class ContainerTerminal {
    private static final MersenneTwisterFast RANDOM = new MersenneTwisterFast();
    private final Container[][] grid;
    private final int rows;
    private final int cols;

    public ContainerTerminal(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Container[rows][cols];
    }

    public boolean addContainer(int row, int col, Container container) {
        if (grid[row][col] == null) {
            grid[row][col] = container;
            return true;
        }
        return false;
    }

    public Container removeContainer(int row, int col) {
        Container container = grid[row][col];
        grid[row][col] = null;
        return container;
    }

    /**
     * Retrives all containers in the terminal.
     * @return List of all containers in the terminal.
     */
    public List<Container> getAllContainers() {
        List<Container> containers = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] != null) {
                    containers.add(grid[row][col]);
                }
            }
        }
        return containers;
    }


    /**
     * Places a specific number of random containers in the terminal.
     * @param numberOfContainers The number of containers to place.
     */
    public void placeRandomContainers(int numberOfContainers) {
        int placedContainers = 0;
        while (placedContainers < numberOfContainers) {
            Container container = generateRandomContainer();
            int row = RANDOM.nextInt(rows);
            int col = RANDOM.nextInt(cols);

            if (addContainer(row, col, container)) {
                placedContainers++;
            }
        }
    }


    /**
     * Generates a random container with random attributes.
     * @return Container with random attributes.
     */
    private Container generateRandomContainer() {
        boolean isHeavy = generateIsHeavy();
        ShippingCompany shippingCompany = generateShippingCompany();
        double weight = generateWeight();
        return new Container(weight, isHeavy, shippingCompany);
    }

    /**
     * Generates a random heaviness status for the container.
     * @return boolean indicating if the container is heavy.
     */
    private boolean generateIsHeavy() {
        return RANDOM.nextBoolean();
    }

    /**
     * Randomly selects a shipping company from the list.
     * @return Name of the shipping company.
     */
    private ShippingCompany generateShippingCompany() {
        return ShippingCompany.values()[RANDOM.nextInt(ShippingCompany.values().length)];
    }

    /**
     * Generates a random weight for the container within the specified range.
     * @return Weight of the container.
     */
    private double generateWeight() {
        return  12500 + (20000 * RANDOM.nextDouble());
    }


    /**
     * Removes a container from the terminal by its ID.
     * @param containerId
     * @return The removed container, or null if no container was found.
     */
    public Container removeContainerById(UUID containerId) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Container container = grid[row][col];
                if (container != null && container.getId().equals(containerId)) {
                    grid[row][col] = null;
                    return container;
                }
            }
        }
        return null;
    }
}
