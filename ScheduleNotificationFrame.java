import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ScheduleNotificationFrame extends JFrame {
    private Map<String, String> userInfo;
    
    public ScheduleNotificationFrame(Map<String, String> userInfo) {
        this.userInfo = userInfo;
        
        setTitle("Schedule Notification");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("🔜 Schedule Notification Feature Coming Soon!", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        mainPanel.add(label);
        
        add(mainPanel);
        setVisible(true);
    }
}