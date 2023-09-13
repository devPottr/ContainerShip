import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Represents a section of a container ship, consisting of left and right storage areas.
 */
public class Section implements Cloneable {
    /**
     * Unique identifier for this section
     */
    private final UUID id;

    /**
     * List of storage areas on the left side of this section
     */
    private List<StorageArea> leftStorageAreas;

    /**
     * List of storage areas on the right side of this section
     */
    private List<StorageArea> rightStorageAreas;

    /**
     * Constructs a new Section with the specified number of storage areas and maximum stack size.
     *
     * @param numStorageAreas The number of storage areas on each side of the section.
     * @param maxStackSize    The maximum stack size for each storage area.
     */
    public Section(int numStorageAreas, int maxStackSize) {
        this.id = UUID.randomUUID();
        leftStorageAreas = new ArrayList<>();
        rightStorageAreas = new ArrayList<>();
        for (int i = 0; i < numStorageAreas; i++) {
            leftStorageAreas.add(new StorageArea(maxStackSize));
            rightStorageAreas.add(new StorageArea(maxStackSize));
        }
    }

    /**
     * Returns the unique identifier for this section.
     *
     * @return The UUID of this section.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Calculates the total weight of the left storage areas.
     *
     * @return The total weight of the left storage areas.
     */
    public double getLeftTotalWeight() {
        return leftStorageAreas.stream().mapToDouble(StorageArea::getTotalWeight).sum();
    }

    /**
     * Calculates the total weight of the right storage areas.
     *
     * @return The total weight of the right storage areas.
     */
    public double getRightTotalWeight() {
        return rightStorageAreas.stream().mapToDouble(StorageArea::getTotalWeight).sum();
    }

    /**
     * Calculates the difference in total weight between the left and right storage areas.
     *
     * @return The absolute difference in total weight.
     */
    public double getTotalWeightDifference() {
        return Math.abs(getLeftTotalWeight() - getRightTotalWeight());
    }

    /**
     * Finds and returns the lightest storage area from the right side of the section.
     *
     * @return The lightest right storage area, or null if no storage areas exist.
     */
    public StorageArea getLightestRightStorageArea() {
        return rightStorageAreas.stream()
                .min(Comparator.comparingDouble(StorageArea::getTotalWeight))
                .orElse(null);
    }

    /**
     * Returns the list of storage areas on the left side of the section.
     *
     * @return The list of left storage areas.
     */
    public List<StorageArea> getLeftStorageAreas() {
        return leftStorageAreas;
    }

    /**
     * Returns the list of storage areas on the right side of the section.
     *
     * @return The list of right storage areas.
     */
    public List<StorageArea> getRightStorageAreas() {
        return rightStorageAreas;
    }

    /**
     * Returns a list containing all storage areas in the section.
     *
     * @return The list of all storage areas.
     */
    public List<StorageArea> getAllStorageAreas() {
        List<StorageArea> allStorageAreas = new ArrayList<>();
        allStorageAreas.addAll(leftStorageAreas);
        allStorageAreas.addAll(rightStorageAreas);
        return allStorageAreas;
    }

    /**
     * Creates a deep clone of this Section object.
     *
     * @return A deep clone of this Section.
     * @throws AssertionError if cloning is not supported.
     */
    public Section clone() {
        try {
            Section clone = (Section) super.clone();
            clone.leftStorageAreas = new ArrayList<>();
            clone.rightStorageAreas = new ArrayList<>();
            for (StorageArea storageArea : leftStorageAreas) {
                clone.leftStorageAreas.add(storageArea.clone());
            }
            for (StorageArea storageArea : rightStorageAreas) {
                clone.rightStorageAreas.add(storageArea.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
