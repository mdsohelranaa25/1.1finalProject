import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class LowAttendanceAlertFrame extends JFrame {
    private Map<String, String> userInfo;
    private Color primaryColor = new Color(231, 76, 60);
    
    public LowAttendanceAlertFrame(Map<String, String> userInfo) {
        this.userInfo = userInfo;
        
        setTitle("Low Attendance Alert - " + userInfo.get("name"));
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(900, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        JLabel titleLabel = new JLabel("⚠️ Low Attendance Alert");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JButton backButton = createStyledButton("← Back");
        backButton.addActionListener(e -> dispose());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JScrollPane createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        // Get all course stats
        Map<String, AttendanceStats> statsMap = AttendanceManager.getAllCourseStats(userInfo.get("username"));
        
        // Filter courses below 75%
        List<AttendanceStats> lowCourses = new ArrayList<>();
        for (AttendanceStats stats : statsMap.values()) {
            if (stats.percentage < 75.0) {
                lowCourses.add(stats);
            }
        }
        
        // Sort by percentage (lowest first)
        lowCourses.sort((a, b) -> Double.compare(a.percentage, b.percentage));
        
        if (lowCourses.isEmpty()) {
            // All courses above 75% - Show success message
            JPanel successPanel = new JPanel();
            successPanel.setLayout(new BoxLayout(successPanel, BoxLayout.Y_AXIS));
            successPanel.setBackground(Color.WHITE);
            successPanel.setBorder(BorderFactory.createEmptyBorder(80, 50, 80, 50));
            
            JLabel successIcon = new JLabel("✅");
            successIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
            successIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel successLabel = new JLabel("Great Job!");
            successLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
            successLabel.setForeground(new Color(39, 174, 96));
            successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel messageLabel = new JLabel("All your courses have 75%+ attendance");
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            messageLabel.setForeground(new Color(127, 140, 141));
            messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel infoLabel = new JLabel("You are eligible for all exams!");
            infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            infoLabel.setForeground(new Color(39, 174, 96));
            infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            successPanel.add(successIcon);
            successPanel.add(Box.createVerticalStrut(20));
            successPanel.add(successLabel);
            successPanel.add(Box.createVerticalStrut(10));
            successPanel.add(messageLabel);
            successPanel.add(Box.createVerticalStrut(10));
            successPanel.add(infoLabel);
            
            contentPanel.add(successPanel);
        } else {
            // Show warning message
            JPanel warningPanel = createWarningPanel(lowCourses.size());
            contentPanel.add(warningPanel);
            contentPanel.add(Box.createVerticalStrut(20));
            
            // Show each low attendance course
            for (AttendanceStats stats : lowCourses) {
                contentPanel.add(createAlertCard(stats));
                contentPanel.add(Box.createVerticalStrut(15));
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        return scrollPane;
    }
    
    private JPanel createWarningPanel(int count) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 240, 240));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(231, 76, 60), 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        JLabel iconLabel = new JLabel("⚠️");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Warning: Low Attendance Detected!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(192, 57, 43));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel countLabel = new JLabel(count + " course(s) below 75% attendance");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        countLabel.setForeground(new Color(127, 140, 141));
        countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(countLabel);
        
        return panel;
    }
    
    private JPanel createAlertCard(AttendanceStats stats) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        
        // Left side
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        
        JLabel courseLabel = new JLabel(stats.course);
        courseLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        courseLabel.setForeground(new Color(44, 62, 80));
        
        JLabel statsLabel = new JLabel(String.format(
            "Present: %d | Absent: %d | Total: %d",
            stats.present + stats.late, stats.absent, stats.total
        ));
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statsLabel.setForeground(new Color(127, 140, 141));
        
        // Calculate classes needed
        int classesNeeded = calculateClassesNeeded(stats);
        JLabel neededLabel = new JLabel(String.format(
            "⚠️ Attend next %d classes to reach 75%%",
            classesNeeded
        ));
        neededLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        neededLabel.setForeground(new Color(231, 76, 60));
        
        leftPanel.add(courseLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(statsLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(neededLabel);
        
        // Right side - Percentage
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        
        JLabel percentLabel = new JLabel(String.format("%.1f%%", stats.percentage));
        percentLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        percentLabel.setForeground(new Color(231, 76, 60));
        percentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel statusLabel = new JLabel("Below 75%");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        statusLabel.setForeground(new Color(192, 57, 43));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(percentLabel);
        rightPanel.add(Box.createVerticalStrut(3));
        rightPanel.add(statusLabel);
        rightPanel.add(Box.createVerticalGlue());
        
        card.add(leftPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);
        
        return card;
    }
    
    private int calculateClassesNeeded(AttendanceStats stats) {
        double currentPercentage = stats.percentage;
        int present = stats.present + stats.late;
        int total = stats.total;
        
        // Formula: (present + x) / (total + x) >= 0.75
        // Solving: x >= (0.75 * total - present) / 0.25
        
        int classesNeeded = (int) Math.ceil((0.75 * total - present) / 0.25);
        return Math.max(0, classesNeeded);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(52, 73, 94));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(120, 40));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(44, 62, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(52, 73, 94));
            }
        });
        
        return button;
    }
}