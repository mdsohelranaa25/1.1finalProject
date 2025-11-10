import javax.swing.*;
import java.awt.*;
import java.util.*;

public class AttendancePercentageFrame extends JFrame {
    private Map<String, String> userInfo;
    private Color primaryColor = new Color(155, 89, 182);
    
    public AttendancePercentageFrame(Map<String, String> userInfo) {
        this.userInfo = userInfo;
        
        setTitle("Attendance Percentage - " + userInfo.get("name"));
        setSize(900, 700);
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
        
        JLabel titleLabel = new JLabel("📈 Attendance Percentage");
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
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        Map<String, AttendanceStats> statsMap = AttendanceManager.getAllCourseStats(userInfo.get("username"));
        
        if (statsMap.isEmpty()) {
            // Empty state
            JPanel emptyPanel = new JPanel();
            emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
            emptyPanel.setBackground(Color.WHITE);
            emptyPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
            
            JLabel emptyIcon = new JLabel("📈");
            emptyIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
            emptyIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel emptyLabel = new JLabel("No Attendance Data");
            emptyLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            emptyLabel.setForeground(new Color(127, 140, 141));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            emptyPanel.add(emptyIcon);
            emptyPanel.add(Box.createVerticalStrut(20));
            emptyPanel.add(emptyLabel);
            
            contentPanel.add(emptyPanel);
        } else {
            for (String course : statsMap.keySet()) {
                AttendanceStats stats = statsMap.get(course);
                contentPanel.add(createStatsCard(stats));
                contentPanel.add(Box.createVerticalStrut(15));
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        return scrollPane;
    }
    
    private JPanel createStatsCard(AttendanceStats stats) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(getColorForPercentage(stats.percentage), 3),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        
        // Left side - Course info
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        
        JLabel courseLabel = new JLabel(stats.course);
        courseLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        courseLabel.setForeground(new Color(44, 62, 80));
        
        JLabel detailsLabel = new JLabel(String.format(
            "Present: %d | Absent: %d | Late: %d | Leave: %d",
            stats.present, stats.absent, stats.late, stats.leave
        ));
        detailsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        detailsLabel.setForeground(new Color(127, 140, 141));
        
        JLabel totalLabel = new JLabel("Total Classes: " + stats.total);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabel.setForeground(new Color(52, 73, 94));
        
        leftPanel.add(courseLabel);
        leftPanel.add(Box.createVerticalStrut(8));
        leftPanel.add(detailsLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(totalLabel);
        
        // Right side - Percentage
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        
        JLabel percentageLabel = new JLabel(String.format("%.1f%%", stats.percentage));
        percentageLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        percentageLabel.setForeground(getColorForPercentage(stats.percentage));
        percentageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel statusLabel = new JLabel(getStatusText(stats.percentage));
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(getColorForPercentage(stats.percentage));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(percentageLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(statusLabel);
        rightPanel.add(Box.createVerticalGlue());
        
        card.add(leftPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);
        
        return card;
    }
    
    private Color getColorForPercentage(double percentage) {
        if (percentage >= 75) {
            return new Color(39, 174, 96); // Green
        } else if (percentage >= 60) {
            return new Color(243, 156, 18); // Orange
        } else {
            return new Color(231, 76, 60); // Red
        }
    }
    
    private String getStatusText(double percentage) {
        if (percentage >= 75) {
            return "✅ Eligible for Exam";
        } else if (percentage >= 60) {
            return "⚠️ Warning - Below 75%";
        } else {
            return "❌ Critical - Attend More!";
        }
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