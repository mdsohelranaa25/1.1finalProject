// NotificationManager.java - Complete File Handling for Notifications
import java.io.*;
import java.util.*;

public class NotificationManager {
    // File names for storing data
    private static final String NOTIFICATIONS_FILE = "notifications.txt";
    private static final String ALARMS_FILE = "alarms.txt";
    
    /**
     * Save a notification to file
     * Format: username,title,message,type,datetime,timestamp
     * Example: student123,Exam Alert,Mid-term exam tomorrow,exam,08-11-2024 10:30,1699438200000
     */
    public static boolean saveNotification(String username, String title, String message, 
                                          String type, String datetime) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOTIFICATIONS_FILE, true))) {
            // true means append to file, not overwrite
            writer.println(username + "," + title + "," + message + "," + 
                          type + "," + datetime + "," + System.currentTimeMillis());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Save an alarm to file
     * Format: username,title,message,date,time,repeat,repeatType,timestamp,status
     * Example: student123,Class Reminder,CSE 101,09-11-2024,09:00 AM,true,Daily,1699438200000,active
     */
    public static boolean saveAlarm(String username, String title, String message,
                                   String date, String time, boolean repeat, String repeatType) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ALARMS_FILE, true))) {
            writer.println(username + "," + title + "," + message + "," + 
                          date + "," + time + "," + repeat + "," + repeatType + "," + 
                          System.currentTimeMillis() + ",active");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all notifications for a specific user
     * Returns a list of maps, each map contains notification data
     */
    public static List<Map<String, String>> getNotifications(String username) {
        List<Map<String, String>> notifications = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(NOTIFICATIONS_FILE))) {
            String line;
            // Read file line by line
            while ((line = reader.readLine()) != null) {
                // Split by comma: username,title,message,type,datetime,timestamp
                String[] data = line.split(",");
                
                // Check if line has enough data and username matches
                if (data.length >= 6 && data[0].equals(username)) {
                    // Create a map to store notification data
                    Map<String, String> notif = new HashMap<>();
                    notif.put("username", data[0]);   // student123
                    notif.put("title", data[1]);       // Exam Alert
                    notif.put("message", data[2]);     // Mid-term exam tomorrow
                    notif.put("type", data[3]);        // exam
                    notif.put("datetime", data[4]);    // 08-11-2024 10:30
                    notif.put("timestamp", data[5]);   // 1699438200000
                    
                    // Add to list
                    notifications.add(notif);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet - this is okay for first run
            System.out.println("Notifications file not found. Will be created when first notification is saved.");
        } catch (IOException e) {
            System.err.println("Error reading notifications: " + e.getMessage());
            e.printStackTrace();
        }
        
        return notifications;
    }
    
    /**
     * Get all alarms for a specific user
     * Returns a list of maps, each map contains alarm data
     */
    public static List<Map<String, String>> getAlarms(String username) {
        List<Map<String, String>> alarms = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ALARMS_FILE))) {
            String line;
            // Read file line by line
            while ((line = reader.readLine()) != null) {
                // Split by comma: username,title,message,date,time,repeat,repeatType,timestamp,status
                String[] data = line.split(",");
                
                // Check if line has enough data and username matches
                if (data.length >= 9 && data[0].equals(username)) {
                    // Create a map to store alarm data
                    Map<String, String> alarm = new HashMap<>();
                    alarm.put("username", data[0]);      // student123
                    alarm.put("title", data[1]);         // Class Reminder
                    alarm.put("message", data[2]);       // CSE 101 in Room 301
                    alarm.put("date", data[3]);          // 09-11-2024
                    alarm.put("time", data[4]);          // 09:00 AM
                    alarm.put("repeat", data[5]);        // true
                    alarm.put("repeatType", data[6]);    // Daily
                    alarm.put("timestamp", data[7]);     // 1699438200000
                    alarm.put("status", data[8]);        // active
                    
                    // Add to list
                    alarms.add(alarm);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet - this is okay for first run
            System.out.println("Alarms file not found. Will be created when first alarm is saved.");
        } catch (IOException e) {
            System.err.println("Error reading alarms: " + e.getMessage());
            e.printStackTrace();
        }
        
        return alarms;
    }
    
    /**
     * Delete an alarm by username and timestamp
     * Reads all lines, skips the one to delete, rewrites file
     */
    public static boolean deleteAlarm(String username, String timestamp) {
        List<String> lines = new ArrayList<>();
        boolean found = false;
        
        // Step 1: Read all lines except the one to delete
        try (BufferedReader reader = new BufferedReader(new FileReader(ALARMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                
                // If this is the alarm to delete, skip it
                if (data.length >= 8 && data[0].equals(username) && data[7].equals(timestamp)) {
                    found = true;
                    continue; // Skip this line
                }
                
                // Keep all other lines
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading alarms for deletion: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        
        // Step 2: If alarm was found, rewrite file without it
        if (found) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(ALARMS_FILE))) {
                for (String line : lines) {
                    writer.println(line);
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error rewriting alarms file: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * Get total count of notifications for a user
     */
    public static int getNotificationCount(String username) {
        return getNotifications(username).size();
    }
    
    /**
     * Get total count of alarms for a user
     */
    public static int getAlarmCount(String username) {
        return getAlarms(username).size();
    }
    
    /**
     * Delete all notifications for a user
     */
    public static boolean deleteAllNotifications(String username) {
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(NOTIFICATIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Keep only notifications that don't belong to this user
                if (data.length >= 1 && !data[0].equals(username)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOTIFICATIONS_FILE))) {
            for (String line : lines) {
                writer.println(line);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}