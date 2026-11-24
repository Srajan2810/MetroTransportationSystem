package metro.threading;

// CONCEPT: Multithreading (Implementing Runnable)
// CONCEPT: Thread Synchronization (Synchronized Method & Block to prevent race conditions)
public class TicketCounterSyncDemo implements Runnable {
    private int availableTickets = 5;

    // CONCEPT: Synchronized Method
    // Ensures only one passenger thread can book a ticket at a time
    public synchronized boolean bookTicket(String passengerName) {
        if (availableTickets > 0) {
            System.out.printf("[%s] Successfully booked ticket! Remaining tickets: %d\n",
                    passengerName, --availableTickets);
            return true;
        } else {
            System.out.printf("[%s] BOOKING FAILED: Tickets sold out!\n", passengerName);
            return false;
        }
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        bookTicket(threadName);
    }

    // Runner method demonstrating 6 threads competing for 5 tickets
    public static void runDemo() {
        System.out.println("\n--- RUNNING THREAD SYNCHRONIZATION DEMO ---");
        System.out.println("Starting 6 concurrent passenger threads competing for 5 tickets...");

        TicketCounterSyncDemo counter = new TicketCounterSyncDemo();

        Thread t1 = new Thread(counter, "Passenger-1");
        Thread t2 = new Thread(counter, "Passenger-2");
        Thread t3 = new Thread(counter, "Passenger-3");
        Thread t4 = new Thread(counter, "Passenger-4");
        Thread t5 = new Thread(counter, "Passenger-5");
        Thread t6 = new Thread(counter, "Passenger-6");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

        try {
            t1.join(); t2.join(); t3.join();
            t4.join(); t5.join(); t6.join();
        } catch (InterruptedException ignored) {}

        System.out.println("--- SYNCHRONIZATION DEMO COMPLETED ---\n");
    }
}
