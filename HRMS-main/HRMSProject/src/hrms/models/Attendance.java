package hrms.models;

import java.util.Date;

public class Attendance {
    private String employeeId;
    private Date date;
    private String inTime;
    private String outTime;

    public Attendance(String employeeId, Date date, String inTime, String outTime) {
        this.employeeId = employeeId;
        this.date = date;
        this.inTime = inTime;
        this.outTime = outTime;
    }

    public String getEmployeeId() { return employeeId; }
    public Date getDate() { return date; }
    public String getInTime() { return inTime; }
    public void setInTime(String inTime) { this.inTime = inTime; }
    public String getOutTime() { return outTime; }
    public void setOutTime(String outTime) { this.outTime = outTime; }

    // Helper methods for compatibility with calculation logic
    public String getStatus() {
        return (inTime != null && !inTime.isEmpty()) ? "Present" : "Absent";
    }

    public double getOvertimeHours() {
        return 0.0; // Simplified: No overtime logic yet
    }
}
