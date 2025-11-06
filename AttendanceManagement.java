class AttendanceManagement {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    public void show() {
        System.out.println("\n" + GREEN + "╔══════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(GREEN + "║" + RESET + BOLD + YELLOW + "          ✅ ATTENDANCE MANAGEMENT                        " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "╠══════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  🔜 Coming Soon!                                         " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  This feature will manage student attendance.           " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "                                                          " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  Features:                                               " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  • QR Code Scanning & Manual Entry                      " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  • Attendance Reports & Analytics                       " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  • Low Attendance Alerts (75% rule)                     " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "╚══════════════════════════════════════════════════════════╝" + RESET);
    }
}