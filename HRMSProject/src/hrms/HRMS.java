
package hrms;

import hrms.models.Employee;
import hrms.models.FullTimeEmployee;
import hrms.models.PartTimeEmployee;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class HRMS { 
    private static List<Employee> employeeList = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public static void main(String[] args) {
        System.out.println("HRMS System");
        
        while (true) {            
            System.out.println("\n===Main Menu===");
            System.out.println("1. Employee Management");
            System.out.println("2. Attendance Managemen");
            System.out.println("3. Salary Management");
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            System.out.print("Select ur number: ");
            
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1": employeeMenu(); break;
                //case "2": attendanceMenu(); break;
                //case "3": salaryMenu(); break;    
                //case "4": reportsMenu(); break;    
                case "5": 
                    System.out.println("End");
                    return;
                default: System.out.println("Invalid");
            }
           
        }
    }
    
    public static void employeeMenu(){
        while (true) {            
            System.out.println("\n=== EMPLOYEE MANAGEMENT ===");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. View Employee");
            System.out.println("5. Search Employees");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose (1-6): ");
            
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1": addEmployee(); break;
                case "2": updateEmployee(); break;
                case "3": removeEmployee(); break;
                case "4": viewAllEmployees(); break;
                case "5": searchEmployee(); break;
                case "6": return; 
                default:
                    System.out.println("Invalid choose again");
            }
        }
    }
    
    //1. Add employee
    public static void addEmployee(){
        System.out.println("\n--- ADD EMPLOYEE ---");
        //Nhập id
        System.out.print("Employee ID: ");
        String id = sc.nextLine();
        //Id ko đc bỏ
       if(id.isEmpty()){
           System.out.print("Error id empty");
           return;
       }
       //Check id có trùng ko
        for (Employee emp : employeeList) {
            if(emp.getId().equals(id)){
                System.out.print("Id exist");
                return;
            }
        }
        //Enter name
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        if(name.isEmpty()){
            System.out.print("Name can't be enmpty");
            return;
        }
        //Enter department
        System.out.print("Department: ");
        String department = sc.nextLine();
        if(department.isEmpty()){
            System.out.println("Department can't be empty");
            return;
        }
        //Enter job title
        System.out.print("Job Title: ");
        String jobTitle = sc.nextLine();
        //Enter type job
        System.out.print("Type (Part-time/Full-time): ");
        String type = sc.nextLine();
        //Enter date join
        System.out.print("Date join(dd/MM/yyyy): ");
        String dateStr = sc.nextLine();
        Date jointDate;
        try {
            jointDate = dateFormat.parse(dateStr);
        } catch (Exception e) {
            System.out.println("Invalid");
            return;
        }
        //Enter salary
        System.out.print("Basic salary: ");
        String salaryStr = sc.nextLine();
        double basicSalary;
        try {
            basicSalary = Double.parseDouble(salaryStr);
            if(basicSalary <= 0){
                System.out.println("Error salary must be greater than 0");
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid salary");
            return;
        }
        //Create employee
        Employee newEmployee;
        if(type.equalsIgnoreCase("Full-time")){
            newEmployee = new FullTimeEmployee(id, name, department, 
                    jobTitle, jointDate, basicSalary, true);
            
        }else if(type.equalsIgnoreCase("Part-time")){
            newEmployee = new PartTimeEmployee(id, name, department, 
                    jobTitle, jointDate, basicSalary, true);
        }else{
            System.out.println("Invalid");
            return;
        }
        //Save and Cancel
        System.out.println("1. Save  2. Cancel");
        System.out.print("Choose number: ");
        String saveChoice = sc.nextLine();
        
        if(saveChoice.equals("1")){
            employeeList.add(newEmployee);
            System.out.println("Employee add successfully");
        }else{
            System.out.println("Cancel");
        }
        
    }
    
    //2. Update info Employee
    public static void updateEmployee(){
        System.out.println("\n--- ADD EMPLOYEE ---");
        System.out.println("Enter id to update: ");
        String id = sc.nextLine();
        
        //Find employee
        Employee emp = null;
        for (Employee e : employeeList) {
            if(e.getId().equals(id)){
                emp = e;
                break;
            }
        }
        
        if(emp == null){
            System.out.println("Error: Employee not found");
            return;
        }
        
        //Show current info
        System.out.println("Show info");
        System.out.println("Name: " +emp.getName());
        System.out.println("Department: " +emp.getDepartment());
        System.out.println("Job title: " +emp.getJobTitle());
        System.out.println("Basic salary: " +emp.getBasicSalary());
        
        //Input new info
        System.out.print("Enter new department (leave bleank to skip): ");
        String newDepartment = sc.nextLine();
        if(!newDepartment.isEmpty()){
            emp.setDepartment(newDepartment);
        }
        
        System.out.print("Enter new Job title (leave blank to skip): ");
        String newJobTitle = sc.nextLine();
        if(!newJobTitle.isEmpty()){
            emp.setJobTitle(newJobTitle);
        }
        
        //Save and Cancel
        System.out.println("1. Update   2. Cancel");
        System.out.print("Choose number: ");
        String updateChoice = sc.nextLine();
        if(updateChoice.equals("1")){
            System.out.println("Successfully update");
        }else{
            System.out.println("Cancel");
        }
        
    }
    
    //3. Remove info Employee
    public static void removeEmployee(){
        System.out.println("\n===REMOVE EMPLOYEE===");
        System.out.print("Enter id to remove: ");
        String id = sc.nextLine();
        
        //Search employee
        Employee emp = null;
        for (Employee e : employeeList) {
            if(e.getId().equals(id)){
                emp = e;
                break;
            }
        }
        if(emp == null){
            System.out.println("Error: Employee not found");
            return;
        }
        
        //Confirm
        System.out.print("You sure remove " +emp.getName()+"? (Y/N): ");
        String confirm = sc.nextLine();
        if(confirm.equalsIgnoreCase("Y")){  //equalIgnoreCase ghi thường hay hoa đều ko sao
            emp.setActive(false);   //// Không xóa hẳn, chỉ deactivate
            System.out.println("Remove successfully");
        }else{
            System.out.println("Cancel");
        }
        
    }
    
    //4.Show all Emoployee
    public static void viewAllEmployees(){
        System.out.println("\n======Employee List======");
        System.out.printf("%-8s %-20s %-15s %-20s %-15s%n", 
                "ID", "Name", "Department", "Job Title", "Salary");
        
        if (employeeList.isEmpty()) {
            System.out.println("No employees found");
        }else {
            for (Employee emp : employeeList) {
                if(emp.isActive()){ //show active employees only
                    System.out.printf("%-8s %-20s %-15s %-20s %-15s%n",
                            emp.getId(),
                            emp.getName(),
                            emp.getDepartment(),
                            emp.getJobTitle(),
                            emp.getBasicSalary() );
                }
            }
        }
    
        System.out.println("----------------------------------------------");
        System.out.println("Enter to return");
        sc.nextLine();
    }
    
    //5.Search Employee
    public static void searchEmployee(){
        System.out.println("\n===Search Employee===");
        System.out.println("Enter 1 for searching name: ");
        
        String choice = sc.nextLine();
        
        if(choice.equals("1")){
            System.out.print("Enter name: ");
            String searchName = sc.nextLine();
            
            System.out.println("\n----Search Result----");
            int count=0;
            for (Employee emp : employeeList) {
                if(emp.isActive() && emp.getName().contains(searchName)){
                                                   //contains:tìm kiếm chuỗi trong List
                    System.out.println(emp.getId() + "-" + emp.getName()
                                        + "-" + emp.getDepartment());
                    count++;
                }
            }
            System.out.println("Found: " +count+ "employees");
        }
        
    }
    
}

