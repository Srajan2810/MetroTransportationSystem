package metro.service;

import metro.exception.InvalidStationException;
import metro.exception.RouteNotFoundException;
import metro.model.Station;

import java.util.*;

// CONCEPT: Multiple Interface Implementation (implements Searchable, FareCalculable)
// CONCEPT: Java Collections Framework (HashMap, ArrayList, HashSet, LinkedList/Queue)
public class MetroPlanner implements Searchable, FareCalculable {

    // Adjacency List representing the Metro Network Graph (Station Name -> List of Connected Stations)
    private Map<String, List<String>> graph;
    private Map<String, Station> stationDetails;

    // CONCEPT: 2D Array (Fare Matrix between 4 sample zones)
    private static final int[][] ZONE_FARE_MATRIX = {
        { 10, 20, 30, 40 },
        { 20, 10, 20, 30 },
        { 30, 20, 10, 20 },
        { 40, 30, 20, 10 }
    };

    public MetroPlanner() {
        this.graph = new HashMap<>();
        this.stationDetails = new HashMap<>();
        initializeMetroNetwork();
    }

    // Direct in-code network initialization (Easy to explain in Viva, no external file dependencies!)
    private void initializeMetroNetwork() {
        // 1. Add Stations
        addStation(new Station("Samaypur Badli", "Yellow", false, 0.0));
        addStation(new Station("Azadpur", "Yellow", true, 4.2));
        addStation(new Station("Kashmere Gate", "Yellow", true, 9.5));
        addStation(new Station("Chandni Chowk", "Yellow", false, 11.0));
        addStation(new Station("New Delhi", "Yellow", true, 12.6));
        addStation(new Station("Rajiv Chowk", "Yellow", true, 13.8));
        addStation(new Station("Central Secretariat", "Yellow", true, 16.5));
        addStation(new Station("Hauz Khas", "Yellow", true, 23.5));
        addStation(new Station("Huda City Centre", "Yellow", false, 37.0));

        addStation(new Station("Dwarka Sector 21", "Blue", true, 0.0));
        addStation(new Station("Janakpuri West", "Blue", true, 12.0));
        addStation(new Station("Karol Bagh", "Blue", false, 21.8));
        addStation(new Station("Mandi House", "Blue", true, 15.7));
        addStation(new Station("Botanical Garden", "Blue", true, 26.5));
        addStation(new Station("Noida City Centre", "Blue", false, 34.0));

        addStation(new Station("Lajpat Nagar", "Violet", true, 21.6));
        addStation(new Station("Kalkaji Mandir", "Violet", true, 26.2));

        // 2. Connect Adjacent Stations (Bidirectional edges)
        // Yellow Line
        connect("Samaypur Badli", "Azadpur");
        connect("Azadpur", "Kashmere Gate");
        connect("Kashmere Gate", "Chandni Chowk");
        connect("Chandni Chowk", "New Delhi");
        connect("New Delhi", "Rajiv Chowk");
        connect("Rajiv Chowk", "Central Secretariat");
        connect("Central Secretariat", "Hauz Khas");
        connect("Hauz Khas", "Huda City Centre");

        // Blue Line
        connect("Dwarka Sector 21", "Janakpuri West");
        connect("Janakpuri West", "Karol Bagh");
        connect("Karol Bagh", "Rajiv Chowk");
        connect("Rajiv Chowk", "Mandi House");
        connect("Mandi House", "Botanical Garden");
        connect("Botanical Garden", "Noida City Centre");

        // Violet Line
        connect("Kashmere Gate", "Mandi House");
        connect("Mandi House", "Central Secretariat");
        connect("Central Secretariat", "Lajpat Nagar");
        connect("Lajpat Nagar", "Kalkaji Mandir");

        // Magenta Line (Connecting Janakpuri West, Hauz Khas, Kalkaji Mandir, Botanical Garden)
        connect("Janakpuri West", "Hauz Khas");
        connect("Hauz Khas", "Kalkaji Mandir");
        connect("Kalkaji Mandir", "Botanical Garden");
    }

    private void addStation(Station s) {
        stationDetails.put(s.getName().toLowerCase(), s);
        graph.putIfAbsent(s.getName(), new ArrayList<>());
    }

    private void connect(String s1, String s2) {
        graph.get(s1).add(s2);
        graph.get(s2).add(s1);
    }

    public Station getStation(String name) {
        if (name == null) return null;
        return stationDetails.get(name.trim().toLowerCase());
    }

    public List<String> getAllStationNames() {
        List<String> list = new ArrayList<>(graph.keySet());
        Collections.sort(list); // Sort alphabetically
        return list;
    }

    // =========================================================================
    // Breadth-First Search (BFS) for Shortest Route
    // =========================================================================
    @Override
    public List<String> findShortestRoute(String source, String destination)
            throws InvalidStationException, RouteNotFoundException {

        // Validate stations exist
        Station srcStation = getStation(source);
        Station dstStation = getStation(destination);

        if (srcStation == null) throw new InvalidStationException(source);
        if (dstStation == null) throw new InvalidStationException(destination);

        String start = srcStation.getName();
        String end = dstStation.getName();

        if (start.equalsIgnoreCase(end)) {
            return Collections.singletonList(start);
        }

        // BFS Data Structures
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);

        boolean found = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equalsIgnoreCase(end)) {
                found = true;
                break;
            }

            for (String neighbor : graph.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        if (!found) {
            throw new RouteNotFoundException(source, destination);
        }

        // Backtrack path from destination to source
        LinkedList<String> path = new LinkedList<>();
        String curr = end;
        while (curr != null) {
            path.addFirst(curr);
            curr = parent.get(curr);
        }

        return path;
    }

    // =========================================================================
    // Fare Calculation
    // =========================================================================
    @Override
    public double calculateFare(int stops, double distanceKm) {
        // Standard DMRC Slabs:
        // 0-2 stops: ₹10
        // 3-5 stops: ₹20
        // 6-9 stops: ₹30
        // 10-14 stops: ₹40
        // 15+ stops: ₹50
        if (stops <= 2) return 10.0;
        else if (stops <= 5) return 20.0;
        else if (stops <= 9) return 30.0;
        else if (stops <= 14) return 40.0;
        else return 50.0;
    }

    // 2D Array Matrix Lookup demonstration
    public int getZoneFare(int zone1, int zone2) {
        if (zone1 >= 0 && zone1 < 4 && zone2 >= 0 && zone2 < 4) {
            return ZONE_FARE_MATRIX[zone1][zone2];
        }
        return 20; // Default
    }
}
