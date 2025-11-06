class CreateRoutine {
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    public void show() {
        System.out.println("\n" + BLUE + "╔══════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE + "║" + RESET + BOLD + YELLOW + "              ➕ CREATE ROUTINE                           " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╠══════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  🔜 Coming Soon!                                         " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  This feature will allow you to create schedules.       " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "                                                          " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  Features:                                               " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  • Smart Scheduling with Conflict Detection             " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  • Room & Teacher Assignment                            " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  • Auto-save & Draft Mode                               " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╚══════════════════════════════════════════════════════════╝" + RESET);
    }
}