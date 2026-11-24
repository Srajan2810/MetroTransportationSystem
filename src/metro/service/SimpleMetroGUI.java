package metro.service;

import metro.model.Route;
import metro.model.Station;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// CONCEPT: Simple Java Swing GUI (Clean & Easy to explain)
public class SimpleMetroGUI extends JFrame {

    private MetroPlanner planner;
    private JComboBox<String> sourceBox;
    private JComboBox<String> destBox;
    private JTextArea resultArea;

    public SimpleMetroGUI(MetroPlanner planner) {
        super("Delhi Metro Route Planner");
        this.planner = planner;

        setSize(600, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top Panel: Station Selection
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        List<String> stations = planner.getAllStationNames();
        sourceBox = new JComboBox<>(stations.toArray(new String[0]));
        destBox = new JComboBox<>(stations.toArray(new String[0]));
        if (stations.size() > 4) destBox.setSelectedIndex(4);

        JButton findBtn = new JButton("Find Shortest Route");
        findBtn.setBackground(new Color(30, 144, 255));
        findBtn.addActionListener(e -> findRoute());

        topPanel.add(new JLabel("Source Station:"));
        topPanel.add(sourceBox);
        topPanel.add(new JLabel("Destination Station:"));
        topPanel.add(destBox);
        topPanel.add(new JLabel(""));
        topPanel.add(findBtn);

        add(topPanel, BorderLayout.NORTH);

        // Center: Directions Display
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        resultArea.setText("Select Source and Destination, then click 'Find Shortest Route'.\n");
    }

    private void findRoute() {
        String src = (String) sourceBox.getSelectedItem();
        String dst = (String) destBox.getSelectedItem();

        try {
            List<String> path = planner.findShortestRoute(src, dst);
            Route route = new Route(src, dst, path);
            double fare = planner.calculateFare(route.getTotalStops(), route.getEstimatedDistanceKm());

            resultArea.setText(route.getFormattedDirections());
            resultArea.append(String.format("Standard Token Fare: ₹%.2f\n", fare));
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
}
