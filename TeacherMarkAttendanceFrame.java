// TeacherMarkAttendanceFrame.java - Teacher marks attendance for all students
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TeacherMarkAttendanceFrame extends JFrame {
    private Map<String, String> teacherInfo;
    private Color primaryColor = new Color(52, 152, 219);
    
    private String teacherCourse;
    private String todayDate;
    private JTable studentsTable;
    private DefaultTableModel tableModel;
    private Map<String, JComboBox<String>> statusCombos;
    
    public TeacherMarkAttendanceFrame(Map<String, String> teacherInfo) {
        this.teacherInfo = teacherInfo;
        this.teacherCourse = teacherInfo.get("field1"); // Teacher's course
        this.todayDate = AttendanceManager.getTodayDate();
        this.statusCombos = new HashMap<>();
        
        setTitle("Mark Attendance - " + teacherCourse);
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Header
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Students List
        mainPanel.add(createStudentsPanel(), BorderLayout.CENTER);
        
        // Buttons
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(1100, 120));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        // Title
        JLabel titleLabel = new JLabel("✅ Mark Attendance");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Course and Date info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        infoPanel.setBackground(primaryColor);
        
        JLabel courseLabel = new JLabel("📚 " + teacherCourse);
        courseLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        courseLabel.setForeground(new Color(255, 255, 255, 230));
        
        JLabel dateLabel = new JLabel("📅 " + todayDate);
        dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dateLabel.setForeground(new Color(255, 255, 255, 230));
        
        infoPanel.add(courseLabel);
        infoPanel.add(dateLabel);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(infoPanel);
        
        return headerPanel;
    }
    
    private JScrollPane createStudentsPanel() {
        // Get all students
        List<Map<String, String>> students = AttendanceManager.getAllStudents();
        
        // Get today's attendance status (if already marked)
        Map<String, String> todayStatus = AttendanceManager.getTodayAttendanceStatus(
            teacherCourse, todayDate);
        
        // Create table
        String[] columnNames = {"Roll", "Student Name", "Batch", "Dept", "Status", "Icon"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only status column editable
            }
        };
        
        // Populate table
        int roll = 1;
        for (Map<String, String> student : students) {
            String username = student.get("username");
            String name = student.get("name");
            String batch = student.get("batch");
            String dept = student.get("department");
            
            // Check if already marked
            String currentStatus = todayStatus.getOrDefault(username, "Not Marked");
            String icon = getStatusIcon(currentStatus);
            
            tableModel.addRow(new Object[]{
                roll++, name, batch, dept, currentStatus, icon
            });
            
            // Store student username for this row
            tableModel.setValueAt(username, roll - 2, 6); // Hidden column
        }
        
        studentsTable = new JTable(tableModel);
        studentsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentsTable.setRowHeight(50);
        studentsTable.setSelectionBackground(new Color(52, 152, 219, 100));
        studentsTable.setGridColor(new Color(220, 220, 220));
        
        // Hide the username column
        studentsTable.getColumnModel().getColumn(6).setMinWidth(0);
        studentsTable.getColumnModel().getColumn(6).setMaxWidth(0);
        studentsTable.getColumnModel().getColumn(6).setWidth(0);
        
        // Column widths
        studentsTable.getColumnModel().getColumn(0).setPreferredWidth(60);  // Roll
        studentsTable.getColumnModel().getColumn(1).setPreferredWidth(250); // Name
        studentsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Batch
        studentsTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Dept
        studentsTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Status
        studentsTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Icon
        
        // Custom renderer for status column with dropdown
        studentsTable.getColumnModel().getColumn(4).setCellEditor(
            new DefaultCellEditor(createStatusCombo()));
        
        // Style header
        JTableHeader header = studentsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        
        // Center align columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 6; i++) {
            studentsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(studentsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        return scrollPane;
    }
    
    private JComboBox<String> createStatusCombo() {
        String[] statuses = {"Present", "Absent", "Late", "Leave", "Not Marked"};
        JComboBox<String> combo = new JComboBox<>(statuses);
        combo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return combo;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JButton markAllPresentButton = createStyledButton("✅ Mark All Present", new Color(46, 204, 113));
        markAllPresentButton.setPreferredSize(new Dimension(200, 50));
        markAllPresentButton.addActionListener(e -> markAllAs("Present"));
        
        JButton markAllAbsentButton = createStyledButton("❌ Mark All Absent", new Color(231, 76, 60));
        markAllAbsentButton.setPreferredSize(new Dimension(200, 50));
        markAllAbsentButton.addActionListener(e -> markAllAs("Absent"));
        
        JButton saveButton = createStyledButton("💾 Save Attendance", primaryColor);
        saveButton.setPreferredSize(new Dimension(200, 50));
        saveButton.addActionListener(e -> handleSaveAttendance());
        
        JButton backButton = createStyledButton("← Back", new Color(52, 73, 94));
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(e -> dispose());
        
        buttonPanel.add(markAllPresentButton);
        buttonPanel.add(markAllAbsentButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);
        
        return buttonPanel;
    }
    
    private void markAllAs(String status) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(status, i, 4);
            tableModel.setValueAt(getStatusIcon(status), i, 5);
        }
    }
    
    private void handleSaveAttendance() {
        int successCount = 0;
        int alreadyMarked = 0;
        int errorCount = 0;
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String studentUsername = (String) tableModel.getValueAt(i, 6); // Hidden column
            String status = (String) tableModel.getValueAt(i, 4);
            
            if (status.equals("Not Marked")) {
                continue; // Skip if not marked
            }
            
            // Check if already marked
            if (AttendanceManager.isAttendanceMarked(studentUsername, teacherCourse, todayDate)) {
                alreadyMarked++;
                continue;
            }
            
            // Mark attendance
            if (AttendanceManager.markStudentAttendance(studentUsername, teacherCourse, 
                                                       todayDate, status, 
                                                       teacherInfo.get("username"))) {
                successCount++;
            } else {
                errorCount++;
            }
        }
        
        // Show result
        StringBuilder message = new StringBuilder();
        message.append("📊 Attendance Marking Summary:\n\n");
        message.append("✅ Successfully marked: " + successCount + "\n");
        if (alreadyMarked > 0) {
            message.append("⚠️ Already marked: " + alreadyMarked + "\n");
        }
        if (errorCount > 0) {
            message.append("❌ Errors: " + errorCount + "\n");
        }
        message.append("\nCourse: " + teacherCourse + "\n");
        message.append("Date: " + todayDate);
        
        JOptionPane.showMessageDialog(this,
            message.toString(),
            "Attendance Saved",
            JOptionPane.INFORMATION_MESSAGE);
        
        if (successCount > 0) {
            dispose();
        }
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