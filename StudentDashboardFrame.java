// StudentDashboardFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class StudentDashboardFrame extends JFrame {
    private Map<String, String> studentInfo;
    private Color primaryColor = new Color(41, 128, 185);
    private Color backgroundColor = new Color(236, 240, 241);
    
    public StudentDashboardFrame(Map<String, String> studentInfo) {
        this.studentInfo = studentInfo;
        
        setTitle("Student Dashboard - " + studentInfo.get("name"));
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Menu Panel
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(1400, 140));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        
        // Left side - User Info
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(primaryColor);
        
        JLabel logoLabel = new JLabel("🎓");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        
        JLabel welcomeLabel = new JLabel("  STUDENT DASHBOARD");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.WHITE);
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setBackground(primaryColor);
        titlePanel.add(logoLabel);
        titlePanel.add(welcomeLabel);
        
        JLabel nameLabel = new JLabel("👤 " + studentInfo.get("name"));
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        nameLabel.setForeground(new Color(255, 255, 255, 240));
        
        JLabel infoLabel = new JLabel("🎯 Batch: " + studentInfo.get("field1") + 
                                      " | Department: " + studentInfo.get("field2"));
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        infoLabel.setForeground(new Color(255, 255, 255, 210));
        
        userInfoPanel.add(titlePanel);
        userInfoPanel.add(Box.createVerticalStrut(8));
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(Box.createVerticalStrut(3));
        userInfoPanel.add(infoLabel);
        
        // Right side - Logout Button
        JButton logoutButton = createStyledButton("🚪 Logout", new Color(231, 76, 60));
        logoutButton.setPreferredSize(new Dimension(140, 50));
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                System.exit(0);
            }
        });
        
        headerPanel.add(userInfoPanel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 3, 25, 25));
        menuPanel.setBackground(backgroundColor);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        // Create menu cards with click actions
        String[][] menuItems = {
            {"📅", "View Routine", "Check your class schedule"},
            {"✅", "Attendance", "View attendance record"},
            {"📚", "Course Management", "Manage enrolled courses"},
            {"📝", "Exam Schedule", "View exam dates & times"},
            {"👤", "Profile", "Update your information"},
            {"🔔", "Notifications", "Check important alerts"},
            {"📊", "Reports", "Generate various reports"},
            {"💬", "Support", "Get help & support"}
        };
        
        Color[] colors = {
            new Color(52, 152, 219),
            new Color(46, 204, 113),
            new Color(155, 89, 182),
            new Color(241, 196, 15),
            new Color(230, 126, 34),
            new Color(231, 76, 60),
            new Color(26, 188, 156),
            new Color(52, 73, 94)
        };
        
        for (int i = 0; i < menuItems.length; i++) {
            final int index = i;
            JPanel card = createMenuCard(menuItems[i][0], menuItems[i][1], 
                                        menuItems[i][2], colors[i], () -> handleMenuClick(index));
            menuPanel.add(card);
        }
        
        return menuPanel;
    }
    
    private JPanel createMenuCard(String icon, String title, String subtitle, Color color, Runnable onClick) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 55));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(color);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("<html><center>" + subtitle + "</center></html>");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(Box.createVerticalGlue());
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitleLabel);
        card.add(Box.createVerticalGlue());
        
        // Hover and click effects
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 249, 250));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color, 2),
                    BorderFactory.createEmptyBorder(25, 25, 25, 25)
                ));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                    BorderFactory.createEmptyBorder(25, 25, 25, 25)
                ));
            }
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }
        });
        
        return card;
    }
    
    private void handleMenuClick(int index) {
        switch (index) {
            case 0: // View Routine
                new ViewRoutineFrame(studentInfo);
                break;
            case 1: // Attendance
                new AttendanceFrame(studentInfo);
                break;
            case 2: // Course Management
                new CourseManagementFrame(studentInfo);
                break;
            case 3: // Exam Schedule
                new ExamScheduleFrame(studentInfo);
                break;
            case 4: // Profile
                new ProfileFrame(studentInfo);
                break;
            case 5: // Notifications
                new NotificationsFrame(studentInfo);
                break;
            case 6: // Reports
                new ReportsFrame(studentInfo);
                break;
            case 7: // Support
                new SupportFrame(studentInfo);
                break;
        }
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
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