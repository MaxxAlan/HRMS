package hrms.models;

import java.util.Date;

public class Attendance {
    
    private Date date;
    private String status;      //PRESENT, ABSENT, LEAVE
    private int overtimeHours;

    public Attendance(Date date, String status, int overtimeHours) {
        this.date = date;
        setStatus(status);  //1 lọa phương thức Setter để thay đổi giá trị của status 
                            //tái sử dụng cho bên dưới , khi cần sửa setStatus dưới thì constructor 
                            //tự động dùng valid mới 
        this.overtimeHours = overtimeHours;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public void setStatus(String status) {
        if ("Present".equals(status) || "Absent".equals(status) 
                || "Leave".equals(status)) {
            this.status=status;
        }else{
            System.out.println("Error");
        }
    }
    //  object1.equals(object2)
    //  "Data".equals.(Scanner input)

    public void setOvertimeHours(int overtimeHours) {
        if(overtimeHours >= 0){
            this.overtimeHours = overtimeHours;
        }else{
            System.out.println("Error");
        }
    }

    
    
}
