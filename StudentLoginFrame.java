// StudentLoginFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class StudentLoginFrame extends JFrame {
    private Color primaryColor = new Color(52, 152, 219);
    private Color backgroundColor = new Color(236, 240, 241);
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public StudentLoginFrame() {
        setTitle("Student Login");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(600, 100));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("🎓 STUDENT LOGIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Access your dashboard");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);
        
        return headerPanel;
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(40, 60, 40, 60),
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username
        JLabel usernameLabel = new JLabel("👤 Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(usernameLabel, gbc);
        
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(300, 40));
        gbc.gridy = 1;
        formPanel.add(usernameField, gbc);
        
        // Password
        JLabel passwordLabel = new JLabel("🔒 Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setForeground(new Color(44, 62, 80));
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(300, 40));
        gbc.gridy = 3;
        formPanel.add(passwordField, gbc);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton loginButton = createStyledButton("🔑 Login", primaryColor);
        loginButton.setPreferredSize(new Dimension(150, 45));
        loginButton.addActionListener(e -> handleLogin());
        
        JButton cancelButton = createStyledButton("❌ Cancel", new Color(231, 76, 60));
        cancelButton.setPreferredSize(new Dimension(150, 45));
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridy = 4;
        formPanel.add(buttonPanel, gbc);
        
        // Add Enter key listener
        passwordField.addActionListener(e -> handleLogin());
        
        return formPanel;
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password!",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Map<String, String> student = FileManager.findStudent(username, password);
        
        if (student != null) {
            JOptionPane.showMessageDialog(this,
                "✅ Login Successful!\n\n" +
                "Welcome back, " + student.get("name") + "!\n" +
                "Batch: " + student.get("field1") + "\n" +
                "Department: " + student.get("field2"),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            new StudentDashboardFrame(student);
        } else {
            JOptionPane.showMessageDialog(this,
                "❌ Invalid Username or Password!\n\n" +
                "Please check your credentials and try again.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
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
