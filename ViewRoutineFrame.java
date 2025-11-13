// ViewRoutineFrame.java - Student views routine (Weekly Timetable)
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ViewRoutineFrame extends JFrame {
    private Map<String, String> userInfo;
    private Color primaryColor = new Color(52, 152, 219);
    
    private JComboBox<String> departmentCombo;
    private JComboBox<String> semesterCombo;
    private JComboBox<String> sectionCombo;
    private JTable routineTable;
    
    public ViewRoutineFrame(Map<String, String> userInfo) {
        this.userInfo = userInfo;
        
        setTitle("View Routine - " + userInfo.get("name"));
        setSize(1400, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Header
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Filter Panel
        mainPanel.add(createFilterPanel(), BorderLayout.WEST);
        
        // Routine Table (Weekly View)
        mainPanel.add(createWeeklyRoutinePanel(), BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(1400, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        JLabel titleLabel = new JLabel("📅 CLASS ROUTINE - WEEKLY VIEW");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JButton backButton = createStyledButton("← Back");
        backButton.addActionListener(e -> dispose());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setPreferredSize(new Dimension(280, 800));
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel filterLabel = new JLabel("🔍 FILTERS");
        filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        filterLabel.setForeground(primaryColor);
        filterLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        filterPanel.add(filterLabel);
        filterPanel.add(Box.createVerticalStrut(20));
        
        // Department (auto-select from student info)
        addFilterLabel(filterPanel, "Department:");
        String[] departments = {"CSE", "EEE", "BBA", "Civil", "Mechanical"};
        departmentCombo = new JComboBox<>(departments);
        departmentCombo.setSelectedItem(userInfo.get("field2")); // Student's department
        departmentCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        departmentCombo.setMaximumSize(new Dimension(240, 40));
        departmentCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        filterPanel.add(departmentCombo);
        filterPanel.add(Box.createVerticalStrut(15));
        
        // Semester
        addFilterLabel(filterPanel, "Semester:");
        String[] semesters = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th"};
        semesterCombo = new JComboBox<>(semesters);
        semesterCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        semesterCombo.setMaximumSize(new Dimension(240, 40));
        semesterCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        filterPanel.add(semesterCombo);
        filterPanel.add(Box.createVerticalStrut(15));
        
        // Section
        addFilterLabel(filterPanel, "Section:");
        String[] sections = {"A", "B", "C", "D"};
        sectionCombo = new JComboBox<>(sections);
        sectionCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sectionCombo.setMaximumSize(new Dimension(240, 40));
        sectionCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        filterPanel.add(sectionCombo);
        filterPanel.add(Box.createVerticalStrut(25));
        
        // Show Routine Button
        JButton showButton = createStyledButton("📅 Show Routine", primaryColor);
        showButton.setMaximumSize(new Dimension(240, 50));
        showButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        showButton.addActionListener(e -> refreshRoutine());
        filterPanel.add(showButton);
        
        return filterPanel;
    }
    
    private void addFilterLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(44, 62, 80));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
    }
    
    private JScrollPane createWeeklyRoutinePanel() {
        String department = (String) departmentCombo.getSelectedItem();
        String semester = (String) semesterCombo.getSelectedItem();
        String section = (String) sectionCombo.getSelectedItem();
        
        // Create table with Days as columns
        String[] columnNames = new String[RoutineManager.DAYS.length + 1];
        columnNames[0] = "Time";
        System.arraycopy(RoutineManager.DAYS, 0, columnNames, 1, RoutineManager.DAYS.length);
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Get all routines for this dept/sem/sec
        List<Map<String, String>> routines = RoutineManager.getRoutinesByFilter(
            department, semester, section);
        
        // Create a map: timeSlot -> day -> class info
        Map<String, Map<String, String>> schedule = new HashMap<>();
        
        for (Map<String, String> routine : routines) {
            String timeSlot = routine.get("timeSlot");
            String day = routine.get("day");
            String classInfo = "<html><b>" + routine.get("course") + "</b><br>" +
                              "Room: " + routine.get("room") + "<br>" +
                              "Teacher: " + routine.get("teacher") + "</html>";
            
            schedule.putIfAbsent(timeSlot, new HashMap<>());
            schedule.get(timeSlot).put(day, classInfo);
        }
        
        // Populate table
        for (String timeSlot : RoutineManager.TIME_SLOTS) {
            Object[] row = new Object[RoutineManager.DAYS.length + 1];
            row[0] = timeSlot;
            
            Map<String, String> dayClasses = schedule.get(timeSlot);
            for (int i = 0; i < RoutineManager.DAYS.length; i++) {
                if (dayClasses != null && dayClasses.containsKey(RoutineManager.DAYS[i])) {
                    row[i + 1] = dayClasses.get(RoutineManager.DAYS[i]);
                } else {
                    row[i + 1] = "<html><center>—<br>Free</center></html>";
                }
            }
            
            model.addRow(row);
        }
        
        routineTable = new JTable(model);
        routineTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        routineTable.setRowHeight(80);
        routineTable.setGridColor(new Color(220, 220, 220));
        routineTable.setShowGrid(true);
        
        // Style header
        JTableHeader header = routineTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        
        // Column widths
        routineTable.getColumnModel().getColumn(0).setPreferredWidth(150); // Time
        for (int i = 1; i <= RoutineManager.DAYS.length; i++) {
            routineTable.getColumnModel().getColumn(i).setPreferredWidth(180);
        }
        
        // Custom renderer for cells
        routineTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                          boolean isSelected, boolean hasFocus,
                                                          int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, 
                                                                 isSelected, hasFocus, row, column);
                
                if (column == 0) {
                    // Time column
                    setFont(new Font("Segoe UI", Font.BOLD, 13));
                    setBackground(new Color(240, 240, 240));
                    setHorizontalAlignment(CENTER);
                } else {
                    setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    String text = value.toString();
                    if (text.contains("Free")) {
                        setBackground(new Color(250, 250, 250));
                        setForeground(new Color(180, 180, 180));
                    } else {
                        setBackground(new Color(230, 247, 255));
                        setForeground(Color.BLACK);
                    }
                    setHorizontalAlignment(CENTER);
                    setVerticalAlignment(TOP);
                }
                
                setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(routineTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        return scrollPane;
    }
    
    private void refreshRoutine() {
        // Remove old table
        Container parent = routineTable.getParent().getParent();
        ((JPanel) getContentPane()).remove((JComponent) parent);
        
        // Add new table
        ((JPanel) getContentPane()).add(createWeeklyRoutinePanel(), BorderLayout.CENTER);
        
        // Refresh UI
        revalidate();
        repaint();
    }
    
    private JButton createStyledButton(String text) {
        return createStyledButton(text, new Color(52, 73, 94));
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(120, 40));
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