package metro.threading;

// CONCEPT: Multithreading (Extending the Thread Class)
public class AnnouncementThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(250); // Demonstrates multithreaded execution
            System.out.println("\n[METRO ANNOUNCEMENT]: Welcome to Delhi Metro!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
