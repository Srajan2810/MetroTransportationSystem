package metro.main;

import metro.applet.MetroApplet;
import metro.exception.InsufficientBalanceException;
import metro.exception.InvalidStationException;
import metro.exception.RouteNotFoundException;
import metro.model.*;
import metro.service.GCDemo;
import metro.service.MetroPlanner;
import metro.service.SimpleMetroGUI;
import metro.threading.AnnouncementThread;
import metro.threading.TicketCounterSyncDemo;
import metro.threading.TrainCoordinator;

import javax.swing.SwingUtilities;
import java.util.Arrays;
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
            } else if ("--applet".equalsIgnoreCase(args[0])) {
                MetroApplet.main(new String[0]);
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

        // Start background announcement thread
        AnnouncementThread announcementThread = new AnnouncementThread();
        announcementThread.start();

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter your choice (0-9): ");

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
                    handleViewStations();
                    break;
                case "4":
                    handle2DFareMatrix(scanner);
                    break;
                case "5":
                    TicketCounterSyncDemo.runDemo();
                    break;
                case "6":
                    TrainCoordinator.runDemo();
                    break;
                case "7":
                    GCDemo.runDemo();
                    break;
                case "8":
                    System.out.println("Launching Applet Life Cycle Window...");
                    MetroApplet.main(new String[0]);
                    break;
                case "9":
                    System.out.println("Opening Simple Swing GUI Window...");
                    launchGUI();
                    break;
                case "0":
                    running = false;
                    System.out.println("\nThank you for using Delhi Metro Route Planner. Safe travels!");
                    break;
                default:
                    System.out.println("Invalid option! Please enter a number between 0 and 9.");
                    break;
            }

            if (running) {
                System.out.println("\nPress ENTER to return to menu...");
                if (!scanner.hasNextLine()) break;
                scanner.nextLine();
            }
        }

        announcementThread.stopAnnouncements();
    }

    private void printMenu() {
        System.out.println("\n=======================================================");
        System.out.println("        🚇 DELHI METRO ROUTE PLANNER SYSTEM            ");
        System.out.println("=======================================================");
        System.out.println(" 1. 🔍 Plan Journey & Find Shortest Route (BFS)");
        System.out.println(" 2. 🎟️ Calculate Fare & Book Ticket (Token / Smart Card)");
        System.out.println(" 3. ℹ️ View Station Details & Facilities (1D Array)");
        System.out.println(" 4. 📊 View 2D Zone Fare Lookup Matrix (2D Array)");
        System.out.println(" 5. ⚡ Multithreading & Synchronization Demo");
        System.out.println(" 6. 🔄 Inter-Thread Communication (wait & notify) Demo");
        System.out.println(" 7. ♻️ Garbage Collection (GC & Reachability) Demo");
        System.out.println(" 8. 🖼️ Java Applet Life Cycle Graphic Demo");
        System.out.println(" 9. 🖥️ Launch Simple Java Swing GUI Window");
        System.out.println(" 0. 🚪 Exit");
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
            List<String> path = planner.findShortestRoute(src, dest);
            Route route = new Route(src, dest, path);
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
            List<String> path = planner.findShortestRoute(src, dest);
            Route route = new Route(src, dest, path);
            double baseFare = planner.calculateFare(route.getTotalStops(), route.getEstimatedDistanceKm());

            System.out.println("\nSelect Commuter Type:");
            System.out.println("  1. Regular Token Commuter (No discount)");
            System.out.println("  2. Smart Card Commuter (10% discount)");
            System.out.print("Choice (1 or 2): ");
            String type = scanner.nextLine().trim();

            Passenger passenger;
            double finalFare = baseFare;

            if ("2".equals(type)) {
                SmartCardPassenger cardUser = new SmartCardPassenger("Rahul Kumar", "9876543210", "SC104521", 100.0);
                double discount = baseFare * cardUser.getDiscountRate();
                finalFare = baseFare - discount;

                System.out.printf("Base Fare: ₹%.2f | 10%% Discount: -₹%.2f | Payable: ₹%.2f\n",
                        baseFare, discount, finalFare);

                // Deduct fare from card
                cardUser.deductFare(finalFare);
                passenger = cardUser;
                System.out.printf("Fare deducted! Remaining Smart Card Balance: ₹%.2f\n", cardUser.getBalance());
            } else {
                passenger = new Passenger("Amit Sharma", "9811223344", "Regular Commuter") {
                    @Override
                    public double getDiscountRate() {
                        return 0.0;
                    }
                };
            }

            // Generate Ticket (Association)
            Ticket ticket = new Ticket(src, dest, finalFare, passenger);
            ticket.printReceipt();

        } catch (InvalidStationException | RouteNotFoundException | InsufficientBalanceException e) {
            System.err.println("Booking Error: " + e.getMessage());
        }
    }

    private void handleViewStations() {
        System.out.println("\n--- METRO NETWORK STATIONS ---");
        List<String> names = planner.getAllStationNames();
        for (String name : names) {
            Station s = planner.getStation(name);
            System.out.printf("• %-22s | Line: %-7s | Interchange: %-5s | Facilities: %s\n",
                    s.getName(), s.getLine(), (s.isInterchange() ? "YES" : "NO"),
                    Arrays.toString(s.getFacilities()));
        }
    }

    private void handle2DFareMatrix(Scanner scanner) {
        System.out.println("\n--- 2D ZONE FARE LOOKUP MATRIX ---");
        System.out.println("Zones: 0 = North, 1 = Central, 2 = South, 3 = East/Noida");
        System.out.print("Enter Source Zone (0-3): ");
        int z1 = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter Destination Zone (0-3): ");
        int z2 = Integer.parseInt(scanner.nextLine().trim());

        int fare = planner.getZoneFare(z1, z2);
        System.out.printf("Zone Fare from Zone %d to Zone %d is: ₹%d\n", z1, z2, fare);
    }
}
