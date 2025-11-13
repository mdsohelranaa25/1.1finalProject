// CreateRoutineFrame.java - Teacher creates routine step by step
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CreateRoutineFrame extends JFrame {
    private Map<String, String> teacherInfo;
    private Color primaryColor = new Color(46, 204, 113);
    
    private JComboBox<String> departmentCombo;
    private JComboBox<String> semesterCombo;
    private JComboBox<String> sectionCombo;
    private JComboBox<String> dayCombo;
    private JComboBox<String> timeSlotCombo;
    private JComboBox<String> courseCombo;
    private JComboBox<String> roomCombo;
    private JTextField teacherNameField;
    
    public CreateRoutineFrame(Map<String, String> teacherInfo) {
        this.teacherInfo = teacherInfo;
        
        setTitle("Create Routine - " + teacherInfo.get("name"));
        setSize(800, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Header
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // ✅ Wrap form panel with JScrollPane to enable scrolling
        JScrollPane scrollPane = new JScrollPane(createFormPanel(),
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(236, 240, 241));
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(800, 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(primaryColor);
        
        JLabel titleLabel = new JLabel("➕ CREATE CLASS ROUTINE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Add a new class to the schedule");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);
        
        JButton backButton = createStyledButton("← Back");
        backButton.addActionListener(e -> dispose());
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Section 1: Class Info
        addSectionLabel(formPanel, "📋 CLASS INFORMATION", gbc, row++);
        
        // Department
        String[] departments = {"CSE", "EEE", "BBA", "Civil", "Mechanical"};
        departmentCombo = new JComboBox<>(departments);
        departmentCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addFormField(formPanel, "🏛️ Department:", departmentCombo, gbc, row++);
        
        // Semester
        String[] semesters = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th"};
        semesterCombo = new JComboBox<>(semesters);
        semesterCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addFormField(formPanel, "📚 Semester:", semesterCombo, gbc, row++);
        
        // Section
        String[] sections = {"A", "B", "C", "D"};
        sectionCombo = new JComboBox<>(sections);
        sectionCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addFormField(formPanel, "📑 Section:", sectionCombo, gbc, row++);
        
        // Section 2: Schedule
        addSectionLabel(formPanel, "📅 SCHEDULE", gbc, row++);
        
        // Day
        dayCombo = new JComboBox<>(RoutineManager.DAYS);
        dayCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addFormField(formPanel, "📆 Day:", dayCombo, gbc, row++);
        
        // Time Slot
        timeSlotCombo = new JComboBox<>(RoutineManager.TIME_SLOTS);
        timeSlotCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addFormField(formPanel, "⏰ Time Slot:", timeSlotCombo, gbc, row++);
        
        // Section 3: Course Details
        addSectionLabel(formPanel, "📖 COURSE DETAILS", gbc, row++);
        
        // Course
        courseCombo = new JComboBox<>(CoursesConfig.getCoursesArray());
        courseCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addFormField(formPanel, "📚 Course:", courseCombo, gbc, row++);
        
        // Teacher (auto-filled with logged-in teacher)
        teacherNameField = new JTextField(teacherInfo.get("username"));
        teacherNameField.setFont(new Font("Segoe UI", Font.BOLD, 14));
        teacherNameField.setEnabled(false); // Read-only
        teacherNameField.setBackground(new Color(240, 240, 240));
        addFormField(formPanel, "👨‍🏫 Teacher:", teacherNameField, gbc, row++);
        
        // Room
        roomCombo = new JComboBox<>(RoutineManager.ROOMS);
        roomCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addFormField(formPanel, "🚪 Room:", roomCombo, gbc, row++);
        
        // Info Label
        JLabel infoLabel = new JLabel("<html>💡 <b>Note:</b> System will check for conflicts " +
                                      "(teacher/room availability)</html>");
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        infoLabel.setForeground(new Color(127, 140, 141));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        formPanel.add(infoLabel, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton createButton = createStyledButton("✅ Create Class", primaryColor);
        createButton.setPreferredSize(new Dimension(180, 50));
        createButton.addActionListener(e -> handleCreateRoutine());
        
        JButton viewButton = createStyledButton("📅 View Routine", new Color(52, 152, 219));
        viewButton.setPreferredSize(new Dimension(180, 50));
        viewButton.addActionListener(e -> {
            new TeacherViewRoutineFrame(teacherInfo);
        });
        
        JButton cancelButton = createStyledButton("❌ Cancel", new Color(231, 76, 60));
        cancelButton.setPreferredSize(new Dimension(150, 50));
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(createButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridy = row;
        formPanel.add(buttonPanel, gbc);
        
        return formPanel;
    }
    
    private void addSectionLabel(JPanel panel, String text, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(primaryColor);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        panel.add(label, gbc);
    }
    
    private void addFormField(JPanel panel, String labelText, JComponent field, 
                             GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(44, 62, 80));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        if (field instanceof JTextField) {
            field.setPreferredSize(new Dimension(300, 40));
        } else if (field instanceof JComboBox) {
            field.setPreferredSize(new Dimension(300, 40));
        }
        panel.add(field, gbc);
    }
    
    private void handleCreateRoutine() {
        String department = (String) departmentCombo.getSelectedItem();
        String semester = (String) semesterCombo.getSelectedItem();
        String section = (String) sectionCombo.getSelectedItem();
        String day = (String) dayCombo.getSelectedItem();
        String timeSlot = (String) timeSlotCombo.getSelectedItem();
        String course = (String) courseCombo.getSelectedItem();
        String teacher = teacherInfo.get("username");
        String room = (String) roomCombo.getSelectedItem();
        
        // Check for conflicts
        if (RoutineManager.hasConflict(day, timeSlot, teacher, room)) {
            String conflictMsg = "";
            java.util.List<Map<String, String>> allRoutines = RoutineManager.getAllRoutines();
            
            for (Map<String, String> r : allRoutines) {
                if (r.get("day").equals(day) && r.get("timeSlot").equals(timeSlot)) {
                    if (r.get("teacher").equals(teacher)) {
                        conflictMsg = "⚠️ Conflict Detected!\n\n" +
                                    "You already have a class at this time:\n" +
                                    "Course: " + r.get("course") + "\n" +
                                    "Room: " + r.get("room") + "\n" +
                                    "Day: " + day + "\n" +
                                    "Time: " + timeSlot;
                        break;
                    }
                    if (r.get("room").equals(room)) {
                        conflictMsg = "⚠️ Room Conflict!\n\n" +
                                    "Room " + room + " is already booked:\n" +
                                    "Course: " + r.get("course") + "\n" +
                                    "Teacher: " + r.get("teacher") + "\n" +
                                    "Day: " + day + "\n" +
                                    "Time: " + timeSlot;
                        break;
                    }
                }
            }
            
            JOptionPane.showMessageDialog(this,
                conflictMsg,
                "Conflict Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Save routine
        if (RoutineManager.saveRoutineClass(day, timeSlot, course, teacher, room,
                                           department, semester, section)) {
            JOptionPane.showMessageDialog(this,
                "✅ Class Added Successfully!\n\n" +
                "Department: " + department + " - " + semester + " - Section " + section + "\n" +
                "Day: " + day + "\n" +
                "Time: " + timeSlot + "\n" +
                "Course: " + course + "\n" +
                "Room: " + room + "\n\n" +
                "Students can now view this class in their routine.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            dayCombo.setSelectedIndex(0);
            timeSlotCombo.setSelectedIndex(0);
            courseCombo.setSelectedIndex(0);
            roomCombo.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to create routine!\nPlease try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
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
