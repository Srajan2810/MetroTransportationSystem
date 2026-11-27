package metro.service;

import metro.exception.InvalidStationException;
import metro.exception.RouteNotFoundException;
import metro.model.Route;
import metro.model.Station;

import java.util.*;

// CONCEPT: Multiple Interface Implementation (implements Searchable, FareCalculable)
// CONCEPT: Java Collections Framework (HashMap, ArrayList, HashSet, LinkedList/Queue)
public class MetroPlanner implements Searchable, FareCalculable {

    // Adjacency List representing the Metro Network Graph (Station Name -> List of Connected Stations)
    private Map<String, List<String>> graph;
    private Map<String, Station> stationDetails;

    // Track connections and which Line connects any two adjacent stations
    private Map<String, String> connectionLines;

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
        this.connectionLines = new HashMap<>();
        initializeMetroNetwork();
    }

    // Official Delhi Metro Network Initialization
    private void initializeMetroNetwork() {
        // 1. Line 1 = Red Line (Rithala to Shaheed Sthal) - 29 Stations
        String[] redLine = {
            "Rithala", "Rohini West", "Rohini East", "Pitampura", "Kohat Enclave",
            "Netaji Subhash Place", "Keshav Puram", "Kanhaiya Nagar", "Inderlok",
            "Shastri Nagar", "Pratap Nagar", "Pulbangash", "Tis Hazari",
            "Kashmere Gate", "Shastri Park", "Seelampur", "Welcome", "Shahdara",
            "Mansarovar Park", "Jhilmil", "Dilshad Garden", "Shahid Nagar",
            "Raj Bagh", "Major Mohit Sharma Rajendra Nagar", "Shyam Park",
            "Mohan Nagar", "Arthala", "Hindon River", "Shaheed Sthal (New Bus Adda)"
        };
        loadLine("Red", redLine);

        // 2. Line 2 = Yellow Line (Samaypur Badli to Millennium City Centre Gurugram) - 37 Stations
        String[] yellowLine = {
            "Samaypur Badli", "Rohini Sector 18, 19", "Haiderpur Badli Mor",
            "Jahangirpuri", "Adarsh Nagar", "Azadpur", "Model Town",
            "Guru Tegh Bahadur Nagar", "Vishwavidyalaya", "Vidhan Sabha",
            "Civil Lines", "Kashmere Gate", "Chandni Chowk", "Chawri Bazar",
            "New Delhi", "Rajiv Chowk", "Patel Chowk", "Central Secretariat",
            "Udyog Bhawan", "Lok Kalyan Marg", "Jor Bagh", "Dilli Haat INA",
            "AIIMS", "Green Park", "Hauz Khas", "Malviya Nagar", "Saket",
            "Qutab Minar", "Chhattarpur", "Sultanpur", "Ghitorni", "Arjan Garh",
            "Guru Dronacharya", "Sikanderpur", "MG Road", "IFFCO Chowk",
            "Millennium City Centre Gurugram"
        };
        loadLine("Yellow", yellowLine);

        // 3. Line 3 = Blue Line (Dwarka Sector 21 to Noida Electronic City) - 50 Stations
        String[] blueMainLine = {
            "Dwarka Sector 21", "Dwarka Sector 8", "Dwarka Sector 9", "Dwarka Sector 10",
            "Dwarka Sector 11", "Dwarka Sector 12", "Dwarka Sector 13", "Dwarka Sector 14",
            "Dwarka", "Dwarka Mor", "Nawada", "Uttam Nagar West", "Uttam Nagar East",
            "Janakpuri West", "Janakpuri East", "Tilak Nagar", "Subhash Nagar",
            "Tagore Garden", "Rajouri Garden", "Ramesh Nagar", "Moti Nagar",
            "Kirti Nagar", "Shadipur", "Patel Nagar", "Rajendra Place",
            "Karol Bagh", "Jhandewalan", "RK Ashram Marg", "Rajiv Chowk",
            "Barakhamba Road", "Mandi House", "Supreme Court", "Indraprastha",
            "Yamuna Bank", "Akshardham", "Mayur Vihar-I", "Mayur Vihar Extension",
            "New Ashok Nagar", "Noida Sector 15", "Noida Sector 16", "Noida Sector 18",
            "Botanical Garden", "Golf Course", "Noida City Centre", "Noida Sector 34",
            "Noida Sector 52", "Noida Sector 61", "Noida Sector 59", "Noida Sector 62",
            "Noida Electronic City"
        };
        loadLine("Blue", blueMainLine);

        // 4. Line 4 = Blue Line Branch (Yamuna Bank to Vaishali) - 8 Stations
        String[] blueBranchLine = {
            "Yamuna Bank", "Laxmi Nagar", "Nirman Vihar", "Preet Vihar",
            "Karkarduma", "Anand Vihar ISBT", "Kaushambi", "Vaishali"
        };
        loadLine("Blue", blueBranchLine);

        // 5. Line 5 = Green Line (Inderlok / Kirti Nagar to Brig. Hoshiar Singh) - 24 Stations
        String[] greenLine = {
            "Inderlok", "Ashok Park Main", "Punjabi Bagh", "Punjabi Bagh West",
            "Shivaji Park", "Madipur", "Paschim Vihar East", "Paschim Vihar West",
            "Peeragarhi", "Udyog Nagar", "Maharaja Surajmal Stadium", "Nangloi",
            "Nangloi Railway Station", "Rajdhani Park", "Mundka", "Mundka Industrial Area",
            "Ghevra Metro Station", "Tikri Kalan", "Tikri Border",
            "Pandit Shree Ram Sharma", "Bahadurgarh City", "Brig. Hoshiar Singh"
        };
        loadLine("Green", greenLine);
        // Connect Green line branch to Kirti Nagar (Blue Line interchange)
        connect("Kirti Nagar", "Ashok Park Main", "Green");

        // 6. Line 6 = Violet Line (Kashmere Gate to Raja Nahar Singh) - 34 Stations
        String[] violetLine = {
            "Kashmere Gate", "Lal Quila", "Jama Masjid", "Delhi Gate", "ITO",
            "Mandi House", "Janpath", "Central Secretariat", "Khan Market",
            "Jawaharlal Nehru Stadium", "Jangpura", "Lajpat Nagar", "Moolchand",
            "Kailash Colony", "Nehru Place", "Kalkaji Mandir", "Govind Puri",
            "Harkesh Nagar Okhla", "Jasola Apollo", "Sarita Vihar", "Mohan Estate",
            "Tughlakabad Station", "Badarpur Border", "Sarai", "NHPC Chowk",
            "Mewala Maharajpur", "Sector 28", "Badkal Mor", "Old Faridabad",
            "Neelam Chowk Ajronda", "Bata Chowk", "Escorts Mujesar",
            "Sant Surdas (Sihi)", "Raja Nahar Singh (Ballabhgarh)"
        };
        loadLine("Violet", violetLine);

        // 7. Line 7 = Pink Line (Majlis Park to Shiv Vihar) - 38 Stations
        String[] pinkLine = {
            "Majlis Park", "Azadpur", "Shalimar Bagh", "Netaji Subhash Place",
            "Shakurpur", "Punjabi Bagh West", "ESI-Basaidarapur", "Rajouri Garden",
            "Maya Puri", "Naraina Vihar", "Delhi Cantt", "Durgabai Deshmukh South Campus",
            "Sir M. Vishweshwaraiah Moti Bagh", "Bhikaji Cama Place", "Sarojini Nagar",
            "Dilli Haat INA", "South Extension", "Lajpat Nagar", "Vinobapuri",
            "Ashram", "Sarai Kale Khan-Nizamuddin", "Mayur Vihar-I", "Mayur Vihar Pocket 1",
            "Trilokpuri-Sanjay Lake", "East Vinod Nagar-Mayur Vihar II",
            "Mandawali-West Vinod Nagar", "IP Extension", "Anand Vihar ISBT",
            "Karkarduma", "Karkarduma Court", "Krishna Nagar", "East Azad Nagar",
            "Welcome", "Jaffrabad", "Maujpur-Babarpur", "Gokulpuri",
            "Johri Enclave", "Shiv Vihar"
        };
        loadLine("Pink", pinkLine);

        // 8. Line 8 = Magenta Line (Janakpuri West to Botanical Garden) - 25 Stations
        String[] magentaLine = {
            "Janakpuri West", "Dabri Mor-Janakpuri South", "Dashrath Puri", "Palam",
            "Sadar Bazar Cantonment", "Terminal 1-IGI Airport", "Shankar Vihar",
            "Vasant Vihar", "Munirka", "RK Puram", "IIT Delhi", "Hauz Khas",
            "Panchsheel Park", "Chirag Delhi", "Greater Kailash", "Nehru Enclave",
            "Kalkaji Mandir", "Okhla NSIC", "Sukhdev Vihar", "Jamia Millia Islamia",
            "Okhla Vihar", "Jasola Vihar Shaheen Bagh", "Kalindi Kunj",
            "Okhla Bird Sanctuary", "Botanical Garden"
        };
        loadLine("Magenta", magentaLine);

        // 9. Orange Line (Airport Express: New Delhi to Yashobhoomi Dwarka Sector-25) - 7 Stations
        String[] airportExpress = {
            "New Delhi", "Shivaji Stadium", "Dhaula Kuan", "Delhi Aerocity",
            "Airport (T-3)", "Dwarka Sector 21", "Yashobhoomi Dwarka Sector-25"
        };
        loadLine("Airport Express", airportExpress);

        // 10. Line 9 = Grey Line (Dwarka to Dhansa Bus Stand) - 4 Stations
        String[] greyLine = {
            "Dwarka", "Nangli", "Najafgarh", "Dhansa Bus Stand"
        };
        loadLine("Grey", greyLine);
    }

    private void loadLine(String lineName, String[] stationNames) {
        for (int i = 0; i < stationNames.length; i++) {
            String name = stationNames[i];
            Station existing = getStation(name);

            if (existing == null) {
                // First time registering this station
                addStation(new Station(name, lineName, false, i * 1.4));
            } else {
                // Station already exists -> Mark as an interchange hub!
                existing.setInterchange(true);
                if (!existing.getLine().contains(lineName)) {
                    existing.setLine(existing.getLine() + " / " + lineName);
                }
            }

            // Connect adjacent stations on this line
            if (i > 0) {
                connect(stationNames[i - 1], name, lineName);
            }
        }
    }

    private void addStation(Station s) {
        stationDetails.put(s.getName().toLowerCase(), s);
        graph.putIfAbsent(s.getName(), new ArrayList<>());
    }

    private String makeKey(String s1, String s2) {
        return s1.trim().toLowerCase() + "<->" + s2.trim().toLowerCase();
    }

    private void connect(String s1, String s2, String lineName) {
        graph.putIfAbsent(s1, new ArrayList<>());
        graph.putIfAbsent(s2, new ArrayList<>());

        if (!graph.get(s1).contains(s2)) {
            graph.get(s1).add(s2);
        }
        if (!graph.get(s2).contains(s1)) {
            graph.get(s2).add(s1);
        }

        connectionLines.put(makeKey(s1, s2), lineName);
        connectionLines.put(makeKey(s2, s1), lineName);
    }

    public String getLineBetween(String s1, String s2) {
        String line = connectionLines.get(makeKey(s1, s2));
        return (line != null) ? line : "Metro";
    }

    public Station getStation(String name) {
        if (name == null) return null;
        String clean = name.trim().toLowerCase();

        // 1. Direct exact match
        Station direct = stationDetails.get(clean);
        if (direct != null) return direct;

        // 2. Partial match
        for (Map.Entry<String, Station> entry : stationDetails.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(clean) ||
                entry.getKey().contains(clean) ||
                clean.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public List<String> getAllStationNames() {
        List<String> list = new ArrayList<>(graph.keySet());
        Collections.sort(list);
        return list;
    }

    public int getTotalStationsCount() {
        return stationDetails.size();
    }

    // =========================================================================
    // Breadth-First Search (BFS) & Comprehensive Route Planning
    // =========================================================================
    public Route planRoute(String source, String destination)
            throws InvalidStationException, RouteNotFoundException {

        Station srcStation = getStation(source);
        Station dstStation = getStation(destination);

        if (srcStation == null) throw new InvalidStationException(source);
        if (dstStation == null) throw new InvalidStationException(destination);

        String start = srcStation.getName();
        String end = dstStation.getName();

        if (start.equalsIgnoreCase(end)) {
            return new Route(start, end, Collections.singletonList(start), Collections.emptyList());
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

        // Gather corresponding Metro Lines for each leg
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            lines.add(getLineBetween(path.get(i), path.get(i + 1)));
        }

        return new Route(start, end, path, lines);
    }

    @Override
    public List<String> findShortestRoute(String source, String destination)
            throws InvalidStationException, RouteNotFoundException {
        return planRoute(source, destination).getStationPath();
    }

    // =========================================================================
    // Fare Calculation
    // =========================================================================
    @Override
    public double calculateFare(int stops, double distanceKm) {
        if (stops <= 2) return 10.0;
        else if (stops <= 5) return 20.0;
        else if (stops <= 9) return 30.0;
        else if (stops <= 14) return 40.0;
        else if (stops <= 20) return 50.0;
        else return 60.0;
    }

    public int getZoneFare(int zone1, int zone2) {
        if (zone1 >= 0 && zone1 < 4 && zone2 >= 0 && zone2 < 4) {
            return ZONE_FARE_MATRIX[zone1][zone2];
        }
        return 20;
    }
}
