package metro.model;

import java.util.ArrayList;
import java.util.List;

// CONCEPT: Composition (Route contains and manages lifecycle of RouteStep objects)
public class Route {
    private String source;
    private String destination;
    private List<String> stationPath;
    
    // Composition: Route owns these RouteStep objects
    private List<RouteStep> steps;
    
    private int totalStops;
    private double estimatedTimeMinutes;
    private double estimatedDistanceKm;

    public Route(String source, String destination, List<String> path) {
        this.source = source;
        this.destination = destination;
        this.stationPath = path;
        this.steps = new ArrayList<>();
        this.totalStops = Math.max(0, path.size() - 1);
        this.estimatedDistanceKm = totalStops * 2.3; // Average ~2.3 km per station
        this.estimatedTimeMinutes = totalStops * 3.0; // Average ~3 minutes per station

        // Composition: create RouteSteps inside Route
        for (int i = 0; i < path.size() - 1; i++) {
            steps.add(new RouteStep(path.get(i), path.get(i + 1), "Metro Line"));
        }
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public List<String> getStationPath() {
        return stationPath;
    }

    public int getTotalStops() {
        return totalStops;
    }

    public double getEstimatedDistanceKm() {
        return estimatedDistanceKm;
    }

    public double getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }

    // CONCEPT: Java String Manipulation & StringBuilder
    public String getFormattedDirections() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=======================================================\n");
        sb.append("   JOURNEY PLANNER: ").append(source).append(" -> ").append(destination).append("\n");
        sb.append("=======================================================\n");
        sb.append(String.format("Total Stops      : %d\n", totalStops));
        sb.append(String.format("Estimated Time   : %.0f Minutes\n", estimatedTimeMinutes));
        sb.append(String.format("Estimated Distance: %.1f KM\n", estimatedDistanceKm));
        sb.append("-------------------------------------------------------\n");
        sb.append("Route Path:\n");

        for (int i = 0; i < stationPath.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(stationPath.get(i));
            if (i == 0) sb.append("  [START]");
            else if (i == stationPath.size() - 1) sb.append("  [DESTINATION]");
            sb.append("\n");
        }
        sb.append("=======================================================\n");
        return sb.toString();
    }
}
