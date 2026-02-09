package hrms.models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public abstract class Employee {
    protected String id;
    protected String name;
    protected String department;
    protected String jobTitle;
    protected Date joinDate;
    protected double basicSalary;
    protected boolean isActive;
    protected List<Attendance> attendances; // HAS-A relationship

    public Employee(String id, String name, String department, String jobTitle, Date joinDate, double basicSalary, boolean isActive) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.jobTitle = jobTitle;
        this.joinDate = joinDate;
        this.basicSalary = basicSalary;
        this.isActive = isActive;
        this.attendances = new ArrayList<>();
    }

    public void addAttendance(Attendance att) {
        this.attendances.add(att);
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    // Logic tính toán theo yêu cầu PDF
    public int calculateWorkingDays(int month, int year) {
        int count = 0;
        Calendar cal = Calendar.getInstance();
        for (Attendance att : attendances) {
            cal.setTime(att.getDate());
            if (cal.get(Calendar.MONTH) == (month - 1) && cal.get(Calendar.YEAR) == year && "Present".equals(att.getStatus())) {
                count++;
            }
        }
        return count;
    }

    public double calculateTotalOvertime(int month, int year) {
        double total = 0;
        Calendar cal = Calendar.getInstance();
        for (Attendance att : attendances) {
            cal.setTime(att.getDate());
            if (cal.get(Calendar.MONTH) == (month - 1) && cal.get(Calendar.YEAR) == year) {
                total += att.getOvertimeHours();
            }
        }
        return total;
    }

    public abstract double calculateSalary(int month, int year);

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public Date getJoinDate() { return joinDate; }
    public double getBasicSalary() { return basicSalary; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
