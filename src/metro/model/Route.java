package metro.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {
    private String source;
    private String destination;
    private List<String> stationPath;
    private List<String> stepLines;
    private List<RouteStep> steps;

    private int totalStops;
    private int interchangeCount;
    private List<String> interchangeStations;
    private List<String> interchangeDetails;
    private double estimatedTimeMinutes;
    private double estimatedDistanceKm;
    private String initialLine;

    public Route(String source, String destination, List<String> path) {
        this(source, destination, path, null);
    }

    public Route(String source, String destination, List<String> path, List<String> lines) {
        this.source = source;
        this.destination = destination;
        this.stationPath = (path != null) ? path : new ArrayList<>();
        this.steps = new ArrayList<>();
        this.interchangeStations = new ArrayList<>();
        this.interchangeDetails = new ArrayList<>();
        this.totalStops = Math.max(0, stationPath.size() - 1);
        this.estimatedDistanceKm = totalStops * 2.1; 
        this.stepLines = new ArrayList<>();
        if (lines != null && lines.size() >= totalStops) {
            this.stepLines.addAll(lines);
        } else {
            for (int i = 0; i < totalStops; i++) {
                this.stepLines.add("Metro");
            }
        }

        this.initialLine = (!stepLines.isEmpty()) ? stepLines.get(0) : "Metro";

        this.interchangeCount = 0;
        for (int i = 0; i < totalStops; i++) {
            String from = stationPath.get(i);
            String to = stationPath.get(i + 1);
            String currentLine = stepLines.get(i);

            boolean isTransfer = false;
            String nextLine = null;

            if (i < totalStops - 1) {
                nextLine = stepLines.get(i + 1);
                if (!currentLine.equalsIgnoreCase(nextLine)) {
                    isTransfer = true;
                    interchangeCount++;
                    interchangeStations.add(to);
                    interchangeDetails.add(String.format("At %s: Change from %s Line to %s Line",
                            to, currentLine, nextLine));
                }
            }

            steps.add(new RouteStep(from, to, currentLine, isTransfer, nextLine));
        }
        this.estimatedTimeMinutes = (totalStops * 2.5) + (interchangeCount * 4.0);
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public List<String> getStationPath() {
        return Collections.unmodifiableList(stationPath);
    }

    public List<RouteStep> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    public int getTotalStops() {
        return totalStops;
    }

    public int getInterchangeCount() {
        return interchangeCount;
    }

    public List<String> getInterchangeStations() {
        return Collections.unmodifiableList(interchangeStations);
    }

    public List<String> getInterchangeDetails() {
        return Collections.unmodifiableList(interchangeDetails);
    }

    public double getEstimatedDistanceKm() {
        return estimatedDistanceKm;
    }

    public double getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }

    public String getInitialLine() {
        return initialLine;
    }
    public String getInterchangeSummary() {
        if (interchangeCount == 0) {
            return String.format("Direct Route: Board %s Line (No transfer needed)", initialLine);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(interchangeCount).append(" Transfer(s): ");
        for (int i = 0; i < interchangeDetails.size(); i++) {
            sb.append(interchangeDetails.get(i));
            if (i < interchangeDetails.size() - 1) sb.append(" | ");
        }
        return sb.toString();
    }

    public String getFormattedDirections() {
        StringBuilder sb = new StringBuilder();
        sb.append("================================================================================\n");
        sb.append(String.format("   JOURNEY ROUTE: %s -> %s\n", source, destination));
        sb.append("================================================================================\n");
        sb.append(String.format("Total Stops      : %d\n", totalStops));
        sb.append(String.format("Line Changes     : %d Transfer(s)\n", interchangeCount));
        sb.append(String.format("Estimated Time   : %.0f Minutes (incl. transfer walking time)\n", estimatedTimeMinutes));
        sb.append(String.format("Estimated Distance: %.1f KM\n", estimatedDistanceKm));
        sb.append("--------------------------------------------------------------------------------\n");

        if (interchangeCount > 0) {
            sb.append("WHERE TO CHANGE TRAIN / INTERCHANGE STATIONS:\n");
            for (int i = 0; i < interchangeDetails.size(); i++) {
                sb.append(String.format("   [%d] %s\n", i + 1, interchangeDetails.get(i)));
            }
            sb.append("--------------------------------------------------------------------------------\n");
        } else {
            sb.append(String.format("DIRECT JOURNEY: Board %s Line. No train change required!\n", initialLine));
            sb.append("--------------------------------------------------------------------------------\n");
        }
        sb.append("STATION-BY-STATION GUIDANCE:\n");
        String currentRunningLine = null;
        int displayNum = 1;

        for (int i = 0; i < stationPath.size(); i++) {
            String station = stationPath.get(i);

            if (i == 0) {
                currentRunningLine = (!stepLines.isEmpty()) ? stepLines.get(0) : "Metro";
                sb.append(String.format("\n  [START] Board %s Line at %s\n", currentRunningLine, station));
                sb.append(String.format("      %2d. %s\n", displayNum++, station));
            } else {
                sb.append(String.format("      %2d. %s", displayNum++, station));
                int prevStepIdx = i - 1;
                if (prevStepIdx < steps.size() && steps.get(prevStepIdx).isInterchange()) {
                    RouteStep step = steps.get(prevStepIdx);
                    sb.append(String.format("  <-- [INTERCHANGE: Change to %s Line]", step.getNextLine()));
                }

                if (i == stationPath.size() - 1) {
                    sb.append("  [DESTINATION: Exit Station]");
                }
                sb.append("\n");
            }
        }

        sb.append("================================================================================\n");
        return sb.toString();
    }
}
