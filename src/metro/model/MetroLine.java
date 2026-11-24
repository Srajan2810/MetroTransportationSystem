package metro.model;

import java.util.ArrayList;
import java.util.List;

// CONCEPT: Aggregation (MetroLine aggregates Station objects; Stations exist independently)
public class MetroLine {
    private String lineName;
    private String color;
    // Aggregation: list of stations belonging to this line
    private List<Station> stations;

    public MetroLine(String lineName, String color) {
        this.lineName = lineName;
        this.color = color;
        this.stations = new ArrayList<>();
    }

    public void addStation(Station s) {
        if (s != null && !stations.contains(s)) {
            stations.add(s);
        }
    }

    public String getLineName() {
        return lineName;
    }

    public String getColor() {
        return color;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void printLineDetails() {
        System.out.println("=== " + lineName + " (" + color + ") ===");
        System.out.println("Total Stations: " + stations.size());
        for (int i = 0; i < stations.size(); i++) {
            System.out.print(stations.get(i).getName());
            if (i < stations.size() - 1) System.out.print(" -> ");
        }
        System.out.println();
    }
}
