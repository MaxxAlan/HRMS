package hrms.models;

import java.util.Date;

public abstract class Employee {
    protected String id;
    protected String name;
    protected String department;
    protected String jobTitle;
    protected Date joinDate;
    protected double basicSalary;
    protected boolean isActive;

    public Employee(String id, String name, String department, String jobTitle, Date joinDate, double basicSalary, boolean isActive) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.jobTitle = jobTitle;
        this.joinDate = joinDate;
        this.basicSalary = basicSalary;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public Date getJoinDate() { return joinDate; }
    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}