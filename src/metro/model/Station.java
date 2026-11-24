package metro.model;

import java.util.Arrays;

// CONCEPT: Class, Encapsulation, Primitive Data Types
public class Station {

    // CONCEPT: Static Variable (shared across all Station objects)
    public static int totalStations = 0;

    // Instance Variables (Primitives & Strings)
    private String name;
    private String line;
    private boolean isInterchange;
    private double distanceKm;

    // CONCEPT: 1D Array (Fixed facilities at the station)
    private String[] facilities;

    // CONCEPT: Default Constructor
    public Station() {
        this("Unknown Station", "General", false, 0.0, new String[]{"Basic Amenities"});
    }

    // CONCEPT: Parameterized Constructor with 'this' Constructor Chaining
    public Station(String name, String line, boolean isInterchange, double distanceKm) {
        this(name, line, isInterchange, distanceKm, new String[]{"Elevator", "Ticket Counter"});
    }

    // Full Constructor
    public Station(String name, String line, boolean isInterchange, double distanceKm, String[] facilities) {
        // String functions: trim() and toUpperCase()
        this.name = (name != null) ? name.trim() : "Default";
        this.line = (line != null) ? line.trim().toUpperCase() : "YELLOW";
        this.isInterchange = isInterchange;
        this.distanceKm = distanceKm;
        this.facilities = facilities;

        totalStations++; // Increment static counter
    }

    // CONCEPT: Static Method
    public static boolean isValidCode(String code) {
        return code != null && code.trim().length() >= 2 && code.trim().length() <= 5;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }

    public boolean isInterchange() {
        return isInterchange;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public String[] getFacilities() {
        return facilities;
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
