package hrms.models;

import java.util.Date;

public class FullTimeEmployee extends Employee {
    //extends: tính kế thừa
    //nhận tất cả thuộc tính, phương thức từ class Employee
    //có thể thêm thuộc tính mới (nếu muốn)

    private static final double OVERTIME_RATE = 80000;
    //tương tự như const (hằng số) trg C
    //mọi nhân viên FullTime làm thêm đều đc 80k, ko bao đổi 
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
