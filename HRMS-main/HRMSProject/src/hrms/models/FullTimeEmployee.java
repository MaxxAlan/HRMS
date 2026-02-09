package hrms.models;

import java.util.Date;

public class FullTimeEmployee extends Employee {
    private static final double OVERTIME_RATE = 80000.0;

    public FullTimeEmployee(String id, String name, String department, String jobTitle, Date joinDate, double basicSalary, boolean isActive) {
        super(id, name, department, jobTitle, joinDate, basicSalary, isActive);
    }

    @Override
    public double calculateSalary(int month, int year) {
        int workingDays = calculateWorkingDays(month, year);
        double overtime = calculateTotalOvertime(month, year);
        // Công thức: Lương cứng + (Lương ngày * số ngày làm) + (Lương OT * số giờ OT)
        // Giả sử lương cơ bản là lương tháng cho 22 ngày công
        double dailySalary = basicSalary / 22;
        return (dailySalary * workingDays) + (OVERTIME_RATE * overtime);
    }
}
