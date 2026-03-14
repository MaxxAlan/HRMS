package hrms.models;

import java.util.Date;

public class FullTimeEmployee extends Employee {
    //extends: tính kế thừa
    //nhận tất cả thuộc tính, phương thức từ class Employee
    

    // BR8: Full-time làm thêm 1 giờ = 80,000 VND
    private static final double OVERTIME_RATE = 80000;
    
    // BR9: Vắng 1 ngày bị trừ 100,000 VND
    private static final double ABSENCE_DEDUCTION = 100000;

    //constructor
    public FullTimeEmployee(String id, String name, String department, String jobTitle, 
            Date joinDate, double basicSalary, boolean active) {
        super(id, name, department, jobTitle, joinDate, basicSalary, active);
    }// có thể insert constructor này khi đã có extends Employee
    // super: gọi constructor của class cha (Employee)  
    // nhằm tránh repeat vs code construct trg Employee 

    @Override
    public double calculateSalary(){
        if(!isActive())return 0;
        
        //Tính giờ làm thêm + ngày nghỉ
        int overtimeHours=0;
        int absentDays=0;
        
        for (Attendance att : getAttendanceList()) {
            if("Present".equals(att.getStatus())){
                overtimeHours += att.getOvertimeHours();
            }else if("Absent".equals(att.getStatus())){
                absentDays++;
            }
        }
        
        return basicSalary 
                + (overtimeHours*OVERTIME_RATE) 
                - (absentDays*ABSENCE_DEDUCTION);
    }
    
    // Tính lương trong tháng/năm cụ thể (BR13)
    @Override
    public double calculateSalaryInMonth(int month, int year){
        if(!isActive()) return 0;   // BR10
        
        // Gọi phương thức đếm từ lớp cha (Employee)
        int overtimeHours = countOvertimeHoursInMonth(month, year);
        int absentDays = countAbsentDaysInMonth(month, year);
         
        return basicSalary 
                + (overtimeHours*OVERTIME_RATE) 
                - (absentDays*ABSENCE_DEDUCTION);
    }
    
    @Override
    public String getEmployeeType(){
        return "Full-time";
    }
    

    
}
