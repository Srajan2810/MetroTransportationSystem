package metro.service;

import metro.model.Route;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class SimpleMetroGUI extends JFrame {

    private final MetroPlanner planner;
    private JComboBox<String> sourceBox;
    private JComboBox<String> destBox;
    private JLabel transferBanner;
    private JLabel metricsLabel;
    private JTextArea resultArea;

    public SimpleMetroGUI(MetroPlanner planner) {
        super("Delhi Metro Route Planner");
        this.planner = planner;

        setSize(760, 580);
        setMinimumSize(new Dimension(680, 480));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // =====================================================================
        // Route Planner & Directions Panel
        // =====================================================================
        JPanel plannerPanel = new JPanel(new BorderLayout(10, 10));

        // 1. Top Panel: Inputs & Station Selectors
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 8));
        topPanel.setBorder(new EmptyBorder(14, 16, 8, 16));
        topPanel.setBackground(new Color(245, 247, 250));

        List<String> stations = planner.getAllStationNames();
        sourceBox = new JComboBox<>(stations.toArray(new String[0]));
        destBox = new JComboBox<>(stations.toArray(new String[0]));
        sourceBox.setEditable(true);
        destBox.setEditable(true);

        sourceBox.setSelectedIndex(-1);
        destBox.setSelectedIndex(-1);
        sourceBox.getEditor().setItem("");
        destBox.getEditor().setItem("");

        JButton findBtn = new JButton("Find Route & Interchanges");
        findBtn.setFont(new Font("Arial", Font.BOLD, 13));
        findBtn.setBackground(new Color(24, 119, 242));
        findBtn.setForeground(Color.BLACK);
        findBtn.addActionListener(e -> findRoute());

        topPanel.add(new JLabel("Source Station (Start):"));
        topPanel.add(sourceBox);
        topPanel.add(new JLabel("Destination Station (End):"));
        topPanel.add(destBox);
        topPanel.add(new JLabel("")); // spacer
        topPanel.add(findBtn);

        // 2. Middle Status & Interchange Banner
        JPanel middleStatusPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        middleStatusPanel.setBorder(new EmptyBorder(4, 16, 6, 16));

        transferBanner = new JLabel(" Select source & destination, then click 'Find Route' to see interchanges.", JLabel.CENTER);
        transferBanner.setOpaque(true);
        transferBanner.setBackground(new Color(224, 231, 255));
        transferBanner.setForeground(new Color(30, 58, 138));
        transferBanner.setFont(new Font("Arial", Font.BOLD, 13));
        transferBanner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(165, 180, 252), 1),
                new EmptyBorder(8, 10, 8, 10)
        ));

        metricsLabel = new JLabel("Ready. System loaded 220+ stations across 10 official DMRC lines.", JLabel.CENTER);
        metricsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        metricsLabel.setForeground(new Color(75, 85, 99));

        middleStatusPanel.add(transferBanner);
        middleStatusPanel.add(metricsLabel);

        JPanel northContainer = new JPanel(new BorderLayout());
        northContainer.add(topPanel, BorderLayout.NORTH);
        northContainer.add(middleStatusPanel, BorderLayout.SOUTH);
        plannerPanel.add(northContainer, BorderLayout.NORTH);

        // 3. Center: Detailed Directions Output
        resultArea = new JTextArea("Select source and destination stations above, then click 'Find Route & Interchanges' to calculate the route.");
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(250, 250, 252));
        resultArea.setBorder(new EmptyBorder(8, 12, 8, 12));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 16, 14, 16));
        plannerPanel.add(scrollPane, BorderLayout.CENTER);

        add(plannerPanel, BorderLayout.CENTER);
    }

    private void findRoute() {
        Object srcObj = sourceBox.getSelectedItem();
        Object dstObj = destBox.getSelectedItem();

        if (srcObj == null || dstObj == null || srcObj.toString().trim().isEmpty() || dstObj.toString().trim().isEmpty()) {
            transferBanner.setBackground(new Color(254, 243, 199));
            transferBanner.setForeground(new Color(146, 64, 14));
            transferBanner.setText("Please select or type both source and destination stations.");
            resultArea.setText("Please select or type both source and destination stations.");
            return;
        }

        String src = srcObj.toString().trim();
        String dst = dstObj.toString().trim();

        try {
            Route route = planner.planRoute(src, dst);
            double fare = planner.calculateFare(route.getTotalStops(), route.getEstimatedDistanceKm());

            // 1. Update Interchange Banner
            if (route.getInterchangeCount() == 0) {
                transferBanner.setBackground(new Color(220, 252, 231)); // Light green
                transferBanner.setForeground(new Color(22, 101, 52));
                transferBanner.setText(String.format("DIRECT JOURNEY: Board %s Line. No train change required!", route.getInitialLine()));
            } else {
                transferBanner.setBackground(new Color(254, 243, 199)); // Amber / yellow alert
                transferBanner.setForeground(new Color(146, 64, 14));

                StringBuilder transferText = new StringBuilder();
                transferText.append(route.getInterchangeCount()).append(" Train Change(s): ");
                for (int i = 0; i < route.getInterchangeDetails().size(); i++) {
                    transferText.append(route.getInterchangeDetails().get(i));
                    if (i < route.getInterchangeDetails().size() - 1) transferText.append("  |  ");
                }
                transferBanner.setText(transferText.toString());
            }

            // 2. Update Metrics Summary
            metricsLabel.setText(String.format("Stops: %d | Transfers: %d | Est. Travel Time: %.0f Mins | Est. Distance: %.1f KM | Standard Fare: ₹%.2f",
                    route.getTotalStops(), route.getInterchangeCount(), route.getEstimatedTimeMinutes(),
                    route.getEstimatedDistanceKm(), fare));

            // 3. Update Detailed Directions Text
            resultArea.setText(route.getFormattedDirections());
            resultArea.append(String.format("Standard Token Fare: ₹%.2f\n", fare));
            resultArea.setCaretPosition(0);

        } catch (Exception ex) {
            transferBanner.setBackground(new Color(254, 226, 226));
            transferBanner.setForeground(new Color(153, 27, 27));
            transferBanner.setText("Error: " + ex.getMessage());
            resultArea.setText("Route calculation failed: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleMetroGUI gui = new SimpleMetroGUI(new MetroPlanner());
            gui.setVisible(true);
        });
    }
}
