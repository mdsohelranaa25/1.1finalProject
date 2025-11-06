class ProfileManagement {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    public void show() {
        System.out.println("\n" + GREEN + "╔══════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(GREEN + "║" + RESET + BOLD + YELLOW + "           👤 PROFILE MANAGEMENT                          " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "╠══════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  🔜 Coming Soon!                                         " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  This feature will allow profile updates.               " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "                                                          " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  Features:                                               " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  • Update Personal Information                          " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  • Change Password & Security Settings                  " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + WHITE + "  • Upload Photo & Contact Details                       " + RESET + GREEN + "║" + RESET);
        System.out.println(GREEN + "╚══════════════════════════════════════════════════════════╝" + RESET);
    }
}