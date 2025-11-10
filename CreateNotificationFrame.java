// CreateNotificationFrame.java
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CreateNotificationFrame extends JFrame {
    private Map<String, String> userInfo;
    private Color primaryColor = new Color(46, 204, 113);
    
    private JTextField titleField;
    private JTextArea messageArea;
    private JComboBox<String> typeCombo;
    
    public CreateNotificationFrame(Map<String, String> userInfo) {
        this.userInfo = userInfo;
        
        setTitle("Create Notification - " + userInfo.get("name"));
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Header
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Form
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(800, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        JLabel titleLabel = new JLabel("➕ Create Notification");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JButton backButton = createStyledButton("← Back", new Color(52, 73, 94));
        backButton.addActionListener(e -> dispose());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        addFormLabel(formPanel, "📝 Title:", gbc, 0);
        titleField = new JTextField(30);
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleField.setPreferredSize(new Dimension(400, 40));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(titleField, gbc);
        
        // Type
        addFormLabel(formPanel, "🏷️ Type:", gbc, 1);
        String[] types = {"General", "Exam", "Class", "Attendance", "Important", "Reminder"};
        typeCombo = new JComboBox<>(types);
        typeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        typeCombo.setPreferredSize(new Dimension(400, 40));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(typeCombo, gbc);
        
        // Message
        addFormLabel(formPanel, "💬 Message:", gbc, 2);
        messageArea = new JTextArea(5, 30);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setPreferredSize(new Dimension(400, 120));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(scrollPane, gbc);
        
        // Info Label
        JLabel infoLabel = new JLabel("💡 This notification will be saved immediately");
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        infoLabel.setForeground(new Color(127, 140, 141));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(infoLabel, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton createButton = createStyledButton("✅ Create Notification", primaryColor);
        createButton.setPreferredSize(new Dimension(200, 45));
        createButton.addActionListener(e -> handleCreate());
        
        JButton cancelButton = createStyledButton("❌ Cancel", new Color(231, 76, 60));
        cancelButton.setPreferredSize(new Dimension(150, 45));
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridy = 4;
        formPanel.add(buttonPanel, gbc);
        
        return formPanel;
    }
    
    private void addFormLabel(JPanel panel, String text, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setForeground(new Color(44, 62, 80));
        panel.add(label, gbc);
    }
    
    private void handleCreate() {
        String title = titleField.getText().trim();
        String message = messageArea.getText().trim();
        String type = (String) typeCombo.getSelectedItem();
        
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a title!",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a message!",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get current datetime
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String datetime = now.format(formatter);
        
        // Save notification
        if (NotificationManager.saveNotification(userInfo.get("username"), 
                                                 title, message, type, datetime)) {
            JOptionPane.showMessageDialog(this,
                "🎉 Notification Created Successfully!\n\n" +
                "Title: " + title + "\n" +
                "Type: " + type + "\n" +
                "Date: " + datetime,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to create notification!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
}