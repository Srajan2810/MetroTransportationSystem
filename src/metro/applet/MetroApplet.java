package metro.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;

// CONCEPT: Java Applet & Applet Life Cycle Methods
// Demonstrates: init(), start(), paint(), stop(), destroy()
@SuppressWarnings("deprecation")
public class MetroApplet extends Applet {

    private String status = "";

    // 1. init(): Called when applet is loaded
    @Override
    public void init() {
        status = "Applet Initialized (init)";
        setBackground(new Color(240, 245, 250));
        System.out.println("[Applet Lifecycle]: init() invoked.");
    }

    // 2. start(): Called when applet starts execution
    @Override
    public void start() {
        status = "Applet Running (start)";
        System.out.println("[Applet Lifecycle]: start() invoked.");
    }

    // 3. paint(): Renders graphics and text on the applet window
    @Override
    public void paint(Graphics g) {
        System.out.println("[Applet Lifecycle]: paint() invoked.");

        // Title text
        g.setColor(new Color(20, 30, 60));
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Delhi Metro Route Map - Applet Demo", 30, 40);

        // Subtitle status
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.setColor(Color.DARK_GRAY);
        g.drawString("Status: " + status, 30, 65);

        // Draw Yellow Line track (Line from (60, 140) to (480, 140))
        g.setColor(new Color(245, 190, 11)); // Yellow Line
        g.fillRect(60, 137, 420, 6);

        // Draw Blue Line track (Line crossing at Rajiv Chowk)
        g.setColor(new Color(30, 120, 220)); // Blue Line
        g.fillRect(270, 80, 6, 120);

        // Station Nodes (Circles + Labels)
        drawStation(g, 80, 140, "Azadpur", false);
        drawStation(g, 170, 140, "Kashmere Gate", true);
        drawStation(g, 270, 140, "Rajiv Chowk (Interchange)", true);
        drawStation(g, 370, 140, "Central Sect.", true);
        drawStation(g, 460, 140, "Hauz Khas", true);

        // Blue Line Station
        drawStation(g, 270, 95, "Karol Bagh", false);
        drawStation(g, 270, 185, "Mandi House", false);

        // Legend
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.drawString("━ Yellow Line    ━ Blue Line    ● Station    ◎ Interchange", 30, 230);
    }

    private void drawStation(Graphics g, int x, int y, String name, boolean isInterchange) {
        if (isInterchange) {
            g.setColor(Color.RED);
            g.fillOval(x - 7, y - 7, 14, 14);
            g.setColor(Color.WHITE);
            g.fillOval(x - 4, y - 4, 8, 8);
        } else {
            g.setColor(Color.BLACK);
            g.fillOval(x - 5, y - 5, 10, 10);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString(name, x - 15, y - 10);
    }

    // 4. stop(): Called when applet is stopped/minimized
    @Override
    public void stop() {
        status = "Applet Stopped (stop)";
        System.out.println("[Applet Lifecycle]: stop() invoked.");
    }

    // 5. destroy(): Called when applet is destroyed/closed
    @Override
    public void destroy() {
        System.out.println("[Applet Lifecycle]: destroy() invoked.");
    }

    // Standalone Runner for modern Java 21 JDK
    public static void main(String[] args) {
        JFrame frame = new JFrame("Delhi Metro - Applet Lifecycle Demo");
        MetroApplet applet = new MetroApplet();

        frame.setSize(550, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(applet);

        // Execute Applet Lifecycle
        applet.init();
        applet.start();

        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                applet.stop();
                applet.destroy();
            }
        });
    }
}
