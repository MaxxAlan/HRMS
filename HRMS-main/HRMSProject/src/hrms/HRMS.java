package hrms;

import hrms.models.Employee;
import hrms.models.FullTimeEmployee;
import hrms.models.PartTimeEmployee;
import hrms.models.User;
import hrms.models.Attendance;
import hrms.utils.FileHandler;
import hrms.utils.ConsoleUI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class HRMS {
    private static List<Employee> employeeList = new ArrayList<>();
    private static List<Attendance> attendanceList = new ArrayList<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    
    private static final String EMPLOYEE_DATA_FILE = "src/data/employees.txt";
    private static final String ATTENDANCE_DATA_FILE = "src/data/attendance.txt";
    private static final String USER_DB_FILE = "src/data/db-sys-hrms.txt";
    private static final String LOG_TIME_FILE = "src/data/log-time-sys.txt";
    private static final String LOG_PERM_FILE = "src/data/log-permission.txt";

    private static Map<String, User> userDatabase = new HashMap<>();
    private static User loggedInUser = null; 

    static {
        FileHandler.readUsersFromFile(userDatabase, USER_DB_FILE);
        if (userDatabase.isEmpty()) {
            userDatabase.put("group5", new User("group5", "0000", "IT"));
        }
        FileHandler.readEmployeeListFromFile(employeeList, EMPLOYEE_DATA_FILE);
        FileHandler.readAttendanceListFromFile(attendanceList, ATTENDANCE_DATA_FILE);
    }

    public static void main(String[] args) {
        displaySystemHeader("HRMS ENTERPRISE SYSTEM");
        
        if (!login()) {
            System.out.println("⛔ Access Denied.");
            return;
        }
        
        while (true) {
            displaySystemHeader("MAIN DASHBOARD | " + loggedInUser.getRole());
            System.out.println("  1. Employee Management");
            System.out.println("  2. Attendance Management");
            System.out.println("  3. Salary Management");
            System.out.println("  4. Reports");
            System.out.println("  5. View System Logs (IT Only)");
            System.out.println("  6. Logout & Exit");
            
            String choice = ConsoleUI.promptForNonEmptyString("Select module: ");
            
            switch (choice) {
                case "1": 
                    if (checkPermission("IT", "DIRECTOR", "HR")) employeeMenu();
                    else ConsoleUI.displayError("Permission Denied.");
                    break;
                case "2": attendanceMenu(); break;
                case "3": 
                    if (checkPermission("IT", "DIRECTOR", "ACCOUNTANT")) salaryMenu();
                    else ConsoleUI.displayError("Permission Denied.");
                    break;
                case "4": 
                    if (checkPermission("IT", "DIRECTOR")) reportsMenu();
                    else ConsoleUI.displayError("Permission Denied.");
                    break;
                case "5":
                    if (checkPermission("IT")) viewLogs();
                    else ConsoleUI.displayError("IT Personnel Only.");
                    break;
                case "6": 
                    saveAndExit();
                    return;
                default: ConsoleUI.displayError("Invalid selection.");
            }
        }
    }
    
    public static boolean login() {
        System.out.println("\n--- SYSTEM LOGIN ---");
        String username = ConsoleUI.promptForNonEmptyString("Username: ");
        String password = ConsoleUI.promptForNonEmptyString("Password: ");
        
        User user = userDatabase.get(username); 
        if (user != null && user.getPassword().equals(password)) {
            loggedInUser = user;
            FileHandler.writeSystemLog(username, "LOGIN", LOG_TIME_FILE);
            ConsoleUI.displaySuccess("Welcome, " + username);
            return true;
        } else {
            FileHandler.writeSystemLog(username, "FAIL", LOG_TIME_FILE);
            ConsoleUI.displayError("Invalid credentials.");
            return false;
        }
    }

    private static void viewLogs() {
        displaySystemHeader("SYSTEM LOG VIEWER");
        System.out.println("1. View System Logs (Login/Logout)");
        System.out.println("2. View Security Logs (Admin Actions)");
        System.out.println("3. Back");
        String choice = ConsoleUI.promptForNonEmptyString("Choose log type: ");
        
        if (choice.equals("1")) printFileContent(LOG_TIME_FILE);
        else if (choice.equals("2")) printFileContent(LOG_PERM_FILE);
    }

    private static void printFileContent(String filePath) {
        System.out.println("\n--- Content of " + filePath + " ---");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("No logs found or error reading file.");
        }
        ConsoleUI.pause();
    }
    
    private static boolean checkPermission(String... allowedRoles) {
        if (loggedInUser == null) return false;
        String role = loggedInUser.getRole();
        if (role.equals("IT") || role.equals("DIRECTOR")) return true;
        for (String r : allowedRoles) { if (role.equals(r)) return true; }
        return false;
    }

    private static void saveAndExit() {
        FileHandler.writeEmployeeListToFile(employeeList, EMPLOYEE_DATA_FILE); 
        FileHandler.writeAttendanceListToFile(attendanceList, ATTENDANCE_DATA_FILE);
        System.out.println("Goodbye!");
    }
    
    public static void employeeMenu(){
        while (true) {            
            displaySystemHeader("EMPLOYEE MANAGEMENT"); 
            System.out.println("  1. Add Employee  2. View All  3. Back");
            String choice = ConsoleUI.promptForNonEmptyString("Choose: ");
            if (choice.equals("1")) addEmployee();
            else if (choice.equals("2")) viewAllEmployees();
            else if (choice.equals("3")) return;
        }
    }
    
    public static void addEmployee(){
        ConsoleUI.displayHeader("ADD EMPLOYEE");
        String id = ConsoleUI.promptForNonEmptyString("ID: ");
        String name = ConsoleUI.promptForNonEmptyString("Name: ");
        Date joinDate = new Date(); // Default today for demo
        Employee newEmp = new FullTimeEmployee(id, name, "General", "Staff", joinDate, 1000, true);
        employeeList.add(newEmp);
        ConsoleUI.displaySuccess("Employee added locally.");
    }
    
    public static void viewAllEmployees(){
        System.out.printf("%-8s | %-20s%n", "ID", "Name");
        System.out.println("----------------------------");
        for (Employee emp : employeeList) {
            if(emp.isActive()) System.out.printf("%-8s | %-20s%n", emp.getId(), emp.getName());
        }
        ConsoleUI.pause();
    }

    public static void attendanceMenu() {
        while (true) {
            displaySystemHeader("ATTENDANCE");
            System.out.println("  1. Check-in  2. Check-out  3. Back");
            String choice = ConsoleUI.promptForNonEmptyString("Choose: ");
            if (choice.equals("1")) handleCheckIn();
            else if (choice.equals("2")) handleCheckOut();
            else if (choice.equals("3")) return;
        }
    }
    
    public static void handleCheckIn() {
        String empId = ConsoleUI.promptForNonEmptyString("Emp ID: ");
        Date now = new Date();
        attendanceList.add(new Attendance(empId, now, timeFormat.format(now), ""));
        ConsoleUI.displaySuccess("Check-in recorded.");
    }

    public static void handleCheckOut() {
        ConsoleUI.displaySuccess("Check-out recorded.");
    }

    public static void salaryMenu() {
        System.out.println("Salary module accessed.");
        ConsoleUI.pause();
    }

    public static void reportsMenu() {
        System.out.println("Reports module accessed.");
        ConsoleUI.pause();
    }

    public static void displaySystemHeader(String title) {
        System.out.println("\n########################################");
        System.out.println("# " + title);
        System.out.println("########################################");
    }
}