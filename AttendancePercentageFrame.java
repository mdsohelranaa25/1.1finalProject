
import javax.swing.*;

import java.awt.*;

import java.util.Map;


public class AttendancePercentageFrame extends JFrame {


    private Map<String, String> userInfo;

    

    public AttendancePercentageFrame(Map<String, String> userInfo) {

        this.userInfo = userInfo;

        

        setTitle("Attendance Percentage - " + userInfo.get("name"));

        setSize(900, 600);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Attendance Percentage Report");

        label.setFont(new Font("Segoe UI", Font.BOLD, 20));

        label.setHorizontalAlignment(JLabel.CENTER);

        mainPanel.add(label, BorderLayout.CENTER);

        

        add(mainPanel);

        setVisible(true);

    }

}
