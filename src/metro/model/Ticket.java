package metro.model;

import java.text.SimpleDateFormat;
import java.util.Date;

// CONCEPT: Association (Ticket associates with Passenger, Source, and Destination)
public class Ticket {
    // Static Variable
    public static int totalTickets = 0;

    private int ticketNumber;
    private String source;
    private String destination;
    private double fare;
    private Passenger passenger;
    private String issueTime;

    public Ticket(String source, String destination, double fare, Passenger passenger) {
        this.source = source;
        this.destination = destination;
        this.fare = fare;
        this.passenger = passenger;
        this.issueTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(new Date());

        totalTickets++;
        this.ticketNumber = 1000 + totalTickets;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public double getFare() {
        return fare;
    }

    // Print Receipt
    public void printReceipt() {
        System.out.println("\n+-------------------------------------------------------+");
        System.out.println("|              DELHI METRO RAIL CORPORATION             |");
        System.out.println("|                   JOURNEY TICKET                      |");
        System.out.println("+-------------------------------------------------------+");
        System.out.printf("| Ticket No  : %-40d |\n", ticketNumber);
        System.out.printf("| Date/Time  : %-40s |\n", issueTime);
        System.out.printf("| Passenger  : %-40s |\n", (passenger != null ? passenger.getName() : "Standard Commuter"));
        System.out.printf("| From       : %-40s |\n", source);
        System.out.printf("| To         : %-40s |\n", destination);
        System.out.printf("| Total Fare : ₹%-39.2f |\n", fare);
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|             Please keep this ticket with you          |");
        System.out.println("+-------------------------------------------------------+\n");
    }
}
