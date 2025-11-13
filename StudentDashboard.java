// StudentDashboard.java
import java.util.Map;
import java.util.Scanner;

public class StudentDashboard {
    private Map<String, String> studentInfo;
    
    // ANSI Color Codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    public StudentDashboard(Map<String, String> studentInfo) {
        this.studentInfo = studentInfo;
    }
    
    public void show(Scanner scanner) {
        while (true) {
            Main.clearScreen();
            displayHeader();
            displayMenu();
            
            System.out.print(CYAN + "👉 Enter your choice: " + RESET);
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    new ViewRoutine().show();
                    break;
                case 2:
                    new AttendanceManagement().show();
                    break;
                case 3:
                    new CourseManagement().show();
                    break;
                case 4:
                    new ExamSchedule().show();
                    break;
                case 5:
                    new ProfileManagement().show();
                    break;
                case 6:
                    new Notifications().show();
                    break;
                case 7:
                    new Reports().show();
                    break;
                case 8:
                    System.out.println("\n" + YELLOW + "👋 Logging out... Goodbye!" + RESET);
                    Main.pause(scanner);
                    return;
                default:
                    System.out.println(RED + "\n❌ Invalid choice! Please select 1-8." + RESET);
            }
            Main.pause(scanner);
        }
    }
    
    private void displayHeader() {
        System.out.println(CYAN + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + RESET + BOLD + BLUE + "                  🎓 STUDENT DASHBOARD                        " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "╠══════════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(CYAN + "║" + RESET + WHITE + "  👤 " + studentInfo.get("name") + RESET);
        System.out.println(CYAN + "║" + RESET + WHITE + "  🎯 Batch: " + YELLOW + studentInfo.get("field1") + 
                          RESET + WHITE + " | Dept: " + YELLOW + studentInfo.get("field2") + RESET);
        System.out.println(CYAN + "╚══════════════════════════════════════════════════════════════╝" + RESET);
    }
    
    private void displayMenu() {
        System.out.println("\n" + CYAN + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + RESET + BOLD + YELLOW + "                      📋 MAIN MENU                            " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "╠══════════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + "  " + GREEN + "1." + RESET + " 📅 " + WHITE + "View Routine" + RESET + "         - Check your class schedule");
        System.out.println(CYAN + "║" + RESET + "  " + GREEN + "2." + RESET + " ✅ " + WHITE + "Attendance" + RESET + "           - View your attendance record");
        System.out.println(CYAN + "║" + RESET + "  " + GREEN + "3." + RESET + " 📚 " + WHITE + "Course Management" + RESET + "    - Manage enrolled courses");
        System.out.println(CYAN + "║" + RESET + "  " + GREEN + "4." + RESET + " 📝 " + WHITE + "Exam Schedule" + RESET + "        - View exam dates and times");
        System.out.println(CYAN + "║" + RESET + "  " + GREEN + "5." + RESET + " 👤 " + WHITE + "Profile Management" + RESET + "   - Update your information");
        System.out.println(CYAN + "║" + RESET + "  " + GREEN + "6." + RESET + " 🔔 " + WHITE + "Notifications" + RESET + "        - Check important alerts");
        System.out.println(CYAN + "║" + RESET + "  " + GREEN + "7." + RESET + " 📊 " + WHITE + "Reports" + RESET + "              - Generate various reports");
        System.out.println(CYAN + "║" + RESET + "  " + GREEN + "8." + RESET + " 🚪 " + WHITE + "Logout" + RESET + "               - Exit dashboard");
        System.out.println(CYAN + "║" + RESET);
        System.out.println(CYAN + "╚══════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
    }
}

// Simple stub implementations to satisfy compile-time references.
// Replace these with real implementations as you develop the application.

class ViewRoutine {
    public void show() {
        System.out.println("ViewRoutine: feature not implemented yet.");
    }
}

class AttendanceManagement {
    public void show() {
        System.out.println("AttendanceManagement: feature not implemented yet.");
    }
}

class CourseManagement {
    public void show() {
        System.out.println("CourseManagement: feature not implemented yet.");
    }
}

class ExamSchedule {
    public void show() {
        System.out.println("ExamSchedule: feature not implemented yet.");
    }
}

class ProfileManagement {
    public void show() {
        System.out.println("ProfileManagement: feature not implemented yet.");
    }
}

class Notifications {
    public void show() {
        System.out.println("Notifications: feature not implemented yet.");
    }
}

class Reports {
    public void show() {
        System.out.println("Reports: feature not implemented yet.");
    }
}