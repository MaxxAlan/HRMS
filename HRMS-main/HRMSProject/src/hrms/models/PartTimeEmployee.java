package hrms.models;

import java.util.Date;

public class PartTimeEmployee extends Employee {
    private static final double OVERTIME_RATE = 50000.0;

    public PartTimeEmployee(String id, String name, String department, String jobTitle, Date joinDate, double basicSalary, boolean isActive) {
        super(id, name, department, jobTitle, joinDate, basicSalary, isActive);
    }

    @Override
    public double calculateSalary(int month, int year) {
        int workingDays = calculateWorkingDays(month, year);
        double overtime = calculateTotalOvertime(month, year);
        // Part-time: Lương theo ngày * số ngày làm + OT
        return (basicSalary * workingDays) + (OVERTIME_RATE * overtime);
    }
}
