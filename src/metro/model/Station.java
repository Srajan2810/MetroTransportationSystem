package metro.model;

public class Station {

    public static int totalStations = 0;
    private String name;
    private String line;
    private boolean isInterchange;
    private double distanceKm;

    public Station() {
        this("Unknown Station", "General", false, 0.0);
    }

    public Station(String name, String line, boolean isInterchange, double distanceKm) {
        this.name = (name != null) ? name.trim() : "Default";
        this.line = (line != null) ? line.trim().toUpperCase() : "YELLOW";
        this.isInterchange = isInterchange;
        this.distanceKm = distanceKm;
        totalStations++;
    }

    public static boolean isValidCode(String code) {
        return code != null && code.trim().length() >= 2 && code.trim().length() <= 5;
    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }

    public boolean isInterchange() {
        return isInterchange;
    }

    public void setInterchange(boolean interchange) {
        this.isInterchange = interchange;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    // Overriding toString() for clean printing
    @Override
    public String toString() {
        return name + " (" + line + " Line" + (isInterchange ? " - Interchange" : "") + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Station station = (Station) obj;
        return name.equalsIgnoreCase(station.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
