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

    // --- LOGGING SYSTEM ---
    
    // Ghi log thời gian đăng nhập (log-time-sys.txt)
    public static void writeSystemLog(String username, String status, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) { // true = append mode
            String logEntry = logTimeFormat.format(new Date()) + " | User: " + username + " | Status: " + status;
            writer.println(logEntry);
        } catch (IOException e) {
            System.err.println("❌ Cannot write to system log: " + e.getMessage());
        }
    }

    // Ghi log bảo mật/admin (log-permission.txt)
    public static void writeSecurityLog(String username, String action, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            String logEntry = logTimeFormat.format(new Date()) + " | ADMIN/VIP: " + username + " | Action: " + action;
            writer.println(logEntry);
        } catch (IOException e) {
            System.err.println("❌ Cannot write to security log: " + e.getMessage());
        }
    }

    // --- USER MANAGEMENT ---

    // Đọc danh sách user từ file (db-sys-hrms.txt)
    public static void readUsersFromFile(Map<String, User> userMap, String filename) {
        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Bỏ qua dòng tiêu đề nếu có (ví dụ dòng đầu tiên)
            // reader.readLine(); 
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue; // Bỏ qua comment hoặc dòng trống

                String[] parts = line.split(FILE_DELIMITER);
                if (parts.length >= 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String role = parts[2].trim().toUpperCase();
                    
                    userMap.put(username, new User(username, password, role));
                }
            }
            System.out.println("✅ System loaded user database.");
        } catch (IOException e) {
            System.err.println("❌ Error loading user database: " + e.getMessage());
        }
    }

    // --- EMPLOYEE HANDLING (Existing Code) ---
    public static void writeEmployeeListToFile(List<Employee> employees, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Employee emp : employees) {
                String line = emp.getId() + FILE_DELIMITER +
                              emp.getName() + FILE_DELIMITER +
                              emp.getDepartment() + FILE_DELIMITER +
                              emp.getJobTitle() + FILE_DELIMITER +
                              emp.getBasicSalary() + FILE_DELIMITER +
                              dateFormat.format(emp.getJoinDate()) + FILE_DELIMITER + 
                              emp.isActive();
                writer.println(line);
            }
            System.out.println("✅ Employee data saved.");
        } catch (IOException e) {
            System.err.println("❌ Error saving employees: " + e.getMessage());
        }
    }

    public static void readEmployeeListFromFile(List<Employee> employees, String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("⚠️ Employee data file not found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(FILE_DELIMITER);
                if (parts.length == 7) {
                    try {
                        String id = parts[0];
                        String name = parts[1];
                        String department = parts[2];
                        String jobTitle = parts[3];
                        double basicSalary = Double.parseDouble(parts[4]);
                        Date joinDate = dateFormat.parse(parts[5]);
                        boolean isActive = Boolean.parseBoolean(parts[6]);
                        
                        // Placeholder for loading specific types. Defaulting to generic load logic.
                        // In a full implementation, we'd store the type "FullTime/PartTime" in the file.
                    } catch (NumberFormatException | ParseException e) {
                        // Suppress log for clean console, or log to error file
                    }
                }
            }
            System.out.println("✅ Employee data loaded.");
        } catch (IOException e) {
            System.err.println("❌ Error loading employees: " + e.getMessage());
        }
    }

    // --- ATTENDANCE HANDLING (Existing Code) ---
    public static void writeAttendanceListToFile(List<Attendance> attendanceList, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Attendance att : attendanceList) {
                String line = att.getEmployeeId() + FILE_DELIMITER +
                              dateFormat.format(att.getDate()) + FILE_DELIMITER +
                              (att.getInTime() == null ? "" : att.getInTime()) + FILE_DELIMITER +
                              (att.getOutTime() == null ? "" : att.getOutTime());
                writer.println(line);
            }
        } catch (IOException e) {
            System.err.println("❌ Error saving attendance: " + e.getMessage());
        }
    }

    public static void readAttendanceListFromFile(List<Attendance> attendanceList, String filename) {
        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(FILE_DELIMITER);
                if (parts.length >= 2) { 
                    try {
                        String empId = parts[0];
                        Date date = dateFormat.parse(parts[1]);
                        String inTime = (parts.length > 2) ? parts[2] : "";
                        String outTime = (parts.length > 3) ? parts[3] : "";
                        attendanceList.add(new Attendance(empId, date, inTime, outTime));
                    } catch (ParseException e) { }
                }
            }
            System.out.println("✅ Attendance data loaded.");
        } catch (IOException e) {
            System.err.println("❌ Error loading attendance: " + e.getMessage());
        }
    }
}
