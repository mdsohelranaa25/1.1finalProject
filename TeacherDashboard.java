// TeacherDashboard.java
import java.util.Map;
import java.util.Scanner;

public class TeacherDashboard {
    private Map<String, String> teacherInfo;
    
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
    
    public TeacherDashboard(Map<String, String> teacherInfo) {
        this.teacherInfo = teacherInfo;
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
                    new CreateRoutine().show();
                    break;
                case 2:
                    new ViewRoutine().show();
                    break;
                case 3:
                    new AttendanceManagement().show();
                    break;
                case 4:
                    new CourseManagement().show();
                    break;
                case 5:
                    new ExamSchedule().show();
                    break;
                case 6:
                    new StudentManagement().show();
                    break;
                case 7:
                    new Reports().show();
                    break;
                case 8:
                    new Notifications().show();
                    break;
                case 9:
                    new ProfileManagement().show();
                    break;
                case 10:
                    System.out.println("\n" + YELLOW + "👋 Logging out... Goodbye!" + RESET);
                    Main.pause(scanner);
                    return;
                default:
                    System.out.println(RED + "\n❌ Invalid choice! Please select 1-10." + RESET);
            }
            Main.pause(scanner);
        }
    }
    
    private void displayHeader() {
        System.out.println(BLUE + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE + "║" + RESET + BOLD + CYAN + "                  👨‍🏫 TEACHER DASHBOARD                        " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╠══════════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  👤 " + teacherInfo.get("name") + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  📚 Subject: " + YELLOW + teacherInfo.get("field1") + 
                          RESET + WHITE + " | Dept: " + YELLOW + teacherInfo.get("field2") + RESET);
        System.out.println(BLUE + "╚══════════════════════════════════════════════════════════════╝" + RESET);
    }
    
    private void displayMenu() {
        System.out.println("\n" + BLUE + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE + "║" + RESET + BOLD + YELLOW + "                      📋 MAIN MENU                            " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╠══════════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + "  " + GREEN + "1. " + RESET + " ➕ " + WHITE + "Create Routine" + RESET + "         - Create new class schedule");
        System.out.println(BLUE + "║" + RESET + "  " + GREEN + "2. " + RESET + " 📅 " + WHITE + "View Routine" + RESET + "           - Check teaching schedule");
        System.out.println(BLUE + "║" + RESET + "  " + GREEN + "3. " + RESET + " ✅ " + WHITE + "Attendance" + RESET + "            - Mark student attendance");
        System.out.println(BLUE + "║" + RESET + "  " + GREEN + "4. " + RESET + " 📚 " + WHITE + "Course Management" + RESET + "     - Manage your courses");
        System.out.println(BLUE + "║" + RESET + "  " + GREEN + "5. " + RESET + " 📝 " + WHITE + "Exam Schedule" + RESET + "         - Create/manage exam schedule");
        System.out.println(BLUE + "║" + RESET + "  " + GREEN + "6. " + RESET + " 👥 " + WHITE + "Student Management" + RESET + "    - Manage student information");
        System.out.println(BLUE + "║" + RESET + "  " + GREEN + "7. " + RESET + " 📊 " + WHITE + "Reports" + RESET + "               - Generate various reports");
        System.out.println(BLUE + "║" + RESET + "  " + GREEN + "8. " + RESET + " 🔔 " + WHITE + "Notifications" + RESET + "         - Send/check notifications");
        System.out.println(BLUE + "║" + RESET + "  " + GREEN + "9. " + RESET + " 👤 " + WHITE + "Profile Management" + RESET + "    - Update your information");
        System.out.println(BLUE + "║" + RESET + "  " + GREEN + "10." + RESET + " 🚪 " + WHITE + "Logout" + RESET + "                - Exit dashboard");
        System.out.println(BLUE + "║" + RESET);
        System.out.println(BLUE + "╚══════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
    }
}