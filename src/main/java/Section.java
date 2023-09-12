import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Section implements Cloneable {
    private UUID id;
    private List<StorageArea> leftStorageAreas;
    private List<StorageArea> rightStorageAreas;

    public Section(int numStorageAreas, int maxStackSize) {
        this.id = UUID.randomUUID();
        leftStorageAreas = new ArrayList<>();
        rightStorageAreas = new ArrayList<>();
        for (int i = 0; i < numStorageAreas; i++) {
            leftStorageAreas.add(new StorageArea(maxStackSize));
            rightStorageAreas.add(new StorageArea(maxStackSize));
        }
    }

    public UUID getId() {
        return id;
    }

    public double getLeftTotalWeight() {
        return leftStorageAreas.stream().mapToDouble(StorageArea::getTotalWeight).sum();
    }

    public double getRightTotalWeight() {
        return rightStorageAreas.stream().mapToDouble(StorageArea::getTotalWeight).sum();
    }

    public double getTotalWeightDifference() {
        return Math.abs(getLeftTotalWeight() - getRightTotalWeight());
    }

    public StorageArea getLightestRightStorageArea() {
        return rightStorageAreas.stream().min((a, b) -> Double.compare(a.getTotalWeight(), b.getTotalWeight())).orElse(null);
    }

    public List<StorageArea> getLeftStorageAreas() {
        return leftStorageAreas;
    }

    public List<StorageArea> getRightStorageAreas() {
        return rightStorageAreas;
    }


    public List<StorageArea> getAllStorageAreas() {
        List<StorageArea> allStorageAreas = new ArrayList<>();
        allStorageAreas.addAll(leftStorageAreas);
        allStorageAreas.addAll(rightStorageAreas);
        return allStorageAreas;
    }

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
