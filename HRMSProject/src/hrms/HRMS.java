
package hrms;

import hrms.models.*;
import hrms.utils.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;


public class HRMS { 
    private static List<Employee> employeeList = new ArrayList<>();
    private static Map<String, Employee> employeeMap = new HashMap<>();
    private static Set<String> usedIds = new HashSet<>();
    private static Scanner sc = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public static void main(String[] args) {
        System.out.println("HRMS System");
        loadData();
        
        while (true) {            
            System.out.println("\n===Main Menu===");
            System.out.println("1. Manage Employees");
            System.out.println("2. Attendance Management");
            System.out.println("3. Salary Management");
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            System.out.print("Select ur number: ");
            
            String choice = sc.nextLine().trim();
            
            switch (choice) {
                case "1": 
                    employeeMenu(); 
                    break;
                case "2": 
                    attendanceMenu(); 
                    break;
                case "3": 
                    salaryMenu(); 
                    break;    
                case "4": 
                    reportMenu(); 
                    break;    
                case "5": 
                    saveData();
                    System.out.println("End");
                    return;
                default: 
                    System.out.println("Invalid choose(1-5)");
            }
           
        }
    }
    
    // ================================================================
    // PHẦN FILE I/O
    // ================================================================
    
    // Gọi FileManager để load dữ liệu khi khởi động
    private static void loadData(){
        try {
            // Bước 1: Load danh sách nhân viên từ file employees.csv
            employeeList = FileManager.loadEmployees();
            
            // Bước 2: Rebuild Map và Set từ List vừa load
            // (Map và Set bị mất khi tắt máy, phải tạo lại từ List)
            for (Employee emp : employeeList) {
                employeeMap.put(emp.getId(), emp);// thêm vào Map để tìm nhanh
                usedIds.add(emp.getId());                // thêm vào Set để kiểm tra trùng
            }
            
            // Bước 3: Load lịch sử chấm công từ file attendance.csv
            FileManager.loadAttendance(employeeList);
            
        } catch (Exception e) {
            System.out.println("[ERROR] Can't load the file" + e.getMessage());
        }
    }
    // Gọi FileManager để lưu dữ liệu (gọi khi thoát chương trình)
    private static void saveData(){
        try {
            FileManager.saveEmployees(employeeList);
            FileManager.saveAttendance(employeeList);
            System.out.println("Save info success");
        } catch (Exception e) {
            System.out.println("[ERROR] Can't save the info" + e.getMessage());
        }
    }
    
