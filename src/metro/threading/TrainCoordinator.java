package metro.threading;

// CONCEPT: Inter-Thread Communication (Using wait() and notify())
public class TrainCoordinator {
    private boolean trainArrived = false;

    // Passenger thread calls this to wait on platform
    public synchronized void waitForTrain(String passengerName) {
        System.out.println("[" + passengerName + "] Waiting on platform for Yellow Line train...");
        while (!trainArrived) {
            try {
                // CONCEPT: wait() releases the lock and pauses the thread
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("[" + passengerName + "] Train arrived! Passenger boarded successfully.");
    }

    // Train arrives and signals waiting passengers
    public synchronized void signalTrainArrival() {
        System.out.println("[Station Master] Yellow Line train is entering platform...");
        trainArrived = true;
        // CONCEPT: notify() wakes up a waiting thread
        notify();
    }

    // Demo method to run the wait-notify flow
    public static void runDemo() {
        System.out.println("\n--- RUNNING INTER-THREAD COMMUNICATION (wait/notify) DEMO ---");
        TrainCoordinator coordinator = new TrainCoordinator();

        // Thread 1: Passenger waiting for train
        Thread passengerThread = new Thread(() -> {
            coordinator.waitForTrain("Commuter-A");
        }, "Passenger-Thread");

        // Thread 2: Train arrival signal
        Thread trainThread = new Thread(() -> {
            try {
                Thread.sleep(1500); // Train takes 1.5 seconds to arrive
                coordinator.signalTrainArrival();
            } catch (InterruptedException ignored) {}
        }, "Train-Arrival-Thread");

        passengerThread.start();
        trainThread.start();

        try {
            passengerThread.join();
            trainThread.join();
        } catch (InterruptedException ignored) {}

        System.out.println("--- INTER-THREAD COMMUNICATION DEMO COMPLETED ---\n");
    }
}
