// RoutineManager.java - Complete Routine File Management
import java.io.*;
import java.util.*;

public class RoutineManager {
    private static final String ROUTINE_FILE = "routines.txt";
    
    // Time slots available
    public static final String[] TIME_SLOTS = {
        "08:00 AM - 09:00 AM",
        "09:00 AM - 10:00 AM",
        "10:00 AM - 11:00 AM",
        "11:00 AM - 12:00 PM",
        "12:00 PM - 01:00 PM",
        "01:00 PM - 02:00 PM",
        "02:00 PM - 03:00 PM",
        "03:00 PM - 04:00 PM",
        "04:00 PM - 05:00 PM"
    };
    
    // Days of week
    public static final String[] DAYS = {
        "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"
    };
    
    // Rooms available
    public static final String[] ROOMS = {
        "Room 301", "Room 302", "Room 303", "Room 304", "Room 305",
        "Lab 1", "Lab 2", "Lab 3", "Auditorium", "Seminar Hall"
    };
    
    /**
     * Save a routine class
     * Format: day,timeSlot,course,teacher,room,department,semester,section,timestamp
     */
    public static boolean saveRoutineClass(String day, String timeSlot, String course,
                                          String teacher, String room, String department,
                                          String semester, String section) {
        // Check for conflicts
        if (hasConflict(day, timeSlot, teacher, room)) {
            return false;
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(ROUTINE_FILE, true))) {
            writer.println(day + "," + timeSlot + "," + course + "," + teacher + "," + 
                          room + "," + department + "," + semester + "," + section + "," +
                          System.currentTimeMillis());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Check if there's a conflict (same teacher or room at same time)
     */
    public static boolean hasConflict(String day, String timeSlot, String teacher, String room) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ROUTINE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String existingDay = data[0];
                    String existingTime = data[1];
                    String existingTeacher = data[3];
                    String existingRoom = data[4];
                    
                    if (existingDay.equals(day) && existingTime.equals(timeSlot)) {
                        // Same day and time
                        if (existingTeacher.equals(teacher)) {
                            return true; // Teacher conflict
                        }
                        if (existingRoom.equals(room)) {
                            return true; // Room conflict
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Get all routine classes
     */
    public static List<Map<String, String>> getAllRoutines() {
        List<Map<String, String>> routines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ROUTINE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 9) {
                    Map<String, String> routine = new HashMap<>();
                    routine.put("day", data[0]);
                    routine.put("timeSlot", data[1]);
                    routine.put("course", data[2]);
                    routine.put("teacher", data[3]);
                    routine.put("room", data[4]);
                    routine.put("department", data[5]);
                    routine.put("semester", data[6]);
                    routine.put("section", data[7]);
                    routine.put("timestamp", data[8]);
                    routines.add(routine);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return routines;
    }
    
    /**
     * Get routines for specific department/semester/section
     */
    public static List<Map<String, String>> getRoutinesByFilter(String department, 
                                                                 String semester, 
                                                                 String section) {
        List<Map<String, String>> filteredRoutines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ROUTINE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 9) {
                    boolean matches = true;
                    
                    if (department != null && !department.isEmpty() && 
                        !data[5].equals(department)) {
                        matches = false;
                    }
                    if (semester != null && !semester.isEmpty() && 
                        !data[6].equals(semester)) {
                        matches = false;
                    }
                    if (section != null && !section.isEmpty() && 
                        !data[7].equals(section)) {
                        matches = false;
                    }
                    
                    if (matches) {
                        Map<String, String> routine = new HashMap<>();
                        routine.put("day", data[0]);
                        routine.put("timeSlot", data[1]);
                        routine.put("course", data[2]);
                        routine.put("teacher", data[3]);
                        routine.put("room", data[4]);
                        routine.put("department", data[5]);
                        routine.put("semester", data[6]);
                        routine.put("section", data[7]);
                        routine.put("timestamp", data[8]);
                        filteredRoutines.add(routine);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return filteredRoutines;
    }
    
    /**
     * Get routines for specific teacher
     */
    public static List<Map<String, String>> getRoutinesByTeacher(String teacherUsername) {
        List<Map<String, String>> teacherRoutines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ROUTINE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 9 && data[3].equals(teacherUsername)) {
                    Map<String, String> routine = new HashMap<>();
                    routine.put("day", data[0]);
                    routine.put("timeSlot", data[1]);
                    routine.put("course", data[2]);
                    routine.put("teacher", data[3]);
                    routine.put("room", data[4]);
                    routine.put("department", data[5]);
                    routine.put("semester", data[6]);
                    routine.put("section", data[7]);
                    routine.put("timestamp", data[8]);
                    teacherRoutines.add(routine);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return teacherRoutines;
    }
    
    /**
     * Get routines for a specific day
     */
    public static List<Map<String, String>> getRoutinesByDay(String day, String department,
                                                             String semester, String section) {
        List<Map<String, String>> dayRoutines = new ArrayList<>();
        List<Map<String, String>> allRoutines = getRoutinesByFilter(department, semester, section);
        
        for (Map<String, String> routine : allRoutines) {
            if (routine.get("day").equals(day)) {
                dayRoutines.add(routine);
            }
        }
        
        // Sort by time slot
        dayRoutines.sort((a, b) -> {
            int indexA = Arrays.asList(TIME_SLOTS).indexOf(a.get("timeSlot"));
            int indexB = Arrays.asList(TIME_SLOTS).indexOf(b.get("timeSlot"));
            return Integer.compare(indexA, indexB);
        });
        
        return dayRoutines;
    }
    
    /**
     * Delete a routine class
     */
    public static boolean deleteRoutineClass(String day, String timeSlot, String department,
                                            String semester, String section) {
        List<String> lines = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ROUTINE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 8 &&
                    data[0].equals(day) &&
                    data[1].equals(timeSlot) &&
                    data[5].equals(department) &&
                    data[6].equals(semester) &&
                    data[7].equals(section)) {
                    found = true;
                    continue; // Skip this line
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        if (found) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(ROUTINE_FILE))) {
                for (String line : lines) {
                    writer.println(line);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * Check if routine is empty for a slot
     */
    public static boolean isSlotEmpty(String day, String timeSlot, String department,
                                     String semester, String section) {
        List<Map<String, String>> routines = getRoutinesByFilter(department, semester, section);
        
        for (Map<String, String> routine : routines) {
            if (routine.get("day").equals(day) && routine.get("timeSlot").equals(timeSlot)) {
                return false;
            }
        }
        return true;
    }
}