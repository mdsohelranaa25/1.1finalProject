import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class SupportFrame extends JFrame {
    private Map<String, String> userInfo;
    private Color primaryColor = new Color(52, 73, 94);
    
    public SupportFrame(Map<String, String> userInfo) {
        this.userInfo = userInfo;
        setupFrame("💬 SUPPORT");
        
        String[][] options = {
            {"❓", "FAQ", "Frequently asked questions"},
            {"📧", "Contact Support", "Email support team"},
            {"💬", "Live Chat", "Chat with support"},
            {"📞", "Call Support", "Phone support"},
            {"📖", "User Guide", "View documentation"},
            {"🐛", "Report Bug", "Report technical issues"}
        };
        
        Color[] colors = {
            new Color(52, 73, 94), new Color(52, 152, 219),
            new Color(46, 204, 113), new Color(241, 196, 15),
            new Color(155, 89, 182), new Color(231, 76, 60)
        };
        
        add(createContent(options, colors));
        setVisible(true);
    }
    
    private void setupFrame(String title) {
        setTitle(title.substring(2) + " - " + userInfo.get("name"));
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(1200, 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBackground(new Color(41, 128, 185));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(120, 45));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> dispose());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        add(mainPanel);
    }
    
    private JPanel createContent(String[][] options, Color[] colors) {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        for (int i = 0; i < options.length; i++) {
            gbc.gridx = i % 3;
            gbc.gridy = row;
            contentPanel.add(createCard(options[i][0], options[i][1], options[i][2], colors[i]), gbc);
            if ((i + 1) % 3 == 0) row++;
        }
        
        return contentPanel;
    }
    
    private JPanel createCard(String icon, String title, String subtitle, Color color) {
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
                JOptionPane.showMessageDialog(null, "🔜 Coming Soon!\n\n" + title + " feature is under development.", title, JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        return card;
    }
}