import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class AttendanceManager {

    private static final String STUDENT_FILE = "Student.txt";
    private static final String ATTENDANCE_FILE = "Attendance.txt";

    // ✅ আজকের তারিখ নেওয়া
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    // ✅ সব ছাত্রের তথ্য পড়া
    public static List<Map<String, String>> getAllStudents() {
        List<Map<String, String>> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Map<String, String> map = new HashMap<>();
                    map.put("username", parts[0]);
                    map.put("name", parts[1]);
                    map.put("batch", parts[2]);
                    map.put("department", parts[3]);
                    students.add(map);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    // ✅ Attendance mark করা হয়েছে কিনা চেক করা
    public static boolean isAttendanceMarked(String username, String course, String date) {
        try (BufferedReader br = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    if (parts[0].equals(username) && parts[1].equals(course) && parts[2].equals(date)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Attendance মার্ক করা
    public static boolean markStudentAttendance(String username, String course, String date, String status, String teacherUsername) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ATTENDANCE_FILE, true))) {
            bw.write(username + "," + course + "," + date + "," + status + "," + teacherUsername);
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ নির্দিষ্ট দিনের Attendance Status পাওয়া
    public static Map<String, String> getTodayAttendanceStatus(String course, String date) {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    if (parts[1].equals(course) && parts[2].equals(date)) {
                        map.put(parts[0], parts[3]); // username -> status
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    // ✅ নির্দিষ্ট ছাত্রের সব Attendance ইতিহাস (এইটাই CourseWiseAttendanceFrame-এ ব্যবহার হবে)
    public static List<String[]> getStudentAttendanceHistory(String username) {
        List<String[]> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[0].equals(username)) {
                    list.add(parts); // username, course, date, status, teacherUsername
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static String findUsernameByName(String name) {
    try (BufferedReader br = new BufferedReader(new FileReader(STUDENT_FILE))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 2 && parts[1].equalsIgnoreCase(name)) {
                return parts[0];
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
}
}
