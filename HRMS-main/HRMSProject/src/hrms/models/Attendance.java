package hrms.models;

import java.util.Date;

public class Attendance {
    private String employeeId; // Cần thiết để định danh khi lưu file
    private Date date;
    private String status; // Present, Absent, Leave
    private double overtimeHours;
    private String inTime;
    private String outTime;

    // Constructor đầy đủ theo PDF + Hệ thống
    public Attendance(String employeeId, Date date, String status, double overtimeHours, String inTime, String outTime) {
        this.employeeId = employeeId;
        this.date = date;
        this.status = status;
        this.overtimeHours = overtimeHours;
        this.inTime = inTime;
        this.outTime = outTime;
    }

    // Constructor rút gọn cho Check-in (khớp với HRMS.java)
    public Attendance(String employeeId, Date date, String inTime, String outTime) {
        this(employeeId, date, "Present", 0.0, inTime, outTime);
    }

    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public Date getDate() { return date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getOvertimeHours() { return overtimeHours; }
    public void setOvertimeHours(double overtimeHours) { this.overtimeHours = overtimeHours; }
    public String getInTime() { return inTime; }
    public String getOutTime() { return outTime; }
    public void setOutTime(String outTime) { this.outTime = outTime; }
}
