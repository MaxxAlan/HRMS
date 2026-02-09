package hrms;

import hrms.models.*;
import hrms.utils.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class HRMS {
    private static List<Employee> employeeList = new ArrayList<>();
    private static List<Attendance> attendanceList = new ArrayList<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    
    private static final String DATA_DIR = "data/"; 
    private static final String EMPLOYEE_DATA_FILE = DATA_DIR + "employees.txt";
    private static final String ATTENDANCE_DATA_FILE = DATA_DIR + "attendance.txt";
    private static final String USER_DB_FILE = DATA_DIR + "db-sys-hrms.txt";
    private static final String LOG_TIME_FILE = DATA_DIR + "log-time-sys.txt";
    private static final String LOG_PERM_FILE = DATA_DIR + "log-permission.txt";

    private static Map<String, User> userDatabase = new HashMap<>();
    private static User loggedInUser = null; 

    static {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) directory.mkdirs();

        FileHandler.readUsersFromFile(userDatabase, USER_DB_FILE);
        if (userDatabase.isEmpty()) {
            userDatabase.put("group5", new User("group5", "0000", "IT"));
        }
        FileHandler.readEmployeeListFromFile(employeeList, EMPLOYEE_DATA_FILE);
        FileHandler.readAttendanceListFromFile(attendanceList, ATTENDANCE_DATA_FILE);
        
        // Link attendance to employees (Relationship logic)
        for (Attendance att : attendanceList) {
            for (Employee emp : employeeList) {
                if (emp.getId().equals(att.getEmployeeId())) {
                    emp.addAttendance(att);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        if (!login()) return;
        
        while (true) {
            ConsoleUI.displayHeader("HRMS SYSTEM | USER: " + loggedInUser.getUsername() + " | ROLE: " + loggedInUser.getRole());
            System.out.println("  [1] Employee Management");
            System.out.println("  [2] Attendance Management");
            System.out.println("  [3] Salary Management");
            System.out.println("  [4] Statistical Reports");
            System.out.println("  [5] System Logs (IT Only)");
            System.out.println("  [6] User Management (Admin Only)");
            System.out.println("  [7] Logout & Exit");
            
            String choice = ConsoleUI.promptForNonEmptyString("Select function: ");
            
            switch (choice) {
                case "1": if (checkPermission("HR")) employeeMenu(); break;
                case "2": attendanceMenu(); break;
                case "3": if (checkPermission("ACCOUNTANT")) salaryMenu(); break;
                case "4": if (checkPermission("DIRECTOR")) reportsMenu(); break;
                case "5": if (checkPermission("IT")) viewLogs(); break;
                case "6": if (checkPermission("IT")) userMenu(); break;
                case "7": saveAndExit(); return;
                default: ConsoleUI.displayError("Invalid selection.");
            }
        }
    }
    
    public static boolean login() {
        ConsoleUI.displayHeader("SYSTEM LOGIN");
        String username = ConsoleUI.promptForNonEmptyString("Username: ");
        String password = ConsoleUI.promptForNonEmptyString("Password: ");
        
        User user = userDatabase.get(username); 
        if (user != null && user.getPassword().equals(password)) {
            loggedInUser = user;
            FileHandler.writeSystemLog(username, "LOGIN", LOG_TIME_FILE);
            return true;
        } else {
            FileHandler.writeSystemLog(username, "FAIL", LOG_TIME_FILE);
            ConsoleUI.displayError("Invalid credentials.");
            return false;
        }
    }

    private static boolean checkPermission(String requiredRole) {
        String role = loggedInUser.getRole();
        if (role.equals("IT") || role.equals("DIRECTOR")) return true;
        if (role.equals(requiredRole)) return true;
        ConsoleUI.displayError("Permission denied.");
        return false;
    }

    // --- 1. EMPLOYEE MANAGEMENT ---
    public static void employeeMenu() {
        while (true) {
            ConsoleUI.displayHeader("EMPLOYEE MANAGEMENT");
            System.out.println("  1. View List");
            System.out.println("  2. Add New Employee");
            System.out.println("  3. Update Employee");
            System.out.println("  4. Deactivate Employee");
            System.out.println("  5. Search Employee");
            System.out.println("  6. Back");
            String choice = ConsoleUI.promptForNonEmptyString("Select: ");
            switch (choice) {
                case "1": viewAllEmployees(); break;
                case "2": addEmployee(); break;
                case "3": updateEmployee(); break;
                case "4": deactivateEmployee(); break;
                case "5": searchEmployee(); break;
                case "6": return;
            }
        }
    }

    public static void viewAllEmployees() {
        ConsoleUI.displayHeader("EMPLOYEE LIST");
        System.out.printf("  %-10s | %-20s | %-15s | %-10s%n", "ID", "Full Name", "Dept", "Active");
        System.out.println("  -----------+----------------------+-----------------+-----------");
        for (Employee emp : employeeList) {
            System.out.printf("  %-10s | %-20s | %-15s | %-10s%n", emp.getId(), emp.getName(), emp.getDepartment(), emp.isActive());
        }
        ConsoleUI.pause();
    }

    public static void addEmployee() {
        String id = ConsoleUI.promptForNonEmptyString("ID: ");
        String name = ConsoleUI.promptForNonEmptyString("Name: ");
        String dept = ConsoleUI.promptForNonEmptyString("Dept: ");
        String title = ConsoleUI.promptForNonEmptyString("Job Title: ");
        String type = ConsoleUI.promptForNonEmptyString("Type (Full/Part): ");
        double salary = 1000.0;
        try { salary = Double.parseDouble(ConsoleUI.promptForNonEmptyString("Basic Salary: ")); } catch(Exception e) {}
        
        Employee newEmp;
        if (type.equalsIgnoreCase("Part")) newEmp = new PartTimeEmployee(id, name, dept, title, new Date(), salary, true);
        else newEmp = new FullTimeEmployee(id, name, dept, title, new Date(), salary, true);
        
        employeeList.add(newEmp);
        ConsoleUI.displaySuccess("Added: " + name);
    }

    private static void updateEmployee() {
        String id = ConsoleUI.promptForNonEmptyString("Enter ID to update: ");
        for (Employee e : employeeList) {
            if (e.getId().equals(id)) {
                e.setDepartment(ConsoleUI.promptForNonEmptyString("New Dept: "));
                e.setJobTitle(ConsoleUI.promptForNonEmptyString("New Title: "));
                ConsoleUI.displaySuccess("Updated.");
                return;
            }
        }
        ConsoleUI.displayError("Not found.");
    }

    private static void deactivateEmployee() {
        String id = ConsoleUI.promptForNonEmptyString("Enter ID to deactivate: ");
        for (Employee e : employeeList) {
            if (e.getId().equals(id)) {
                e.setActive(false);
                ConsoleUI.displaySuccess("Deactivated.");
                return;
            }
        }
        ConsoleUI.displayError("Not found.");
    }

    private static void searchEmployee() {
        String key = ConsoleUI.promptForNonEmptyString("Search Name: ").toLowerCase();
        for (Employee e : employeeList) {
            if (e.getName().toLowerCase().contains(key)) 
                System.out.println("  " + e.getId() + " | " + e.getName() + " | " + e.getDepartment());
        }
        ConsoleUI.pause();
    }

    // --- 2. ATTENDANCE MANAGEMENT ---
    public static void attendanceMenu() {
        while (true) {
            ConsoleUI.displayHeader("ATTENDANCE MANAGEMENT");
            System.out.println("  1. Check-in (Entry)");
            System.out.println("  2. Manual Check-out");
            System.out.println("  3. Quick Check-out List");
            System.out.println("  4. View My History");
            System.out.println("  5. Back");
            String choice = ConsoleUI.promptForNonEmptyString("Select: ");
            switch (choice) {
                case "1": handleCheckIn(); break;
                case "2": handleCheckOut(); break;
                case "3": quickCheckOutList(); break;
                case "4": viewMyAttendance(); break;
                case "5": return;
            }
        }
    }

    private static void handleCheckIn() {
        String id = ConsoleUI.promptForNonEmptyString("Employee ID: ");
        Attendance att = new Attendance(id, new Date(), "Present", 0.0, timeFormat.format(new Date()), "");
        attendanceList.add(att);
        for (Employee e : employeeList) if (e.getId().equals(id)) e.addAttendance(att);
        ConsoleUI.displaySuccess("Check-in success.");
    }

    private static void handleCheckOut() {
        String id = ConsoleUI.promptForNonEmptyString("Employee ID: ");
        processCheckOut(id);
    }

    private static void quickCheckOutList() {
        while (true) {
            ConsoleUI.displayHeader("ACTIVE WORKERS (ON-SITE)");
            String today = dateFormat.format(new Date());
            List<String> onSiteIds = new ArrayList<>();
            for (Attendance att : attendanceList) {
                if (dateFormat.format(att.getDate()).equals(today) && att.getOutTime().isEmpty()) onSiteIds.add(att.getEmployeeId());
            }
            if (onSiteIds.isEmpty()) { System.out.println("  [!] No workers currently on-site."); ConsoleUI.pause(); return; }
            for (String id : onSiteIds) System.out.println("  [ ] ID: " + id);
            String targetId = ConsoleUI.promptForNonEmptyString("Enter ID to Check-out (or 'back'): ");
            if (targetId.equalsIgnoreCase("back")) return;
            if (onSiteIds.contains(targetId)) processCheckOut(targetId);
            else ConsoleUI.displayError("Invalid ID.");
        }
    }

    private static void processCheckOut(String id) {
        String today = dateFormat.format(new Date());
        for (Attendance att : attendanceList) {
            if (att.getEmployeeId().equals(id) && dateFormat.format(att.getDate()).equals(today) && att.getOutTime().isEmpty()) {
                att.setOutTime(timeFormat.format(new Date()));
                ConsoleUI.displaySuccess("Check-out success.");
                return;
            }
        }
        ConsoleUI.displayError("No active record found.");
    }

    private static void viewMyAttendance() {
        String id = ConsoleUI.promptForNonEmptyString("Enter ID: ");
        for (Attendance att : attendanceList) {
            if (att.getEmployeeId().equals(id))
                System.out.println("  " + dateFormat.format(att.getDate()) + " | " + att.getInTime() + " - " + att.getOutTime());
        }
        ConsoleUI.pause();
    }

    // --- 3. SALARY MANAGEMENT ---
    public static void salaryMenu() {
        ConsoleUI.displayHeader("SALARY CALCULATION (MONTHLY)");
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        System.out.printf("  %-10s | %-20s | %-15s%n", "ID", "Name", "Total Salary");
        System.out.println("  -----------+----------------------+----------------");
        for (Employee emp : employeeList) {
            if (emp.isActive()) {
                double total = emp.calculateSalary(month, year);
                System.out.printf("  %-10s | %-20s | $ %-15.2f%n", emp.getId(), emp.getName(), total);
            }
        }
        ConsoleUI.pause();
    }

    // --- 4. REPORTS ---
    public static void reportsMenu() {
        ConsoleUI.displayHeader("STATISTICAL REPORTS");
        System.out.println("  Total Employees: " + employeeList.size());
        int active = 0; for(Employee e : employeeList) if(e.isActive()) active++;
        System.out.println("  Active Employees: " + active);
        System.out.println("  Total Attendance Logs: " + attendanceList.size());
        ConsoleUI.pause();
    }

    // --- 5. LOGS ---
    private static void viewLogs() {
        ConsoleUI.displayHeader("SYSTEM LOGS");
        System.out.println("  1. Login Logs  2. Security Logs  3. Back");
        String choice = ConsoleUI.promptForNonEmptyString("Select: ");
        if (choice.equals("1")) printFile(LOG_TIME_FILE);
        else if (choice.equals("2")) printFile(LOG_PERM_FILE);
    }

    private static void printFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) System.out.println("  " + line);
        } catch (Exception e) { System.out.println("  Empty or error."); }
        ConsoleUI.pause();
    }

    // --- 6. USER MANAGEMENT ---
    public static void userMenu() {
        while (true) {
            ConsoleUI.displayHeader("USER MANAGEMENT");
            System.out.println("  1. View Users  2. Add New User  3. Back");
            String choice = ConsoleUI.promptForNonEmptyString("Select: ");
            if (choice.equals("1")) {
                for (User u : userDatabase.values()) System.out.printf("  %-15s | %-10s%n", u.getUsername(), u.getRole());
                ConsoleUI.pause();
            } else if (choice.equals("2")) {
                String u = ConsoleUI.promptForNonEmptyString("Username: ");
                String p = ConsoleUI.promptForNonEmptyString("Password: ");
                String r = ConsoleUI.promptForNonEmptyString("Role: ").toUpperCase();
                userDatabase.put(u, new User(u, p, r));
                ConsoleUI.displaySuccess("User added.");
            } else if (choice.equals("3")) return;
        }
    }

    private static void saveAndExit() {
        FileHandler.writeEmployeeListToFile(employeeList, EMPLOYEE_DATA_FILE);
        FileHandler.writeAttendanceListToFile(attendanceList, ATTENDANCE_DATA_FILE);
        FileHandler.writeUsersToFile(userDatabase, USER_DB_FILE);
        System.out.println("\n  [*] All data synchronized. Goodbye!");
    }
}