    // ================================================================
    // MENU MANGE EMPLOYEES
    // ================================================================
    public static void employeeMenu(){
        while (true) {            
            System.out.println("\n=== EMPLOYEE MANAGEMENT ===");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Remove Employee");
            System.out.println("4. View all Employees");
            System.out.println("5. Search Employees");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose (1-6): ");
            
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1": 
                    addEmployee(); 
                    break;
                case "2": 
                    updateEmployee(); 
                    break;
                case "3": 
                    removeEmployee(); 
                    break;
                case "4": 
                    viewAllEmployees(); 
                    break;
                case "5": 
                    searchEmployee(); 
                    break;
                case "6": 
                    return; 
                default:
                    System.out.println("Invalid choose again(1-6)");
            }
        }
    }
    
    //1.1 Add employee
    public static void addEmployee(){
        System.out.println("\n--- ADD EMPLOYEE ---");
        
        //Enter id
        System.out.print("Employee ID: ");
        String id = sc.nextLine().trim();
        //Id ko đc bỏ
        if(!Validator.isValidId(id)){
            // Validator.isValidId trả về false → ID rỗng → thông báo lỗi và thoát
            System.out.println("ID can't be empty");
            return;
        }
       //Check id có trùng ko
       if(usedIds.contains(id)){
           System.out.println("ID: " + id + "existed");
           return;
       }
       
        //Enter name
        System.out.print("Enter name: ");
        String name = sc.nextLine().trim();
        if(!Validator.isValidName(name)){
            System.out.println("Name can't be empty");
            return;
        }
        
        //Enter department
        System.out.print("Department: ");
        String department = sc.nextLine().trim();
        if(!Validator.isValidDepartment(department)){
            System.out.println("Department can't be empty");
            return;
        }
        
        //Enter job title
        System.out.print("Job Title: ");
        String jobTitle = sc.nextLine().trim();
        if(!Validator.isValidJobTitle(jobTitle)){
            System.out.println("Job title can't be empty");
            return;
        }
        
        //Enter type job
        System.out.print("Type (Full-time / Part-time): ");
        String type = sc.nextLine().trim();
        if(!type.equalsIgnoreCase("Full-time") && !type.equalsIgnoreCase("Part-time")){
            System.out.println("Type should be(Full-time / Part-time)");
            return;
        }
        
        //Enter date join
        System.out.print("Date join(dd/MM/yyyy): ");
        String dateStr = sc.nextLine().trim();
        Date jointDate;
        try {
            // setLenient(false): bắt buộc ngày phải hợp lệ thật
            // VD: setLenient(true) thì 32/01/2025 → tự hiểu là 01/02/2025
            //     setLenient(false) thì 32/01/2025 → ném Exception
            dateFormat.setLenient(false);
            jointDate = dateFormat.parse(dateStr);// chuyển chuỗi → Date
        } catch (Exception e) {
            System.out.println("Invalid date format dd/MM/yyyy (12/02/2000)");
            return;
        }
        
        //Enter salary
        System.out.print("Basic salary: ");
        String salaryStr = sc.nextLine().trim();
        if(!Validator.isPositiveDouble(salaryStr)){
            System.out.println("Salary should be positive");
            return;
        }
        double basicSalary = Double.parseDouble(salaryStr);;
        
        //Save or cancel
        System.out.println("ID: " + id + "|Name: " +name+ "|Department: " +department );
        System.out.println("Type: " +type+ "|Salary: " +String.format("%,.0f", basicSalary));
        System.out.println("[1]Save     [2]Cancel");
        System.out.print("Choose number(1-2): ");
        String saveChoice = sc.nextLine().trim();
        
        if(saveChoice.equals("1")){
            Employee newEmp;
            if(type.equalsIgnoreCase("Full-time")){
                newEmp = new FullTimeEmployee(id, name, department, jobTitle, jointDate, basicSalary, true);
            }else{
                newEmp = new PartTimeEmployee(id, name, department, jobTitle, jointDate, basicSalary, true);
            }
            
            // Thêm vào cả 3 collection để đồng bộ:
            employeeList.add(newEmp);           // List: giữ thứ tự, dùng khi cần loop
            employeeMap.put(id, newEmp); // Map: tìm nhanh theo ID
            usedIds.add(id);                    // Set: kiểm tra trùng ID
            
            //Auto-save
            try {
                FileManager.saveEmployees(employeeList);
            } catch (Exception e) {
                System.out.println("[WARN] Can't auto-save" + e.getMessage());
            }
            System.out.println("Save success");
        }else{
            System.out.println("Cancle");
        }
        
    }
    
    //1.2 Update info Employee
    public static void updateEmployee(){
        System.out.println("\n--- ADD EMPLOYEE ---");
        System.out.println("Enter id to update: ");
        String id = sc.nextLine().trim();
        
        //Find id
        Employee emp = employeeMap.get(id);
        if(emp == null || !emp.isActive()){
            System.out.println("Employee ID not found");
            return;
        }
        
        //Show current info
        System.out.println("Show info");
        System.out.println("Name: " +emp.getName());
        System.out.println("Department: " +emp.getDepartment());
        System.out.println("Job title: " +emp.getJobTitle());
        System.out.printf("Basic salary: %,.0f VND" +emp.getBasicSalary());
        
        //Input new info
        System.out.print("New department (leave bleank to skip): ");
        String newDepartment = sc.nextLine().trim();
        if(!newDepartment.isEmpty()){
            emp.setDepartment(newDepartment);
        }
        
        System.out.print("New Job title (leave blank to skip): ");
        String newJobTitle = sc.nextLine().trim();
        if(!newJobTitle.isEmpty()){
            emp.setJobTitle(newJobTitle);
        }
        
        System.out.print("New salary(leave blank to skip): ");
        String newSalary = sc.nextLine().trim();
        if(!newSalary.isEmpty()){
            if(Validator.isPositiveDouble(newSalary)){
                emp.setBasicSalary(Double.parseDouble(newSalary));
            }else{
                System.out.println("Invalid salary, skip");
            }
        }
        
        //Save and Cancel
        System.out.println("\n[1] Update   [2] Cancel");
        System.out.print("Choose number: ");
        String updateChoice = sc.nextLine().trim();
        if(updateChoice.equals("1")){
            try {
                FileManager.saveEmployees(employeeList);              
            } catch (Exception e) {
                System.out.println("[WARN] Can't save: " + e.getMessage());
            }
            System.out.println("Update success");           
        }else{
            System.out.println("Cancel");
        }
        
    }
    
    //1.3 Remove info Employee
    public static void removeEmployee(){
        System.out.println("\n===REMOVE EMPLOYEE===");
        System.out.print("Enter id to remove: ");
        String id = sc.nextLine().trim();
        
        //Search employee
        Employee emp = employeeMap.get(id); //Dùng map dò id employee
        if(emp==null || !emp.isActive()){
            System.out.println("Employee ID not found");
            return;
        }
        
        //Confirm
        System.out.print("You sure remove " +emp.getName()+"? (Y/N): ");
        String confirm = sc.nextLine().trim();
        if(confirm.equalsIgnoreCase("Y")){  //equalIgnoreCase ghi thường hay hoa đều ko sao
            emp.setActive(false);   //// Không xóa khỏi List/Map, chỉ deactivate
            
            //employeeList.remove(emp); Nếu muốn xóa hoàn toàn
            
            try {
                FileManager.saveEmployees(employeeList);
            } catch (Exception e) {
                System.out.println("[WARN] Can't be save" + e.getMessage());
            }
            System.out.println("Remove successfully");
        }else{
            System.out.println("Cancel");
        }
        
    }
    
    //1.4 Show all Employee active
    public static void viewAllEmployees(){
        System.out.println("\n======Employee List======");
        
        System.out.printf("%-8s %-20s %-15s %-22s %14s  %-10s%n", 
                "ID", "Name", "Department", "Job Title", "Salary", "Type");
        
        boolean hasActive = false;
        for (Employee emp : employeeList) {
            if(emp.isActive()){
                System.out.printf("%-8s %-20s %-15s %-22s %,14.0f  %-10s%n",
                        emp.getId(),
                        emp.getName(),
                        emp.getDepartment(),
                        emp.getJobTitle(),
                        emp.getBasicSalary(),
                        emp.getEmployeeType());
                hasActive=true;
            }
        }
        if(!hasActive) {
            System.out.println("Not have employee info yet");
        }
    
        System.out.println("----------------------------------------------");
        System.out.println("Enter to return");
        sc.nextLine();  //chờ nhấn Enter rồi mới return 
    }
    
    //1.5 Search Employee
    public static void searchEmployee(){
        System.out.println("\n===Search Employee===");
        System.out.println("1. Search for name: ");
        System.out.println("2. Search for department: ");
        System.out.println("3. Search for job title: ");
        System.out.print("Choose number (1-3): ");       
        
        String choice = sc.nextLine().trim();
        
        //Search key word
        String keyword = "";
        switch (choice) {
            case "1":
                System.out.print("Enter name to search: ");
                keyword = sc.nextLine().trim().toLowerCase();
                break;
            case "2":
                System.out.print("Enter department to search: ");
                keyword = sc.nextLine().trim().toLowerCase();
                break;
            case "3":
                System.out.print("Enter job title to search: ");
                keyword = sc.nextLine().trim().toLowerCase();
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }
        
        //Result
        System.out.println("\n--- RESULT SEARCHING ---");
        int count =0;
        for (Employee emp : employeeList) {
            if(!emp.isActive()) continue;   //skip nv nghỉ
            
            boolean match = false;
            if(choice.equals("1") && emp.getName().toLowerCase().contains(keyword)){
                match = true;
            }
            if(choice.equals("2") && emp.getDepartment().toLowerCase().contains(keyword)){
                match = true;
            }
            if(choice.equals("3") && emp.getJobTitle().toLowerCase().contains(keyword)){
                match = true;
            }
            
            if(match == true){
                System.out.println( emp.getId() 
                                    + " - " + emp.getName()
                                    + " - " + emp.getDepartment() 
                                    + " - " + emp.getJobTitle());
                count++;
            }
        }
        System.out.println("Found" + count +"employees");
    }
    
    // ================================================================
    // MENU ATTENDANCE MANAGEMENT
    // ================================================================
    
    public static void attendanceMenu(){
        while(true){
            System.out.println("\n=== ATTENDANCE MANAGEMENT ===");
            System.out.println("1. Record Attendance");
            System.out.println("2. Update Attendance");
            System.out.println("3. View Attendance History");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose number(1-4): ");
            
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    recordAttendance();
                    break;
                case "2": 
                    updateAttendance();      
                    break;
                case "3": 
                    viewAttendanceHistory(); 
                    break;
                case "4": 
                    return;    
                default:
                    System.out.println("Invalid choose");;
            }            
        }        
    }
    
    //2.1 Record Attendance
    public static void recordAttendance(){
        System.out.println("\n--- RECORD ATTENDANCE ---");
        System.out.print("Employee ID: ");
        String id = sc.nextLine().trim();
        
        // BR3: NV phải tồn tại và đang active mới được chấm công
        Employee emp = employeeMap.get(id);
        if(emp==null || !emp.isActive()){
            System.out.println("Employee id not found");
            return;
        }
        
        //Date attendance
        System.out.println("Date (dd/MM/yyyy): ");
        String dateStr = sc.nextLine().trim();
        Date date;
        try {
            dateFormat.setLenient(false);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            System.out.println("Wrong date format");
            return;
        }
        
        // BR4: kiểm tra ngày này đã chấm công chưa
        //check attendance date
        if(emp.hasAttendanceOnDate(dateStr)){
            System.out.println("Already has Date: " + dateStr);
            return;
        }
        
        // BR5: kiểm tra status hợp lệ
        //check status
        System.out.print("Status(Present/ Absent/ Leave): ");
        String status = sc.nextLine().trim();
        if(!Validator.isValidAttendanceStatus(status)){
            System.out.println("Status must be (Present/ Absent/ Leave)");
            return;
        }
        
        //Present: has overtime work
        int overtime = 0;
        if("Present".equals(status)){
            System.out.print("Overtime Hours(0 = not work): ");
            String otStr = sc.nextLine().trim();
            if(!Validator.isValidOvertimeHours(otStr)){
                System.out.println("Working time should be >= 0");
                return;
            }
            overtime = Integer.parseInt(otStr);
        }
        
        //Add attendance record
        try {
            Attendance att = new Attendance(date, status, overtime);
            emp.addAttendance(att); // thêm vào List và Map trong Employee
            
            // Lưu file sau mỗi lần chấm công
            FileManager.saveAttendance(employeeList);
            System.out.println("Attendance record success");
        } catch (IllegalArgumentException e) {
            System.out.println(" [Error] " + e.getMessage());
        } catch(Exception e){
            System.out.println(" [Error] save file " + e.getMessage());
        }
        
    }
    
    //2.2 Update Attendance
    public static void updateAttendance(){
        System.out.println("\n--- UPDATE ATTENDANCE ---");
        System.out.print("Emoployee ID: ");
        String id = sc.nextLine().trim();
        
        Employee emp = employeeMap.get(id);
        if(emp==null){
            System.out.println("Employee ID not found");
            return;
        }
        
        System.out.print("Update Date(dd/MM/yyyy): ");
        String dateStr = sc.nextLine().trim();
        
        //Check date has recorded attendance yet?
        if(!emp.hasAttendanceOnDate(dateStr)){
            System.out.println(" Not recorded date " + dateStr);
            return;
        }
        
        System.out.print("Update Status (Present/ Absent/ Leave): ");
        String status = sc.nextLine().trim();
        if(!Validator.isValidAttendanceStatus(status)){
            System.out.println("Invalid status");
            return;
        }
        
        int overtime = 0;
        if("Present".equals(status)){
            System.out.print("New Overtime Hours(>=0): ");
            String otStr = sc.nextLine().trim();
            if(!Validator.isValidOvertimeHours(otStr)){
                System.out.println("Working time should be >= 0");
                return;
            }
            overtime = Integer.parseInt(otStr);
        }
        
        try {
            emp.updateAttendance(dateStr, status, overtime);
            FileManager.saveAttendance(employeeList);
            System.out.println("Update attendance record success");
        } catch (Exception e) {
            System.out.println(" [ERROR] " + e.getMessage());
        }
        
    }
    
    //2.3 Attendance history
    public static void viewAttendanceHistory(){
        System.out.println("\n--- ATTENDANCE HISTORY ---");
        System.out.print("Employee ID: ");
        String id = sc.nextLine().trim();
        
        Employee emp = employeeMap.get(id);
        if(emp==null){
            System.out.println("Employee ID not found");
            return;
        }
        
        System.out.println("\nEmployee: " + emp.getName() + emp.getId() );
        System.out.println("-----------------------------------------");
        
        List<Attendance> list = emp.getAttendanceList();
        if(list.isEmpty()){
            System.out.println("Attendance record is missing");
        }else{
            for (Attendance att : list) {
                System.out.println(" " + att.toString());
            }
        }
        
        System.out.println("------------------------------------------");
        System.out.println("Total woking days: " + emp.countWorkingDays());
        System.out.println("Total absent days: " + emp.countAbsentDays());
        System.out.println("\nEnter to return...");
        sc.nextLine();
           
    }
    
    // ================================================================
    // MENU SALARY MANAGEMENT
    // ================================================================
    
    public static void salaryMenu(){
        while(true){
            System.out.println("\n=== SALARY MANAGEMENT ===");
            System.out.println("1. Calculate Salary Month");
            System.out.println("2. View Salary Details");
            System.out.println("3. Generate salary report for all employees");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose number(1-4): ");
            
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": 
                    calculateSalary();    
                    break;
                case "2": 
                    viewSalaryDetails(); 
                    break;
                case "3": 
                    generateSalaryReport(); 
                    break;
                case "4": 
                    return;
                default:
                    System.out.println("Invalid choose");
            }
        }
    }
    
    // ================================================================
    // PHƯƠNG THỨC HELPER - dùng chung, tránh lặp code
    // ================================================================
    
    // Đọc và kiểm tra tháng từ bàn phím
    // Trả về số tháng (1-12) nếu hợp lệ, -1 nếu nhập sai
    private static int readMonth(){
        System.out.print("Month (1-12): ");
        String monthStr = sc.nextLine().trim();
        if(!Validator.isValidMonth(monthStr)){
            System.out.println("Invalid month should be 1-12");
            return -1;
        }
        return Integer.parseInt(monthStr);
    }
    
    private static int readYear(){
        System.out.print("Year (>=2000): ");
        String yearStr = sc.nextLine().trim();
        if(!Validator.isValidYear(yearStr)){
            System.out.println("Invalid year should be 2000-2500");
            return -1;
        }
        return Integer.parseInt(yearStr);
    }
    
    
    //3.1 SALARY IN MONTH/YEAR (BR7, BR8, BR9, BR10)
    public static void calculateSalary(){
        System.out.println("\n--- CALCULATE SALARY ---");
        System.out.print("Employee ID: ");
        String id = sc.nextLine().trim();
        
        Employee emp = employeeMap.get(id);
        // BR10: chỉ tính lương NV đang active
        if(emp==null || !emp.isActive()){
            System.out.println("Employee id not found");
            return;
        }
        
        int month = readMonth();
        int year = readYear();
        if(month == -1 || year == -1)   return;
        
        int workingDays = emp.countWorkingDaysInMonth(month, year);
        int overtimeHours = emp.countOvertimeHoursInMonth(month, year);
        int absentDays = emp.countAbsentDaysInMonth(month, year);
        double totalSalary = emp.calculateSalaryInMonth(month, year);
        
        System.out.println("\nSalary Calculated");
        System.out.println("Employee: " + emp.getName());
        System.out.println("Date Salary: " + month + "/" + year);
        System.out.println("Working Days: " + workingDays);
        System.out.println("Overtime Hours: " + overtimeHours);
        System.out.println("Absent Days: " + absentDays);
        System.out.printf("Total Salary: %,.0f VND%n", totalSalary);
 
    }
    
    //3.2 SALARY DETAILS
    public static void viewSalaryDetails(){
        System.out.println("\n--- SALARY DETAILS ---");
        System.out.print("Employee ID: ");
        String id = sc.nextLine().trim();
        
        Employee emp = employeeMap.get(id);
        if(emp == null){
            System.out.println("Employee ID not found");
            return;
        }
        
        int month = readMonth();
        int year = readYear();
        if (month == -1 || year == -1) return;
        
        double basicSalary = emp.getBasicSalary();
        int ot = emp.countOvertimeHoursInMonth(month, year);
        int absent = emp.countAbsentDaysInMonth(month, year);
        
        double otRate;
        //instanceof: kiểm tra đối tượng thuộc class nào
        if(emp instanceof FullTimeEmployee){
            otRate = 80000; // Lương OT cho Full-time
        }else{
            otRate = 50000; // Lương OT cho Part-time
        }
        
        double otPay = ot * otRate;
        double deduction = absent * 100000;
        double total = emp.calculateSalaryInMonth(month, year);
        
        System.out.println("Salary Details: " + emp.getName() + "(" + month + "/" + year + ")");
        System.out.printf("Basic Salary: %,12.0f VND%n", basicSalary);
        System.out.printf("Salary working overtime: %,12.0f VND(%d hours * %,.0f)%n", 
                          otPay, ot, otRate);
        System.out.printf("Absent: %,12.0f VND(%d days × 100000)%n", deduction, absent);
        System.out.printf("Total: %,12.0f VND%n", total);
        
    }
    
    //3.3 SALARY REPORT
    public static void  generateSalaryReport(){
        System.out.println("\n--- SALARY REPORT (ALL EMPLOYEES) ---");
        
        int month = readMonth();
        int year = readYear();
        if(month == -1 || year == -1)   return;
        
        System.out.println("----------------------------------------");
        double grandTotal = 0;  // tổng lương tất cả NV
        for (Employee emp : employeeList) {
            if(!emp.isActive()) continue;   //skip nv nghỉ
            
            double salary = emp.calculateSalaryInMonth(month, year);
            grandTotal += salary;
            
            System.out.printf("%-8s %-20s %-12s %,14.0f%n",
                              emp.getId(),
                              emp.getName(),
                              emp.getEmployeeType(),
                              salary);
        }
        System.out.println("----------------------------------------");
        System.out.printf("Grand total: %,14.0f%n", grandTotal);
        System.out.println("Enter to return...");
        sc.nextLine();
        
    }
    
    
    // ================================================================
    // MENU REPORT
    // ================================================================
    public static void reportMenu(){
        while(true){
            System.out.println("\n=== REPORTS ===");
            System.out.println("1. Low Attendance Employees");
            System.out.println("2. Highest Paid Employees");
            System.out.println("3. back to Main Menu");
            System.out.println("Choose number(1-3): ");
            
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    reportLowAttendance();
                    break;
                case "2":
                    reportHighestPaid();
                    break;
                case "3":                  
                    return;
                default:
                    System.out.println("Invalid choose");;
            }
        }
    }
    
    //4.1 Báo cáo NV nghỉ nhiều (vắng > limit ngày/tháng) (BR 12)
    public static void reportLowAttendance(){
        System.out.println("\n--- LOW ATTENDANCE REPORT ---");
        
        int month = readMonth();
        int year  = readYear();
        if (month == -1 || year == -1) return;
        
        System.out.println("Limit absent (default = 3 days): ");
        String limitStr = sc.nextLine().trim();
        int limit = 3; // giá trị mặc định nếu bỏ trống
        if(!limitStr.isEmpty() && Validator.isPositiveInt(limitStr)){
            limit = Integer.parseInt(limitStr);
        }
        
        System.out.println("-----------------------------------------");
        int count = 0;
        for (Employee emp : employeeList) {
            if(!emp.isActive()) continue;
            
            int absent = emp.countAbsentDaysInMonth(month, year);
            if(absent > limit){
                System.out.printf("%-8s %-20s %d days%n", 
                                  emp.getId(),
                                  emp.getName(),
                                  absent);
                count++;
            }
        }
        if(count==0){
            System.out.println("No absent employee over " + limit +" days ");
        }
        System.out.println("-----------------------------------------");
        System.out.println("Enter to return...");
        sc.nextLine();
    }
    
    //4.2 Báo cáo NV lương cao nhất (sort giảm dần) (BR 13)
    public static void reportHighestPaid(){
        System.out.println("\n--- HIGHEST PAID EMPLOYEES ---");
        int month = readMonth();
        int year  = readYear();
        if (month == -1 || year == -1) return;
        
        // Bước 1: Tính lương từng NV và lưu vào Map
        // LinkedHashMap: giống HashMap nhưng giữ thứ tự insert
        // Dùng Map<Employee, Double>: key = NV, value = lương tháng đó
        Map<Employee,Double> salaryMap = new LinkedHashMap<>();
        for (Employee emp : employeeList) {
            if(emp.isActive()){
                salaryMap.put(emp, emp.calculateSalaryInMonth(month, year));
            }
        }
        
        
        // Bước 2: Chuyển Map → List<Entry> để sort được
        // entrySet(): lấy tập hợp các cặp key-value (Entry)
        // new ArrayList<>(...): chuyển Set thành List để dùng sort()
        List<Map.Entry<Employee,Double>> sorted = new ArrayList<>(salaryMap.entrySet());
        
        // Bước 3: Sort giảm dần theo lương (lương cao nhất lên đầu)
        // sort() với Comparator lambda:
        //   (a, b) -> ...: so sánh 2 phần tử a và b
        //   Double.compare(b, a): so sánh b với a → kết quả âm nếu b < a → sort tăng dần theo a
        //   Double.compare(b.getValue(), a.getValue()): b lớn hơn → b lên trước → giảm dần
        sorted.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        
        System.out.println("----------------------------------------");
        int rank = 1;
        for (Map.Entry<Employee, Double> entry : sorted) {
            // entry.getKey() = Employee
            // entry.getValue() = lương
            System.out.printf("%-5d %-8s %-20s %,14.0f%n",
                                    rank++,
                               entry.getKey().getId(),
                               entry.getKey().getName(),
                               entry.getValue());
            
        }
        
        System.out.println("----------------------------------------");
        System.out.println("ENTER to return...");
        sc.nextLine();
    }
    
}

