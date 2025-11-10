// AttendanceFrame.java - Student can only VIEW attendance
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class AttendanceFrame extends JFrame {
    private Map<String, String> userInfo;
    private Color primaryColor = new Color(46, 204, 113);
    private Color backgroundColor = new Color(236, 240, 241);
    
    public AttendanceFrame(Map<String, String> userInfo) {
        this.userInfo = userInfo;
        
        setTitle("My Attendance - " + userInfo.get("name"));
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        
        // Header
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Content
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(1200, 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel titleLabel = new JLabel("✅ MY ATTENDANCE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        JButton backButton = createStyledButton("← Back", new Color(52, 73, 94));
        backButton.setPreferredSize(new Dimension(120, 45));
        backButton.addActionListener(e -> dispose());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Info message
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(230, 247, 255));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        JLabel infoIcon = new JLabel("ℹ️");
        infoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        infoIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel infoLabel = new JLabel("Attendance is marked by your teachers");
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        infoLabel.setForeground(new Color(52, 152, 219));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel infoSubLabel = new JLabel("You can view your attendance records below");
        infoSubLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        infoSubLabel.setForeground(new Color(127, 140, 141));
        infoSubLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(infoIcon);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(infoLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(infoSubLabel);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        contentPanel.add(infoPanel, gbc);
        
        // Attendance View Options (Student can only VIEW)
        String[][] options = {
            {"📊", "View All Records", "See complete attendance"},
            {"📈", "Course-wise Stats", "View by each course"},
            {"📅", "Monthly Report", "View monthly attendance"},
            {"⚠️", "Low Attendance Alert", "Check courses below 75%"},
            {"📄", "Percentage Report", "View attendance percentage"}
        };
        
        Color[] colors = {
            new Color(46, 204, 113),
            new Color(52, 152, 219),
            new Color(155, 89, 182),
            new Color(231, 76, 60),
            new Color(241, 196, 15)
        };
        
        gbc.gridwidth = 1;
        int row = 1;
        for (int i = 0; i < options.length; i++) {
            gbc.gridx = i % 3;
            gbc.gridy = row;
            
            final int index = i;
            JPanel optionCard = createOptionCard(options[i][0], options[i][1], 
                                                 options[i][2], colors[i],
                                                 () -> handleOptionClick(index));
            contentPanel.add(optionCard, gbc);
            
            if ((i + 1) % 3 == 0) row++;
        }
        
        return contentPanel;
    }
    
    private void handleOptionClick(int index) {
        switch (index) {
            case 0: // View All Records
                new ViewAttendanceFrame(userInfo);
                break;
            case 1: // Course-wise Stats
                new CourseWiseAttendanceFrame(userInfo);
                break;
            case 2: // Monthly Report
                new MonthlyAttendanceFrame(userInfo);
                break;
            case 3: // Low Attendance Alert
                new LowAttendanceAlertFrame(userInfo);
                break;
            case 4: // Percentage Report
                new AttendancePercentageFrame(userInfo);
                break;
        }
    }
    
    private JPanel createOptionCard(String icon, String title, String subtitle, 
                                    Color color, Runnable onClick) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(320, 200));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(color);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(Box.createVerticalGlue());
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(subtitleLabel);
        card.add(Box.createVerticalGlue());
        
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 249, 250));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color, 2),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }
        });
        
        return card;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
}