class CourseManagement {
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    public void show() {
        System.out.println("\n" + BLUE + "╔══════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE + "║" + RESET + BOLD + YELLOW + "            📚 COURSE MANAGEMENT                          " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╠══════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  🔜 Coming Soon!                                         " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  This feature will manage courses and enrollments.      " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "                                                          " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  Features:                                               " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  • Add/Edit/Delete Courses                              " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  • Student Enrollment & Teacher Assignment              " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + WHITE + "  • Course Materials & Syllabus Upload                   " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╚══════════════════════════════════════════════════════════╝" + RESET);
    }
}