// TeacherRegistration.java
import java.util.Scanner;

public class TeacherRegistration {
    // ANSI Color Codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    public void register(Scanner scanner) {
        Main.clearScreen();
        displayHeader();
        
        System.out.println(CYAN + "📝 Please fill in your information:\n" + RESET);
        
        // Get Name
        System.out.print(WHITE + "👤 Full Name: " + RESET);
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println(RED + "\n❌ Name cannot be empty!" + RESET);
            Main.pause(scanner);
            return;
        }
        
        // Get Unique Username
        String username;
        while (true) {
            System.out.print(WHITE + "🔑 Username (unique): " + RESET);
            username = scanner.nextLine().trim();
            
            if (username.isEmpty()) {
                System.out.println(RED + "❌ Username cannot be empty!" + RESET);
                continue;
            }
            
            if (FileManager.usernameExists("teachers.txt", username)) {
                System.out.println(RED + "❌ Username '" + username + "' already exists! Please choose another." + RESET);
            } else {
                System.out.println(GREEN + "✅ Username is available!" + RESET);
                break;
            }
        }
        
        // Get Password
        String password;
        while (true) {
            System.out.print(WHITE + "🔒 Password (min 6 characters): " + RESET);
            password = scanner.nextLine().trim();
            
            if (password.length() < 6) {
                System.out.println(RED + "❌ Password must be at least 6 characters!" + RESET);
            } else {
                break;
            }
        }
        
        // Get Subject
        System.out.print(WHITE + "📚 Subject/Course (e.g., Programming, Mathematics): " + RESET);
        String subject = scanner.nextLine().trim();
        
        // Get Department
        System.out.print(WHITE + "🏛️  Department (e.g., CSE, EEE, BBA): " + RESET);
        String department = scanner.nextLine().trim().toUpperCase();
        
        // Display Summary
        System.out.println("\n" + CYAN + "═════════════════════════════════════════════════════" + RESET);
        System.out.println(YELLOW + "📋 Registration Summary:" + RESET);
        System.out.println(CYAN + "═════════════════════════════════════════════════════" + RESET);
        System.out.println(WHITE + "Name       : " + GREEN + name + RESET);
        System.out.println(WHITE + "Username   : " + GREEN + username + RESET);
        System.out.println(WHITE + "Subject    : " + GREEN + subject + RESET);
        System.out.println(WHITE + "Department : " + GREEN + department + RESET);
        System.out.println(CYAN + "═════════════════════════════════════════════════════\n" + RESET);
        
        // Confirm
        System.out.print(YELLOW + "✓ Confirm registration? (Y/N): " + RESET);
        String confirm = scanner.nextLine().trim().toUpperCase();
        
        if (confirm.equals("Y") || confirm.equals("YES")) {
            if (FileManager.saveTeacher(username, password, name, subject, department)) {
                System.out.println("\n" + GREEN + "╔═══════════════════════════════════════════════════╗" + RESET);
                System.out.println(GREEN + "║" + RESET + BOLD + GREEN + "    🎉 REGISTRATION SUCCESSFUL! 🎉                 " + RESET + GREEN + "║" + RESET);
                System.out.println(GREEN + "╠═══════════════════════════════════════════════════╣" + RESET);
                System.out.println(GREEN + "║" + RESET + WHITE + "  Welcome to University Routine Management!       " + RESET + GREEN + "║" + RESET);
                System.out.println(GREEN + "║" + RESET + WHITE + "  You can now login with your credentials.        " + RESET + GREEN + "║" + RESET);
                System.out.println(GREEN + "╚═══════════════════════════════════════════════════╝" + RESET);
            } else {
                System.out.println("\n" + RED + "❌ Registration Failed! Please try again." + RESET);
            }
        } else {
            System.out.println("\n" + YELLOW + "⚠️  Registration cancelled!" + RESET);
        }
        
        Main.pause(scanner);
    }
    
    private void displayHeader() {
        System.out.println(BLUE + "╔══════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE + "║" + RESET + BOLD + CYAN + "              👨‍🏫 TEACHER REGISTRATION                      " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╠══════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(BLUE + "║" + RESET + YELLOW + "  Join our faculty and manage your teaching schedule      " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╚══════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
    }
}