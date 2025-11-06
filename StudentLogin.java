// StudentLogin.java
import java.util.Map;
import java.util.Scanner;

public class StudentLogin {
    // ANSI Color Codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    public void login(Scanner scanner) {
        Main.clearScreen();
        displayHeader();
        
        System.out.println(CYAN + "🔐 Please enter your credentials:\n" + RESET);
        
        System.out.print(WHITE + "👤 Username: " + RESET);
        String username = scanner.nextLine().trim();
        
        System.out.print(WHITE + "🔒 Password: " + RESET);
        String password = scanner.nextLine().trim();
        
        System.out.println("\n" + YELLOW + "⏳ Authenticating..." + RESET);
        
        // Simulate loading
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Map<String, String> student = FileManager.findStudent(username, password);
        
        if (student != null) {
            System.out.println(GREEN + "\n✅ Login Successful!" + RESET);
            System.out.println(GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
            System.out.println(BOLD + CYAN + "Welcome back, " + student.get("name") + "! 👋" + RESET);
            System.out.println(WHITE + "Batch: " + YELLOW + student.get("field1") + RESET);
            System.out.println(WHITE + "Department: " + YELLOW + student.get("field2") + RESET);
            System.out.println(GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
            
            Main.pause(scanner);
            
            StudentDashboard dashboard = new StudentDashboard(student);
            dashboard.show(scanner);
        } else {
            System.out.println("\n" + RED + "╔═══════════════════════════════════════════════════╗" + RESET);
            System.out.println(RED + "║" + RESET + BOLD + RED + "          ❌ LOGIN FAILED                          " + RESET + RED + "║" + RESET);
            System.out.println(RED + "╠═══════════════════════════════════════════════════╣" + RESET);
            System.out.println(RED + "║" + RESET + WHITE + "  Invalid username or password!                    " + RESET + RED + "║" + RESET);
            System.out.println(RED + "║" + RESET + WHITE + "  Please check your credentials and try again.     " + RESET + RED + "║" + RESET);
            System.out.println(RED + "╚═══════════════════════════════════════════════════╝" + RESET);
            Main.pause(scanner);
        }
    }
    
    private void displayHeader() {
        System.out.println(BLUE + "╔══════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE + "║" + RESET + BOLD + CYAN + "                  🎓 STUDENT LOGIN                        " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╠══════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(BLUE + "║" + RESET + YELLOW + "     Access your dashboard and manage your schedule       " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╚══════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
    }
}