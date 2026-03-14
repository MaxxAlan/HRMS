package hrms.models;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Attendance {
    
    private Date date;          //Ngày chấm công
    private String status;      //PRESENT, ABSENT, LEAVE
    private int overtimeHours;  //Số giờ làm thêm (0 nếu không làm thêm)
    
    public static final Set<String> validStatus = new HashSet<>(
                                Arrays.asList("Present", "Absent", "Leave"));
    // Arrays.asList tạo List ["Present", "Absent", "Leave"]
    
    public Attendance(Date date, String status, int overtimeHours) {
        this.date = date;
        setStatus(status);  //1 lọa phương thức Setter để thay đổi giá trị của status 
                            //tái sử dụng cho bên dưới , khi cần sửa setStatus dưới thì constructor 
                            //tự động dùng valid mới 
        setOvertimeHours(overtimeHours);
    }

    //Getter
    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    //Setter
    public void setStatus(String status) {
        if(validStatus.contains(status)){ 
        // .contains() = kiểm tra Set có chứa giá trị này không → true/false
            this.status=status;
        }else{
            // throw = ném ra lỗi, báo ngay cho nơi gọi biết
            // IllegalArgumentException = lỗi "tham số không hợp lệ"
            throw new IllegalArgumentException("Status invalid(Present, Absent, Leave): " +status);
        }
    }
    

    public void setOvertimeHours(int overtimeHours) {
        if(overtimeHours >= 0){
            this.overtimeHours = overtimeHours;
        }else{
            throw new IllegalArgumentException("Work times must be positive");
        }
    }

    //Method    
    //chuyển đổi đối tượng Attendance thành một dòng văn bản theo định dạng CSV để ghi vào file
    public String toCSV(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.join(",", 
                            sdf.format(date), 
                            status, 
                            String.valueOf(overtimeHours));
        //VD: Khi gọi attendance.toCSV() => 15/12/2025,Present,2
        //sdf.format(date): Chuyển đối tượng Date thành chuỗi 
        //String.valueOf(overtimeHours): Chuyển số nguyên int thành chuỗi
        //String.join(",", ...): Nối các chuỗi lại với nhau, phân cách bằng dấu phẩy
        //Cú pháp: String.join(comma, element1, element2, element3)
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%-12s %-10s %d hours", 
                             sdf.format(date), status, overtimeHours);
    }


}
