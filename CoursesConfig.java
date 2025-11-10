// CoursesConfig.java - Centralized Course List
import java.util.*;

public class CoursesConfig {
    // All available courses
    public static final String[] ALL_COURSES = {
        "OOP - Object Oriented Programming",
        "Engineering Mathematics",
        "Discrete Mathematics",
        "Electronic Device and Circuit",
        "EDC Lab",
        "OOP Lab"
    };
    
    /**
     * Get all courses as list
     */
    public static List<String> getAllCourses() {
        return Arrays.asList(ALL_COURSES);
    }
    
    /**
     * Get course dropdown items
     */
    public static String[] getCoursesArray() {
        return ALL_COURSES;
    }
    
    /**
     * Check if course exists
     */
    public static boolean isCourseValid(String course) {
        for (String c : ALL_COURSES) {
            if (c.equals(course)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get short course name (for display)
     */
    public static String getShortCourseName(String fullName) {
        if (fullName.contains(" - ")) {
            return fullName.split(" - ")[0];
        }
        return fullName;
    }
}