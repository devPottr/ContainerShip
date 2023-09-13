import java.util.UUID;

/**
 * Represents a shipping container with various attributes.
 */
public class Container {
    /**
     * Unique identifier for the container
     */
    private final UUID id;

    /**
     * Weight of the container
     */
    private final double weight;

    /**
     * Indicates whether the container is heavy
     */
    private final boolean isHeavy;

    /**
     * The shipping company responsible for this container
     */
    private final ShippingCompany shippingCompany;

    /**
     * UUID of the StorageArea where this container is placed
     */
    private UUID storageAreaUUID;

    /**
     * Creates a new Container with the given attributes.
     *
     * @param weight          The weight of the container.
     * @param isHeavy         Indicates whether the container is heavy.
     * @param shippingCompany The shipping company responsible for this container.
     */
    public Container(double weight, boolean isHeavy, ShippingCompany shippingCompany) {
        // Erstelle eine zufällige UUID für den Container
        this.id = UUID.randomUUID();

        // Initialisiere die restlichen Klassenvariablen anhand der Parameter
        this.weight = weight;
        this.isHeavy = isHeavy;
        this.shippingCompany = shippingCompany;
    }

    /**
     * Gets the unique identifier for this container.
     *
     * @return The UUID of this container.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the weight of this container.
     *
     * @return The weight of this container.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Checks if the container is heavy.
     *
     * @return true if the container is heavy, false otherwise.
     */
    public boolean isHeavy() {
        return isHeavy;
    }

    /**
     * Gets the shipping company responsible for this container.
     *
     * @return The shipping company.
     */
    public ShippingCompany getShippingCompany() {
        return shippingCompany;
    }
    /**
     * Gets the UUID of the StorageArea where this container is placed.
     *
     * @return The UUID of the StorageArea.
     */
    public UUID getStorageAreaUUID() {
        return storageAreaUUID;
    }

    /**
     * Sets the UUID of the StorageArea where this container is placed.
     *
     * @param storageAreaUUID The UUID of the StorageArea.
     */
    public void setStorageAreaUUID(UUID storageAreaUUID) {
        this.storageAreaUUID = storageAreaUUID;
    }
}
