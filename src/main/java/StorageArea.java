import java.util.Optional;
import java.util.UUID;

/**
 * Represents a storage area in a container terminal.
 */
public class StorageArea implements Cloneable {
    // Stack to hold the containers in this storage area
    private MyStackArray<Container> stack;

    // Maximum number of containers this storage area can hold
    private final int maxStackSize;

    // Unique identifier for this storage area
    private final UUID id;

    private final int MAX_CAPACITY = 35;

    /**
     * Creates a new StorageArea with the given maximum stack size.
     *
     * @param maxStackSize The maximum number of containers this storage area can hold.
     */
    public StorageArea(int maxStackSize) {
        this.stack = new MyStackArray<>(MAX_CAPACITY);
        this.maxStackSize = maxStackSize;
        this.id = UUID.randomUUID();
    }

    /**
     * Checks if a new container can be added to this storage area.
     *
     * @return true if a new container can be added, false otherwise.
     */
    public boolean canAddContainer() {
        return !stack.isFull();
    }

    /**
     * Adds a container to this storage area.
     *
     * @param container The container to be added.
     */
    public void addContainer(Container container) {
        if (canAddContainer()) {
            container.setStorageAreaUUID(this.id);
            stack.push(container);
        }
    }

    /**
     * Removes and returns the top container from this storage area.
     *
     * @return The removed container, or Optional.empty() if the storage area is empty.
     */
    public Optional<Container> removeContainer() {
        return stack.isEmpty() ? Optional.empty() : Optional.of(stack.pop());
    }

    /**
     * Gets the total weight of all containers in this storage area.
     *
     * @return The total weight of all containers.
     */
    public double getTotalWeight() {
        return stack.stream().mapToDouble(Container::getWeight).sum();
    }

    /**
     * Gets the number of containers in this storage area.
     *
     * @return The number of containers.
     */
    public int getContainerCount() {
        return stack.size();
    }

    /**
     * Gets the unique identifier for this storage area.
     *
     * @return The UUID of this storage area.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the maximum stack size of this storage area.
     *
     * @return The maximum stack size.
     */
    public int getMaxStackSize() {
        return maxStackSize;
    }

    /**
     * Creates a deep clone of this StorageArea object.
     *
     * @return A deep clone of this StorageArea.
     * @throws AssertionError if cloning is not supported.
     */
    public StorageArea clone() {
        try {
            StorageArea clone = (StorageArea) super.clone();
            clone.stack = new MyStackArray<>(MAX_CAPACITY);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
