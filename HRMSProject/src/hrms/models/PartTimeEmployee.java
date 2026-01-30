package hrms.models;

import java.util.Date;

public class PartTimeEmployee extends Employee {

    private static final double OVERTIME_RATE = 50000;
    private static final double ABSENCE_DEDUCTION = 100000;
    
    
    //Constructor
    public PartTimeEmployee(String id, String name, String department, String jobTitle, 
            Date joinDate, double basicSalary, boolean active) {
        super(id, name, department, jobTitle, joinDate, basicSalary, active);
        
    }

    @Override
    public double calculateSalary(){
        if(!isActive())return 0;
        
        
        int overtimeHours=0;
        int absentDays=0;
        for (Attendance x : getAttendanceList()) {
            if("Present".equals(x.getStatus())){
                overtimeHours += x.getOvertimeHours();
            }else if("Absent".equals(x.getStatus())){
                absentDays++;
            }
        }
        
        return getBasicSalary() 
                + (overtimeHours*OVERTIME_RATE) 
                - (absentDays*ABSENCE_DEDUCTION);
    }
    
}


