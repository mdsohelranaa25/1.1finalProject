// TeacherDashboardFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
public class TeacherDashboardFrame extends JFrame {
    private Map<String, String> teacherInfo;
    private Color primaryColor = new Color(142, 68, 173);
    private Color backgroundColor = new Color(236, 240, 241);
    
    public TeacherDashboardFrame(Map<String, String> teacherInfo) {
        this.teacherInfo = teacherInfo;
        
        setTitle("Teacher Dashboard - " + teacherInfo.get("name"));
        setSize(1200, 800);
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
        headerPanel.setPreferredSize(new Dimension(1200, 120));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Left side - User Info
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(primaryColor);
        
        JLabel welcomeLabel = new JLabel("👨‍🏫 TEACHER DASHBOARD");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        
        JLabel nameLabel = new JLabel("👤 " + teacherInfo.get("name"));
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setForeground(new Color(255, 255, 255, 230));
        
        JLabel infoLabel = new JLabel("📚 Subject: " + teacherInfo.get("field1") + 
                                      " | Department: " + teacherInfo.get("field2"));
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoLabel.setForeground(new Color(255, 255, 255, 200));
        
        userInfoPanel.add(welcomeLabel);
        userInfoPanel.add(Box.createVerticalStrut(5));
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(infoLabel);
        
        // Right side - Logout Button
        JButton logoutButton = new JButton("🚪 Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setPreferredSize(new Dimension(120, 40));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new MainFrame();
            }
        });
        
        headerPanel.add(userInfoPanel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 3, 20, 20));
        menuPanel.setBackground(backgroundColor);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Create menu cards
        String[][] menuItems = {
            {"➕", "Create Routine", "Create new class schedule"},
            {"📅", "View Routine", "Check teaching schedule"},
            {"✅", "Attendance", "Mark student attendance"},
            {"📚", "Course Management", "Manage your courses"},
            {"📝", "Exam Schedule", "Create/manage exams"},
            {"👥", "Student Management", "Manage student info"},
            {"📊", "Reports", "Generate various reports"},
            {"🔔", "Notifications", "Send notifications"},
            {"👤", "Profile", "Update your information"}
        };
        
        Color[] colors = {
            new Color(46, 204, 113),
            new Color(52, 152, 219),
            new Color(155, 89, 182),
            new Color(241, 196, 15),
            new Color(230, 126, 34),
            new Color(231, 76, 60),
            new Color(26, 188, 156),
            new Color(52, 73, 94),
            new Color(211, 84, 0)
        };
        
        for (int i = 0; i < menuItems.length; i++) {
            JPanel card = createMenuCard(menuItems[i][0], menuItems[i][1], 
                                        menuItems[i][2], colors[i]);
            menuPanel.add(card);
        }
        
        return menuPanel;
    }
    
    private JPanel createMenuCard(String icon, String title, String subtitle, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createLineBorder(Color.WHITE, 19)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(color);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("<html><center>" + subtitle + "</center></html>");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(subtitleLabel);
        
        // Click effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(245, 245, 245));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
            }
            public void mouseClicked(MouseEvent e) {
                showComingSoon(title);
            }
        });
        
        return card;
    }
    
    private void showComingSoon(String feature) {
        JOptionPane.showMessageDialog(this,
            "🔜 Coming Soon!\n\n" +
            "The " + feature + " feature is under development.\n" +
            "Stay tuned for updates!",
            feature,
            JOptionPane.INFORMATION_MESSAGE);
    }
}