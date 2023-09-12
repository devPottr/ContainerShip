import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a container ship with multiple sections.
 */
public class ContainerShip implements Cloneable {
    // List of sections in the ship
    private List<Section> sections;

    // Unique identifier for this ship
    private final UUID id;

    /**
     * Creates a new ContainerShip with the given number of sections, storage areas per section, and maximum stack size.
     *
     * @param numSections     The number of sections in the ship.
     * @param numStorageAreas The number of storage areas in each section.
     * @param maxStackSize    The maximum stack size for each storage area.
     */
    public ContainerShip(int numSections, int numStorageAreas, int maxStackSize) {
        this.sections = new ArrayList<>();
        for (int i = 0; i < numSections; i++) {
            this.sections.add(new Section(numStorageAreas, maxStackSize));
        }
        this.id = UUID.randomUUID();
    }

    /**
     * Gets the unique identifier for this ship.
     *
     * @return The UUID of this ship.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the list of sections in this ship.
     *
     * @return The list of sections.
     */
    public List<Section> getSections() {
        return sections;
    }

    /**
     * Prints the load distribution across all sections of the ship.
     */
    public void printLoadDistribution() {
        double totalLeftWeight = 0;
        double totalRightWeight = 0;

        for (int i = 0; i < sections.size(); i++) {
            Section section = sections.get(i);
            double leftWeight = section.getLeftTotalWeight();
            double rightWeight = section.getRightTotalWeight();

            totalLeftWeight += leftWeight;
            totalRightWeight += rightWeight;

            System.out.println("Section " + (i + 1));
            System.out.println("  Total Weight Left: " + leftWeight);
            System.out.println("  Total Weight Right: " + rightWeight);
        }

        System.out.println("Total Ship Weight - Left: " + totalLeftWeight);
        System.out.println("Total Ship Weight - Right: " + totalRightWeight);
        System.out.println("Balanced Value: " + Math.abs(totalLeftWeight - totalRightWeight));
    }

    @Override
    protected ContainerShip clone() {
        try {
            ContainerShip cloned = (ContainerShip) super.clone();
            cloned.sections = new ArrayList<>();
            for (Section section : sections) {
                cloned.sections.add(section.clone());
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Calculates the total weight on the left side of the ship.
     *
     * @return The total weight on the left side.
     */
    public double getTotalLeftWeight() {
        return sections.stream()
                .mapToDouble(Section::getLeftTotalWeight)
                .sum();
    }

    /**
     * Calculates the total weight on the right side of the ship.
     *
     * @return The total weight on the right side.
     */
    public double getTotalRightWeight() {
        return sections.stream()
                .mapToDouble(Section::getRightTotalWeight)
                .sum();
    }

    /**
     * Calculates the balance value of the ship, which is the absolute difference
     * between the total weight on the left and the total weight on the right.
     *
     * @return The balance value.
     */
    public double getBalanceValue() {
        return Math.abs(getTotalLeftWeight() - getTotalRightWeight());
    }
}
