// TeacherRegistrationFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TeacherRegistrationFrame extends JFrame {
    private Color primaryColor = new Color(26, 188, 156);
    private Color backgroundColor = new Color(236, 240, 241);
    
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField subjectField;
    private JComboBox<String> departmentCombo;
    
    public TeacherRegistrationFrame() {
        setTitle("Teacher Registration");
        setSize(700, 750);
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
        headerPanel.setPreferredSize(new Dimension(700, 100));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("👨‍🏫 TEACHER REGISTRATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Join our faculty team");
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
            BorderFactory.createEmptyBorder(30, 50, 30, 50),
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Name
        addFormField(formPanel, "👤 Full Name:", nameField = new JTextField(20), gbc, 0);
        
        // Username
        addFormField(formPanel, "🔑 Username:", usernameField = new JTextField(20), gbc, 1);
        
        // Password
        addFormField(formPanel, "🔒 Password:", passwordField = new JPasswordField(20), gbc, 2);
        
        // Subject
        addFormField(formPanel, "📚 Subject/Course:", subjectField = new JTextField(20), gbc, 3);
        
        // Department
        String[] departments = {"CSE", "EEE", "BBA", "Civil", "Mechanical", "Textile", "IPE"};
        departmentCombo = new JComboBox<>(departments);
        departmentCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        addFormField(formPanel, "🏛️ Department:", departmentCombo, gbc, 4);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton registerButton = createStyledButton("✅ Register", primaryColor);
        registerButton.setPreferredSize(new Dimension(150, 45));
        registerButton.addActionListener(e -> handleRegistration());
        
        JButton cancelButton = createStyledButton("❌ Cancel", new Color(231, 76, 60));
        cancelButton.setPreferredSize(new Dimension(150, 45));
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        
        return formPanel;
    }
    
    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(44, 62, 80));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        if (field instanceof JTextField || field instanceof JPasswordField) {
            field.setPreferredSize(new Dimension(200, 35));
        }
        panel.add(field, gbc);
    }
    
    private void handleRegistration() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String subject = subjectField.getText().trim();
        String department = (String) departmentCombo.getSelectedItem();
        
        // Validation
        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || subject.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill all fields!",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                "Password must be at least 6 characters!",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if username exists
        if (FileManager.usernameExists("teachers.txt", username)) {
            JOptionPane.showMessageDialog(this,
                "Username already exists! Please choose another.",
                "Username Taken",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Save to file
        if (FileManager.saveTeacher(username, password, name, subject, department)) {
            JOptionPane.showMessageDialog(this,
                "🎉 Registration Successful!\n\n" +
                "Name: " + name + "\n" +
                "Username: " + username + "\n" +
                "Subject: " + subject + "\n" +
                "Department: " + department + "\n\n" +
                "You can now login with your credentials.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Registration failed! Please try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
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