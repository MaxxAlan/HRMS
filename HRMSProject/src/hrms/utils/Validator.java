
package hrms.utils;
import hrms.models.*;

public class Validator {
    // BR1: ID không được rỗng
    public static boolean isValidId(String id){
        return id != null && !id.trim().isEmpty();
    }
    
    // BR2: Tên không được rỗng
    public static boolean isValidName(String name){
        return name != null && !name.trim().isEmpty();
    }
    
    // BR2: Phòng ban không được rỗng
    public static boolean  isValidDepartment(String department){
        return department != null && !department.trim().isEmpty();
    }
    
    public static boolean isValidJobTitle(String jobTitle){
        return jobTitle != null && !jobTitle.trim().isEmpty();
    }
    
    // BR5: Status chấm công phải là "Present", "Absent", hoặc "Leave"
    public static boolean isValidAttendanceStatus(String status){
        // Attendance.validStatus: truy cập hằng số static của class Attendance
        return Attendance.validStatus.contains(status);
    }
    
    // Kiểm tra chuỗi có phải số nguyên DƯƠNG không (> 0)
    // Dùng cho: limit ngày vắng, v.v.
    public static boolean isPositiveInt(String input){
        try {
            return Integer.parseInt(input) > 0;
        } catch (NumberFormatException e) {
            // NumberFormatException: lỗi khi chuỗi không phải số hợp lệ
            return false;
        }
    }
    
    // Kiểm tra chuỗi có phải số thực DƯƠNG không (> 0)
    // Dùng cho: lương cơ bản
    public static boolean isPositiveDouble(String input){
        try {
            return Double.parseDouble(input) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // Kt số giờ làm thêm hợp lệ (>= 0, vì 0 = không làm thêm)
    public static boolean isValidOvertimeHours(String input){
        try {
            return Integer.parseInt(input) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // Kt tháng hợp lệ: phải trong khoảng 1 đến 12
    public static boolean isValidMonth(String input){
        try {
            int m = Integer.parseInt(input);
            return m>=1 && m<=12;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidYear(String input){
        try {
            int y = Integer.parseInt(input);
             return y >= 2000 && y <= 2500;
        } catch (Exception e) {
            return false;
        }
    }
    
}
