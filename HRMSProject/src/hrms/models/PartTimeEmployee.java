package hrms.models;

import java.util.Date;

public class PartTimeEmployee extends Employee {

    // BR8: Part-time làm thêm 1 giờ = 50,000 VND
    private static final double OVERTIME_RATE = 50000;
    // BR9: Vắng 1 ngày bị trừ 100,000 VND 
    private static final double ABSENCE_DEDUCTION = 100000;
    
    
    //Constructor
    public PartTimeEmployee(String id, String name, String department, String jobTitle, 
            Date joinDate, double basicSalary, boolean active) {
        super(id, name, department, jobTitle, joinDate, basicSalary, active);
        
    }

    // BR7: Lương Part-time
    @Override
    public double calculateSalary(){
        if(!isActive())return 0;
        
        
        int overtimeHours=0;
        int absentDays=0;
        
        for (Attendance att : getAttendanceList()) {
            if("Present".equals(att.getStatus())){
                overtimeHours += att.getOvertimeHours();
            }else if("Absent".equals(att.getStatus())){
                absentDays++;
            }
        }
        
        return getBasicSalary() 
                + (overtimeHours*OVERTIME_RATE) 
                - (absentDays*ABSENCE_DEDUCTION);
    }

    @Override
    public double calculateSalaryInMonth(int month, int year) {
        if(!isActive()) return 0;
        
        int overtimeHours = countOvertimeHoursInMonth(month, year);
        int absentDays = countAbsentDaysInMonth(month, year);
        
        return getBasicSalary() 
                + (overtimeHours*OVERTIME_RATE) 
                - (absentDays*ABSENCE_DEDUCTION);
    }

    @Override
    public String getEmployeeType() {
        return "Part-time";
    }
    
}


