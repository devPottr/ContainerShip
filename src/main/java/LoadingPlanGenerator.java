import java.util.List;

public interface LoadingPlanGenerator {
    void generatePlan(ContainerShip ship, List<Container> containers, String csvFilePath);
}