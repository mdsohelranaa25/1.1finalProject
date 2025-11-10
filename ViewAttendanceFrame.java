// ViewAttendanceFrame.java - Student views their attendance (marked by teacher)
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ViewAttendanceFrame extends JFrame {
    private Map<String, String> userInfo;
    private Color primaryColor = new Color(46, 204, 113);
    
    public ViewAttendanceFrame(Map<String, String> userInfo) {
        this.userInfo = userInfo;
        
        setTitle("My Attendance Records - " + userInfo.get("name"));
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Header
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Table
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(1100, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        JLabel titleLabel = new JLabel("📊 My Attendance Records");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JButton backButton = createStyledButton("← Back");
        backButton.addActionListener(e -> dispose());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JScrollPane createTablePanel() {
        // Get student's attendance records
        List<Map<String, String>> records = AttendanceManager.getStudentAttendance(
            userInfo.get("username"));
        
        // Create table model
        String[] columnNames = {"Date", "Course", "Status", "Marked By", "Icon"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Read-only table
            }
        };
        
        // Sort by date (newest first)
        records.sort((a, b) -> {
            long timeA = Long.parseLong(a.get("timestamp"));
            long timeB = Long.parseLong(b.get("timestamp"));
            return Long.compare(timeB, timeA);
        });
        
        // Populate table
        for (Map<String, String> record : records) {
            String date = record.get("date");
            String course = record.get("course");
            String status = record.get("status");
            String markedBy = record.get("markedBy");
            String icon = getStatusIcon(status);
            
            model.addRow(new Object[]{date, course, status, markedBy, icon});
        }
        
        // Create table
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(50);
        table.setSelectionBackground(new Color(52, 152, 219, 100));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(220, 220, 220));
        
        // Style header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        
        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(120); // Date
        table.getColumnModel().getColumn(1).setPreferredWidth(350); // Course
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Status
        table.getColumnModel().getColumn(3).setPreferredWidth(150); // Marked By
        table.getColumnModel().getColumn(4).setPreferredWidth(80);  // Icon
        
        // Custom cell renderer for status column
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                          boolean isSelected, boolean hasFocus,
                                                          int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, 
                                                                 isSelected, hasFocus, row, column);
                String status = (String) value;
                
                if (!isSelected) {
                    switch (status) {
                        case "Present":
                            c.setBackground(new Color(46, 204, 113, 50));
                            setForeground(new Color(39, 174, 96));
                            break;
                        case "Late":
                            c.setBackground(new Color(241, 196, 15, 50));
                            setForeground(new Color(243, 156, 18));
                            break;
                        case "Leave":
                            c.setBackground(new Color(52, 152, 219, 50));
                            setForeground(new Color(41, 128, 185));
                            break;
                        case "Absent":
                            c.setBackground(new Color(231, 76, 60, 50));
                            setForeground(new Color(192, 57, 43));
                            break;
                        default:
                            c.setBackground(Color.WHITE);
                    }
                }
                
                setFont(new Font("Segoe UI", Font.BOLD, 14));
                setHorizontalAlignment(CENTER);
                return c;
            }
        });
        
        // Custom cell renderer for icon column
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                          boolean isSelected, boolean hasFocus,
                                                          int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, 
                                                                 isSelected, hasFocus, row, column);
                setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
                setHorizontalAlignment(CENTER);
                if (!isSelected) {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });
        
        // Center align other columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Empty state
        if (records.isEmpty()) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
            emptyPanel.setBackground(Color.WHITE);
            emptyPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
            
            JLabel emptyIcon = new JLabel("📊");
            emptyIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
            emptyIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel emptyLabel = new JLabel("No Attendance Records Yet");
            emptyLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            emptyLabel.setForeground(new Color(127, 140, 141));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel emptySubLabel = new JLabel("Your teacher will mark attendance in class");
            emptySubLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            emptySubLabel.setForeground(new Color(149, 165, 166));
            emptySubLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            emptyPanel.add(emptyIcon);
            emptyPanel.add(Box.createVerticalStrut(20));
            emptyPanel.add(emptyLabel);
            emptyPanel.add(Box.createVerticalStrut(10));
            emptyPanel.add(emptySubLabel);
            
            return new JScrollPane(emptyPanel);
        }
        
        return scrollPane;
    }
    
    private String getStatusIcon(String status) {
        switch (status) {
            case "Present": return "✅";
            case "Late": return "⏰";
            case "Leave": return "📋";
            case "Absent": return "❌";
            default: return "❓";
        }
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(52, 73, 94));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(120, 40));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(44, 62, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(52, 73, 94));
            }
        });
        
        return button;
    }
}