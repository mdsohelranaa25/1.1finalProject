import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CourseWiseAttendanceFrame extends JFrame {
    private Map<String, String> userInfo;
    
    public CourseWiseAttendanceFrame(Map<String, String> userInfo) {
        this.userInfo = userInfo;
        
        setTitle("Course-wise Attendance");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        JLabel label = new JLabel("📝 Course-wise Attendance - Coming Soon!", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        mainPanel.add(label);
        
        add(mainPanel);
        setVisible(true);
    }
}