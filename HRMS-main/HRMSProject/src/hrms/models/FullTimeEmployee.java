package hrms.models;

import java.util.Date;

public class FullTimeEmployee extends Employee {

    public FullTimeEmployee(String id, String name, String department, String jobTitle, Date joinDate, double basicSalary, boolean isActive) {
        super(id, name, department, jobTitle, joinDate, basicSalary, isActive);
    }

    // Logic tính lương cơ bản đơn giản cho demo
    public double calculateSalary() {
        return basicSalary;
    }
}