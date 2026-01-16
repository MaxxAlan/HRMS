package hrms.models;

import java.util.Date;

public class Attendance {
    
    private Date date;
    private String status;
    private int getOvertime;

    public Attendance(Date date, String status, int getOvertime) {
        this.date = date;
        this.status = status;
        this.getOvertime = getOvertime;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public int getGetOvertime() {
        return getOvertime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // chuk add PRESENT, ABSENT, LEAVE nha, cần bổ sung thêm cho status 
    
}
