package metro.threading;

// CONCEPT: Multithreading (Extending the Thread Class)
public class AnnouncementThread extends Thread {
    private volatile boolean running = true;

    private static final String[] ANNOUNCEMENTS = {
        "Train approaching Platform 1 towards Rajiv Chowk.",
        "Please mind the gap between the train and the platform.",
        "Interchange for Yellow and Blue lines is available at Rajiv Chowk.",
        "Passengers are advised to keep their belongings safe."
    };

    @Override
    public void run() {
        int index = 0;
        while (running && index < ANNOUNCEMENTS.length) {
            try {
                System.out.println("\n[METRO ANNOUNCEMENT 📢]: " + ANNOUNCEMENTS[index]);
                index++;
                Thread.sleep(4000); // Sleep for 4 seconds
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void stopAnnouncements() {
        running = false;
        interrupt();
    }
}
