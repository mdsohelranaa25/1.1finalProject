// FileManager.java
import java.io.*;
import java.util.*;

public class FileManager {
    private static final String STUDENT_FILE = "students.txt";
    private static final String TEACHER_FILE = "teachers.txt";
    
    /**
     * Save student information to file
     */
    public static boolean saveStudent(String username, String password, String name, 
                                     String batch, String department) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_FILE, true))) {
            writer.println(username + "," + password + "," + name + "," + batch + "," + department);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Save teacher information to file
     */
    public static boolean saveTeacher(String username, String password, String name, 
                                     String subject, String department) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEACHER_FILE, true))) {
            writer.println(username + "," + password + "," + name + "," + subject + "," + department);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Find and authenticate student
     */
    public static Map<String, String> findStudent(String username, String password) {
        return findUser(STUDENT_FILE, username, password);
    }
    
    /**
     * Find and authenticate teacher
     */
    public static Map<String, String> findTeacher(String username, String password) {
        return findUser(TEACHER_FILE, username, password);
    }
    
    /**
     * Generic method to find user in file
     */
    private static Map<String, String> findUser(String filename, String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5 && data[0].equals(username) && data[1].equals(password)) {
                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("username", data[0]);
                    userInfo.put("password", data[1]);
                    userInfo.put("name", data[2]);
                    userInfo.put("field1", data[3]); // Batch/Subject
                    userInfo.put("field2", data[4]); // Department
                    return userInfo;
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Check if username already exists
     */
    public static boolean usernameExists(String filename, String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[0].equals(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Get all students from file
     */
    public static List<Map<String, String>> getAllStudents() {
        return getAllUsers(STUDENT_FILE);
    }
    
    /**
     * Get all teachers from file
     */
    public static List<Map<String, String>> getAllTeachers() {
        return getAllUsers(TEACHER_FILE);
    }
    
    /**
     * Generic method to get all users
     */
    private static List<Map<String, String>> getAllUsers(String filename) {
        List<Map<String, String>> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    Map<String, String> user = new HashMap<>();
                    user.put("username", data[0]);
                    user.put("password", data[1]);
                    user.put("name", data[2]);
                    user.put("field1", data[3]);
                    user.put("field2", data[4]);
                    users.add(user);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}