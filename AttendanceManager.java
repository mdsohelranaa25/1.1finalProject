// AttendanceManager.java - Teacher marks, Student views
import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AttendanceManager {
    private static final String ATTENDANCE_FILE = "attendance.txt";
    private static final String STUDENTS_FILE = "students.txt";
    
    /**
     * Teacher marks attendance for a student
     * Format: studentUsername,course,date,status,markedBy,timestamp
     */
    public static boolean markStudentAttendance(String studentUsername, String course, 
                                               String date, String status, String markedBy) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ATTENDANCE_FILE, true))) {
            writer.println(studentUsername + "," + course + "," + date + "," + 
                          status + "," + markedBy + "," + System.currentTimeMillis());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Check if student's attendance already marked for this date and course
     */
    public static boolean isAttendanceMarked(String studentUsername, String course, String date) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3 && 
                    data[0].equals(studentUsername) && 
                    data[1].equals(course) && 
                    data[2].equals(date)) {
                    return true;
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
     * Get all students (for teacher to mark attendance)
     */
    public static List<Map<String, String>> getAllStudents() {
        List<Map<String, String>> students = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    Map<String, String> student = new HashMap<>();
                    student.put("username", data[0]);
                    student.put("password", data[1]);
                    student.put("name", data[2]);
                    student.put("batch", data[3]);
                    student.put("department", data[4]);
                    students.add(student);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No students registered yet.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return students;
    }
    
    /**
     * Get attendance records for a specific student
     */
    public static List<Map<String, String>> getStudentAttendance(String studentUsername) {
        List<Map<String, String>> records = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6 && data[0].equals(studentUsername)) {
                    Map<String, String> record = new HashMap<>();
                    record.put("student", data[0]);
                    record.put("course", data[1]);
                    record.put("date", data[2]);
                    record.put("status", data[3]);
                    record.put("markedBy", data[4]);
                    record.put("timestamp", data[5]);
                    records.add(record);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return records;
    }
    
    /**
     * Get attendance for a specific course
     */
    public static List<Map<String, String>> getCourseAttendance(String studentUsername, String course) {
        List<Map<String, String>> records = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6 && 
                    data[0].equals(studentUsername) && 
                    data[1].equals(course)) {
                    Map<String, String> record = new HashMap<>();
                    record.put("student", data[0]);
                    record.put("course", data[1]);
                    record.put("date", data[2]);
                    record.put("status", data[3]);
                    record.put("markedBy", data[4]);
                    record.put("timestamp", data[5]);
                    records.add(record);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return records;
    }
    
    /**
     * Calculate attendance percentage for a course
     */
    public static double calculatePercentage(String studentUsername, String course) {
        List<Map<String, String>> records = getCourseAttendance(studentUsername, course);
        
        if (records.isEmpty()) {
            return 0.0;
        }
        
        int total = records.size();
        int present = 0;
        
        for (Map<String, String> record : records) {
            String status = record.get("status");
            if (status.equals("Present") || status.equals("Late")) {
                present++;
            }
        }
        
        return (present * 100.0) / total;
    }
    
    /**
     * Get attendance statistics for all courses (for a student)
     */
    public static Map<String, AttendanceStats> getAllCourseStats(String studentUsername) {
        Map<String, AttendanceStats> statsMap = new HashMap<>();
        List<Map<String, String>> allRecords = getStudentAttendance(studentUsername);
        
        // Group by course
        Map<String, List<Map<String, String>>> courseGroups = new HashMap<>();
        for (Map<String, String> record : allRecords) {
            String course = record.get("course");
            courseGroups.putIfAbsent(course, new ArrayList<>());
            courseGroups.get(course).add(record);
        }
        
        // Calculate stats for each course
        for (String course : courseGroups.keySet()) {
            List<Map<String, String>> courseRecords = courseGroups.get(course);
            
            int total = courseRecords.size();
            int present = 0;
            int absent = 0;
            int late = 0;
            int leave = 0;
            
            for (Map<String, String> record : courseRecords) {
                String status = record.get("status");
                switch (status) {
                    case "Present": present++; break;
                    case "Absent": absent++; break;
                    case "Late": late++; break;
                    case "Leave": leave++; break;
                }
            }
            
            double percentage = total > 0 ? (present + late) * 100.0 / total : 0.0;
            
            AttendanceStats stats = new AttendanceStats();
            stats.course = course;
            stats.total = total;
            stats.present = present;
            stats.absent = absent;
            stats.late = late;
            stats.leave = leave;
            stats.percentage = percentage;
            
            statsMap.put(course, stats);
        }
        
        return statsMap;
    }
    
    /**
     * Get today's attendance status for a course (for teacher dashboard)
     */
    public static Map<String, String> getTodayAttendanceStatus(String course, String date) {
        Map<String, String> statusMap = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4 && 
                    data[1].equals(course) && 
                    data[2].equals(date)) {
                    // student -> status
                    statusMap.put(data[0], data[3]);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return statusMap;
    }
    
    /**
     * Get today's date in dd-MM-yyyy format
     */
    public static String getTodayDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return today.format(formatter);
    }
    
    /**
     * Get courses with low attendance (below 75%)
     */
    public static List<String> getLowAttendanceCourses(String studentUsername) {
        List<String> lowCourses = new ArrayList<>();
        Map<String, AttendanceStats> stats = getAllCourseStats(studentUsername);
        
        for (String course : stats.keySet()) {
            AttendanceStats stat = stats.get(course);
            if (stat.percentage < 75.0) {
                lowCourses.add(course);
            }
        }
        
        return lowCourses;
    }
}

/**
 * Helper class to store attendance statistics
 */
class AttendanceStats {
    public String course;
    public int total;
    public int present;
    public int absent;
    public int late;
    public int leave;
    public double percentage;
}