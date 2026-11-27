package metro.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
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
        String ticketType = (passenger != null) ? passenger.getPassengerType() : "Standard Token";

        System.out.println("\n+-------------------------------------------------------+");
        System.out.println("|              DELHI METRO RAIL CORPORATION             |");
        System.out.println("|                   JOURNEY TICKET                      |");
        System.out.println("+-------------------------------------------------------+");
        System.out.printf("| Ticket No   : %-39d |\n", ticketNumber);
        System.out.printf("| Date/Time   : %-39s |\n", issueTime);
        System.out.printf("| Ticket Type : %-39s |\n", ticketType);
        System.out.printf("| From        : %-39s |\n", source);
        System.out.printf("| To          : %-39s |\n", destination);
        System.out.printf("| Total Fare  : ₹%-38.2f |\n", fare);
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|             Please keep this ticket with you          |");
        System.out.println("+-------------------------------------------------------+\n");
    }
}
