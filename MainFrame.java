import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    private Color primaryColor = new Color(41, 128, 185);
    private Color successColor = new Color(39, 174, 96);
    private Color warningColor = new Color(243, 156, 18);
    private Color dangerColor = new Color(231, 76, 60);
    private Color backgroundColor = new Color(236, 240, 241);
    
    public MainFrame() {
        setTitle("University Routine Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Center Panel
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(1000, 150));
        headerPanel.setLayout(new BorderLayout());
        
        // Logo and Title
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(primaryColor);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        
        JLabel logoLabel = new JLabel("🎓");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("UNIVERSITY ROUTINE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("MANAGEMENT SYSTEM");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel taglineLabel = new JLabel("📚 Organize Your Academic Life Efficiently");
        taglineLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        taglineLabel.setForeground(new Color(255, 255, 255, 180));
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(logoLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(taglineLabel);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(backgroundColor);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome! Please choose an option");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(welcomeLabel, gbc);
        
        // Login Button
        JButton loginButton = createStyledButton("🔑 LOGIN", primaryColor);
        loginButton.setPreferredSize(new Dimension(350, 80));
        loginButton.addActionListener(e -> openLoginOptions());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(loginButton, gbc);
        
        // Registration Button
        JButton regButton = createStyledButton("📝 REGISTRATION", successColor);
        regButton.setPreferredSize(new Dimension(350, 80));
        regButton.addActionListener(e -> openRegistrationOptions());
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(regButton, gbc);
        
        // Exit Button
        JButton exitButton = createStyledButton("🚪 EXIT", dangerColor);
        exitButton.setPreferredSize(new Dimension(720, 60));
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(exitButton, gbc);
        
        return centerPanel;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
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
    
    private void openLoginOptions() {
        LoginOptionsDialog dialog = new LoginOptionsDialog(this);
        dialog.setVisible(true);
    }
    
    private void openRegistrationOptions() {
        RegistrationOptionsDialog dialog = new RegistrationOptionsDialog(this);
        dialog.setVisible(true);
    }
}