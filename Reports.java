class Reports {
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    public void show() {
        System.out.println("\n" + CYAN + "╔══════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + RESET + BOLD + YELLOW + "                📊 REPORTS                                " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "╠══════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(CYAN + "║" + RESET + WHITE + "  🔜 Coming Soon!                                         " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + WHITE + "  This feature will generate various reports.            " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + WHITE + "                                                          " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + WHITE + "  Features:                                               " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + WHITE + "  • Attendance Reports & Analytics                       " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + WHITE + "  • Performance Reports & Grade Sheets                   " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + WHITE + "  • PDF/Excel Export Options                             " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "╚══════════════════════════════════════════════════════════╝" + RESET);
    }
}