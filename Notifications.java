class Notifications {
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    public void show() {
        System.out.println("\n" + BLUE + "╔══════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE + "║" + RESET + BOLD + YELLOW + "              🔔 NOTIFICATIONS                            " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╠══════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  🔜 Coming Soon!                                         " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  This feature will show all notifications.              " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "                                                          " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  Features:                                               " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  • Email, SMS & Push Notifications                      " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  • Routine Changes & Exam Alerts                        " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  • Custom Notification Preferences                      " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╚══════════════════════════════════════════════════════════╝" + RESET);
    }
}