// TeacherViewRoutineFrame.java - Teacher views classes they created
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TeacherViewRoutineFrame extends JFrame {
    private Map<String, String> teacherInfo;
    private Color primaryColor = new Color(52, 152, 219);
    
    public TeacherViewRoutineFrame(Map<String, String> teacherInfo) {
        this.teacherInfo = teacherInfo;
        
        setTitle("My Routine - " + teacherInfo.get("name"));
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Header
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Routine Table
        mainPanel.add(createRoutineTable(), BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(1200, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        JLabel titleLabel = new JLabel("📅 My Teaching Schedule");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JButton backButton = createStyledButton("← Back");
        backButton.addActionListener(e -> dispose());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JScrollPane createRoutineTable() {
        // Get teacher's routines
        List<Map<String, String>> routines = RoutineManager.getRoutinesByTeacher(
            teacherInfo.get("username"));
        
        // Create table
        String[] columnNames = {"Day", "Time", "Course", "Room", "Department", "Semester", "Section"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Sort by day and time
        routines.sort((a, b) -> {
            int dayCompare = Arrays.asList(RoutineManager.DAYS).indexOf(a.get("day")) -
                           Arrays.asList(RoutineManager.DAYS).indexOf(b.get("day"));
            if (dayCompare != 0) return dayCompare;
            
            return Arrays.asList(RoutineManager.TIME_SLOTS).indexOf(a.get("timeSlot")) -
                   Arrays.asList(RoutineManager.TIME_SLOTS).indexOf(b.get("timeSlot"));
        });
        
        // Populate table
        for (Map<String, String> routine : routines) {
            model.addRow(new Object[]{
                routine.get("day"),
                routine.get("timeSlot"),
                routine.get("course"),
                routine.get("room"),
                routine.get("department"),
                routine.get("semester"),
                routine.get("section")
            });
        }
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(50);
        table.setSelectionBackground(new Color(52, 152, 219, 100));
        table.setGridColor(new Color(220, 220, 220));
        
        // Style header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        
        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(120); // Day
        table.getColumnModel().getColumn(1).setPreferredWidth(180); // Time
        table.getColumnModel().getColumn(2).setPreferredWidth(300); // Course
        table.getColumnModel().getColumn(3).setPreferredWidth(120); // Room
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Department
        table.getColumnModel().getColumn(5).setPreferredWidth(100); // Semester
        table.getColumnModel().getColumn(6).setPreferredWidth(80);  // Section
        
        // Center align
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 7; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Empty state
        if (routines.isEmpty()) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
            emptyPanel.setBackground(Color.WHITE);
            emptyPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
            
            JLabel emptyIcon = new JLabel("📅");
            emptyIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
            emptyIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel emptyLabel = new JLabel("No Classes Scheduled");
            emptyLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            emptyLabel.setForeground(new Color(127, 140, 141));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel emptySubLabel = new JLabel("Create your first class to see it here");
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