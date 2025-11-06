class StudentManagement {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    public void show() {
        System.out.println("\n" + GREEN + "╔══════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(GREEN + "║" + RESET + BOLD + YELLOW + "          👥 STUDENT MANAGEMENT                           " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "╠══════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  🔜 Coming Soon!                                         " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  This feature will manage student information.          " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "                                                          " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  Features:                                               " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  • View Student List & Details                          " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  • Update Student Information                           " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  • Track Academic Progress                              " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "╚══════════════════════════════════════════════════════════╝" + RESET);
    }
}