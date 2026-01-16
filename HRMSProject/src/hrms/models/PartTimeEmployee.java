package hrms.models;

import java.util.Date;

public class PartTimeEmployee extends Employee {

    private static final double OVERTIME_RATE = 50000;

    public PartTimeEmployee(String id, String name, String department, String jobTitle, Date joinDate, double basicSalary, boolean active) {
        super(id, name, department, jobTitle, joinDate, basicSalary, active);
    }

    @Override
    public double caculateSalary(int month, int year) {
        //code here
    }

}
