package hrms.models;

import java.util.Date;

public class PartTimeEmployee extends Employee {

    public PartTimeEmployee(String id, String name, String department, String jobTitle, Date joinDate, double basicSalary, boolean isActive) {
        super(id, name, department, jobTitle, joinDate, basicSalary, isActive);
    }

    public double calculateSalary() {
        return basicSalary; // Simplified
    }
}