package metro.main;

import metro.exception.InvalidStationException;
import metro.exception.RouteNotFoundException;
import metro.model.*;
import metro.service.GCDemo;
import metro.service.MetroPlanner;
import metro.service.SimpleMetroGUI;
import metro.threading.AnnouncementThread;

import javax.swing.SwingUtilities;
import java.util.List;
import java.util.Scanner;

// CONCEPT: Main Application Entry Point & Control Flow
public class MetroApp {

    private final MetroPlanner planner;

    public MetroApp() {
        this.planner = new MetroPlanner();
    }

    public static void main(String[] args) {
        MetroApp app = new MetroApp();

        // Optional quick command-line flags
        if (args.length > 0) {
            if ("--gui".equalsIgnoreCase(args[0])) {
                app.launchGUI();
                return;
            }
        }

        app.runConsole();
    }

    public void launchGUI() {
        SwingUtilities.invokeLater(() -> {
            SimpleMetroGUI gui = new SimpleMetroGUI(planner);
            gui.setVisible(true);
        });
    }

    public void runConsole() {
        Scanner scanner = new Scanner(System.in);

        // Run Announcement Thread before displaying the menu
        AnnouncementThread announcementThread = new AnnouncementThread();
        announcementThread.start();
        try {
            announcementThread.join(); // Ensure announcement thread completes before menu appears
        } catch (InterruptedException ignored) {}

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter your choice (0-5): ");

            if (!scanner.hasNextLine()) break;
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleRoutePlanning(scanner);
                    break;
                case "2":
                    handleTicketBooking(scanner);
                    break;
                case "3":
                    handleViewStations(scanner);
                    break;
                case "4":
                    GCDemo.runDemo();
                    break;
                case "5":
                    System.out.println("Opening GUI Window...");
                    launchGUI();
                    break;
                case "0":
                    running = false;
                    System.out.println("\nThank you for using Delhi Metro Route Planner. Safe travels!");
                    break;
                default:
                    System.out.println("Invalid option! Please enter a number between 0 and 5.");
                    break;
            }

            if (running) {
                System.out.println("\nPress ENTER to return to menu...");
                if (!scanner.hasNextLine()) break;
                scanner.nextLine();
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=======================================================");
        System.out.println("            DELHI METRO ROUTE PLANNER SYSTEM            ");
        System.out.println("=======================================================");
        System.out.println(" 1. Plan Journey & Find Shortest Route");
        System.out.println(" 2. Calculate Fare & Book Ticket");
        System.out.println(" 3. View Station Details");
        System.out.println(" 4. Garbage Collection");
        System.out.println(" 5. Launch GUI");
        System.out.println(" 0. Exit");
        System.out.println("=======================================================");
    }

    private void handleRoutePlanning(Scanner scanner) {
        System.out.println("\n--- PLAN JOURNEY ---");
        System.out.print("Enter Source Station (e.g., Azadpur): ");
        String src = scanner.nextLine().trim();

        System.out.print("Enter Destination Station (e.g., Botanical Garden): ");
        String dest = scanner.nextLine().trim();

        // CONCEPT: Exception Handling (try-catch)
        try {
            Route route = planner.planRoute(src, dest);
            System.out.println(route.getFormattedDirections());

            double baseFare = planner.calculateFare(route.getTotalStops(), route.getEstimatedDistanceKm());
            System.out.printf("Standard Token Fare: ₹%.2f\n", baseFare);

        } catch (InvalidStationException | RouteNotFoundException e) {
            System.err.println("Search Error: " + e.getMessage());
        }
    }

    private void handleTicketBooking(Scanner scanner) {
        System.out.println("\n--- TICKET BOOKING & FARE CALCULATION ---");
        System.out.print("Enter Source Station: ");
        String src = scanner.nextLine().trim();

        System.out.print("Enter Destination Station: ");
        String dest = scanner.nextLine().trim();

        try {
            Route route = planner.planRoute(src, dest);
            double baseFare = planner.calculateFare(route.getTotalStops(), route.getEstimatedDistanceKm());

            Passenger passenger = new Passenger("Commuter", "N/A", "Regular Token") {
                @Override
                public double getDiscountRate() {
                    return 0.0;
                }
            };
            System.out.printf("Ticket Price: ₹%.2f\n", baseFare);

            // Generate Ticket (Association)
            Ticket ticket = new Ticket(src, dest, baseFare, passenger);
            ticket.printReceipt();

        } catch (InvalidStationException | RouteNotFoundException e) {
            System.err.println("Booking Error: " + e.getMessage());
        }
    }

    private void handleViewStations(Scanner scanner) {
        System.out.println("\n--- METRO NETWORK STATIONS (" + planner.getTotalStationsCount() + " Stations across 10 Lines) ---");
        System.out.print("Enter station name to search (or press ENTER to list all): ");
        String q = scanner.nextLine().trim();

        if (!q.isEmpty()) {
            Station s = planner.getStation(q);
            if (s != null) {
                System.out.println("\nStation Found:");
                System.out.printf("- Name          : %s\n", s.getName());
                System.out.printf("- Line(s)       : %s Line\n", s.getLine());
                System.out.printf("- Interchange   : %s\n", (s.isInterchange() ? "YES (Transfer Hub)" : "NO"));
            } else {
                System.out.println("No station matching '" + q + "' was found in the network.");
            }
            return;
        }

        List<String> names = planner.getAllStationNames();
        for (String name : names) {
            Station s = planner.getStation(name);
            System.out.printf("- %-34s | Line: %-30s | Interchange: %-5s\n",
                    s.getName(), s.getLine(), (s.isInterchange() ? "YES" : "NO"));
        }
    }
}
