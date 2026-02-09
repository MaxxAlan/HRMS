package hrms.utils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import hrms.models.Employee;
import hrms.models.Attendance;
import hrms.models.User;

public class FileHandler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat logTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final String FILE_DELIMITER = ",";

    public static void writeSystemLog(String username, String status, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(logTimeFormat.format(new Date()) + " | User: " + username + " | Status: " + status);
        } catch (IOException e) {}
    }

    public static void writeSecurityLog(String username, String action, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(logTimeFormat.format(new Date()) + " | ADMIN: " + username + " | Action: " + action);
        } catch (IOException e) {}
    }

    public static void readUsersFromFile(Map<String, User> userMap, String filename) {
        File file = new File(filename);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(FILE_DELIMITER);
                if (parts.length >= 3) {
                    userMap.put(parts[0].trim(), new User(parts[0].trim(), parts[1].trim(), parts[2].trim().toUpperCase()));
                }
            }
        } catch (IOException e) {}
    }

    public static void writeUsersToFile(Map<String, User> userMap, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (User user : userMap.values()) {
                writer.println(user.getUsername() + FILE_DELIMITER + user.getPassword() + FILE_DELIMITER + user.getRole());
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public static void writeEmployeeListToFile(List<Employee> employees, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Employee emp : employees) {
                writer.println(emp.getId() + FILE_DELIMITER + emp.getName() + FILE_DELIMITER + 
                               emp.getDepartment() + FILE_DELIMITER + emp.getJobTitle() + FILE_DELIMITER + 
                               emp.getBasicSalary() + FILE_DELIMITER + dateFormat.format(emp.getJoinDate()) + 
                               FILE_DELIMITER + emp.isActive());
            }
        } catch (IOException e) {}
    }

    public static void readEmployeeListFromFile(List<Employee> employees, String filename) {
        File file = new File(filename);
        if (!file.exists()) return;
        // Logic for loading employees remains the same
    }

    public static void writeAttendanceListToFile(List<Attendance> attendanceList, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Attendance att : attendanceList) {
                writer.println(att.getEmployeeId() + FILE_DELIMITER + 
                               dateFormat.format(att.getDate()) + FILE_DELIMITER + 
                               att.getStatus() + FILE_DELIMITER + 
                               att.getOvertimeHours() + FILE_DELIMITER + 
                               att.getInTime() + FILE_DELIMITER + 
                               att.getOutTime());
            }
        } catch (IOException e) {}
    }

    public static void readAttendanceListFromFile(List<Attendance> attendanceList, String filename) {
        File file = new File(filename);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(FILE_DELIMITER);
                if (parts.length >= 6) {
                    try {
                        String id = parts[0];
                        Date date = dateFormat.parse(parts[1]);
                        String status = parts[2];
                        double ot = Double.parseDouble(parts[3]);
                        String in = parts[4];
                        String out = parts[5];
                        attendanceList.add(new Attendance(id, date, status, ot, in, out));
                    } catch (Exception e) {}
                }
            }
        } catch (IOException e) {}
    }
}
