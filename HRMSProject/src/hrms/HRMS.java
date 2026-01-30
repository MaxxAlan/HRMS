
package hrms;

import hrms.models.Employee;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HRMS { 
    private static List<Employee> employeeList = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
    
    public static void main(String[] args) {
        System.out.println("HRMS System");
        
        while (true) {            
            System.out.println("\n===Main Menu===");
            System.out.println("1. Employee Management");
            System.out.println("2. Attendance Managemen");
            System.out.println("3. Salary Managemen");
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
            System.out.println("2. Search Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete/Restore Employee");
            System.out.println("5. View All Employees");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose (1-6): ");
            
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1": addEmployee(); break;
                //case "2": 
                default:
                    throw new AssertionError();
            }
        }
    }
    
    public static void addEmployee(){
         System.out.println("\n--- ADD EMPLOYEE ---");
        String id;
        try {
            //1. Enter id
            while (true) {                
                System.out.print("Employee id: ");
                id = sc.nextLine();
                
                if(id.isEmpty()){   //Báo ko nhập id
                    System.out.println("ID can not be empty");
                    continue;
                }
                
                boolean isFound = false;    // Check duplicate
                for (Employee emp : employeeList) {
                    if (emp.getId().equals(id)) {
                        isFound = true;
                        break;
                    }                    
                }
                
                if(isFound){
                    System.out.println("Already exist id" + id);
                    continue;
                }else{
                    break; 
                } 
                
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    
    
}
