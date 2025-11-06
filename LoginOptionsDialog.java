import javax.swing.*;
import java.awt.*;

class LoginOptionsDialog extends JDialog {
    private Color primaryColor = new Color(41, 128, 185);
    private Color studentColor = new Color(52, 152, 219);
    private Color teacherColor = new Color(142, 68, 173);
    
    public LoginOptionsDialog(JFrame parent) {
        super(parent, "Login Options", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(500, 80));
        JLabel headerLabel = new JLabel("🔐 LOGIN OPTIONS");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        
        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Student Login Button
        JButton studentButton = createStyledButton("🎓 Login as Student", studentColor);
        studentButton.setPreferredSize(new Dimension(300, 70));
        studentButton.addActionListener(e -> {
            dispose();
            new StudentLoginFrame();
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(studentButton, gbc);
        
        // Teacher Login Button
        JButton teacherButton = createStyledButton("👨‍🏫 Login as Teacher", teacherColor);
        teacherButton.setPreferredSize(new Dimension(300, 70));
        teacherButton.addActionListener(e -> {
            dispose();
            new TeacherLoginFrame();
        });
        gbc.gridy = 1;
        centerPanel.add(teacherButton, gbc);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
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

