package hrms.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Employee {
    //Lớp trừu tượng (abstract), không thể tạo đối tượng trực tiếp
    //Dùng làm lớp cha để các lớp con kế thừa và triển khai
    //Dùng tránh lỗi compile giữa FullTimeEmployee và PartTimeEmployee

    private String id;
    private String name;
    private String department;
    private String jobTitle;
    private Date joinDate;
    protected double basicSalary; //protected để basicSalary xài chung cho các class trg package
    private boolean active;
    
    private List<Attendance> attendanceList = new ArrayList<>();
    //List<Attendance>: List chứa các Attendance (Vd: mục chấm công+tráng thái+OT)
    //attendance: tên biến và là thuộc tính class Employee
    //new ArrayList<>(): tạo mới list mỗi khi có nhân viên mới (phải có new nếu ko là error)
    
    private Map<String, Attendance> attendanceMap = new HashMap<>();
    // Map<String, Attendance>: key = ngày ("15/12/2025"), value = Attendance của ngày đó
    // Dùng để: kiểm tra BR4 (mỗi ngày chỉ chấm công 1 lần)
    
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    
    public Employee(String id, String name, String department, String jobTitle,
            Date joinDate, double basicSalary, boolean active) {
        this.id = id;
        this.name = name;
        this.department = department; //department: Phòng ban/Bộ phận 
        this.jobTitle = jobTitle;
        this.joinDate = joinDate;
        this.basicSalary = basicSalary;
        this.active = active;
    }

    //Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public boolean isActive() {
        return active;
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }
    
    //Setter
    // BR2: tên không được rỗng hay null
    public void setName(String name) {
        //name != null: không được là null
        //.trim(): Xóa tất cả space ở ĐẦU và CUỐI chuỗi
        //.isEmpty(): Kiểm tra xem chuỗi có rống ko ( boolean isEmpty() )
        //Tên PHẢI tồn tại (không được null), Sau khi cắt space, tên KHÔNG được rỗng 
        if(name != null && !name.trim().isEmpty()){
            this.name=name;
        }
    }
    
    
    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }
     //ko setter id vì mỗi id là unique, ko thể tùy chỉnh sau khi tạo 
    
    
    //METHOD ABSTRACT
    public abstract double calculateSalary();   //Tính lương toàn thời gian
    //Mọi nv đều phải có cách tính Salary, nhưng mỗi loại tính theo cách KHÁC NHAU
    //Tương tự như FullTimeEmployee có CÁCH TÍNH CỦA MIK, PartTimeEmployee cuz có CÁCH TÍNH CỦA MIK
    public abstract double calculateSalaryInMonth(int month, int year); //tính lương theo tháng(BR7, BR13)
    public abstract String getEmployeeType();      //trả về Full-time/Part-time

    //METHOD UTILS  
    @Override
    public String toString() {
        return String.format("%-8s %-20s %-15s %-20s %,12.0f  %-10s", 
                                id,
                                name,
                                department,
                                jobTitle,
                                basicSalary,
                                getEmployeeType());
    }
    
    public String toCSV(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.join(",", 
                                id, name, department, jobTitle, 
                                sdf.format(joinDate), 
                                String.valueOf(basicSalary), // double → String
                                String.valueOf(active),      // boolean → "true"/"false"
                                getEmployeeType());            // "Full-time" hoặc "Part-time"
    }
    
    //OTHER METHODS
    
    //Thêm bản ghi chấm công, chỉ thêm nếu ngày đó chưa có chấm công (BR4) 
    public boolean addAttendance(Attendance attendance){
        if(attendance == null)  return false;   //Tránh NullOfPointer
        
        // Chuyển Date → chuỗi "dd/MM/yyyy" để dùng làm key của Map
        String dateKey = dateFormat.format(attendance.getDate());
        
        // BR4: kiểm tra ngày này đã có chấm công chưa
        // Map.containsKey() = kiểm tra key có tồn tại trong Map không
        if(attendanceMap.containsKey(dateKey))  return false;  // đã có => từ chối thêm
            
        attendanceList.add(attendance);                 // List: để hiển thị theo thứ tự
        attendanceMap.put(dateKey, attendance);  // Map: để kiểm tra trùng ngày nhanh
        return true;
    }
    
    //Update bản chấm công 
    public boolean updateAttendance(String dataKey, String newStatus, int newOvertimeHours){
        // Map.get(key) = lấy value theo key => null nếu không tìm thấy
        Attendance att = attendanceMap.get(dataKey);
        if(att == null) return false;
        
        att.setStatus(newStatus);
        att.setOvertimeHours(newOvertimeHours);
        return true;
    }
    
    //Kiểm tra ngày đã có chấm công chưa - dùng trong attendanceMenu (BR4)
    public boolean hasAttendanceOnDate(String dateKey){
        return attendanceMap.containsKey(dateKey);
    }
    
    //Đếm tổng ngày đi làm
    public int countWorkingDays(){
        int count = 0;
        for (Attendance att : attendanceList) {
            if("Present".equals(att.getStatus())){
                count++;
            }
        }
        return count;
    }
    //Đếm tổng ngày vắng 
    public int countAbsentDays(){
        int count = 0;
        for (Attendance att : attendanceList) {
            if("Absent".equals(att.getStatus())){
                count++;
            }
        }
        return count;
    }
    
    // Đếm ngày đi làm trong tháng/năm cụ thể (BR6)
    public int countWorkingDaysInMonth(int month, int year){
        int count=0;
        
        Calendar cal = Calendar.getInstance();  // Tạo Calendar
        //Calendar.getInstance(): trả về đối tượng Calendar với thời gian hiện tại
        
        for (Attendance att : attendanceList) {
            cal.setTime(att.getDate());    // Set date vào Calendar
            //Đặt ngày của bản ghi vào Calendar
            //Calendar sẽ nhớ att.getDate() để lấy thông tin tháng, năm
            
            int attMonth = cal.get(Calendar.MONTH)+1; //Tháng (0-11) → +1
            int attYear = cal.get(Calendar.YEAR);
            
            if(attMonth==month && attYear==year && "Present".equals(att.getStatus())){
                count++;
            }
        }
        return count;
    }
    
    // Đếm ngày vắng trong tháng/năm (BR12)
    public int countAbsentDaysInMonth(int month, int year){
        int count=0;
        Calendar cal = Calendar.getInstance();
        
        for (Attendance att : attendanceList) {
            cal.setTime(att.getDate());
            
            int attMonth = cal.get(Calendar.MONTH) + 1;
            int attYear = cal.get(Calendar.YEAR);
            
            if(attMonth == month && attYear == year && "Absent".equals(att.getStatus())){
                count++;
            }
        }
        return count;
    }
    
    // Đếm tổng giờ làm thêm trong tháng/năm (BR8)
    public int countOvertimeHoursInMonth(int month, int year){
        int total = 0;
        Calendar cal = Calendar.getInstance();
        
        for (Attendance att : attendanceList) {
            cal.setTime(att.getDate());
            
            int attMonth = cal.get(Calendar.MONTH) + 1;
            int attYear = cal.get(Calendar.YEAR);
            
            if(attMonth==month && attYear==year && "Present".equals(att.getStatus())){
                total += att.getOvertimeHours();
            }
        }
        return total;
    }
    
    public boolean checkLowAttendanceInMonth(int month, int year, int limit){
        return countAbsentDaysInMonth(month, year) > limit;
    }
}
