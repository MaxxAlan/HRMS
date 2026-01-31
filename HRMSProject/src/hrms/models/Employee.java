package hrms.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public double getBasicSalary() {
        return basicSalary;
    }

    public boolean isActive() {
        return active;
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public String getJobTitle() {
        return jobTitle;
    }
    
    

    //Setter
    public void setName(String name) {
        if(name != null && name.trim().isEmpty()){
            this.name=name;
        }
    }
    //name != null: không được là null
    //.trim(): Xóa tất cả space ở ĐẦU và CUỐI chuỗi
    //.isEmpty(): Kiểm tra xem chuỗi có rống ko ( boolean isEmpty() )
    //Tên PHẢI tồn tại (không được null), Sau khi cắt space, tên KHÔNG được rỗng 
    
    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    //ko setter id vì mỗi id là unique, ko thể tùy chỉnh sau khi tạo 
    
    
    //method
    public abstract double calculateSalary();
    //Mọi nv đều phải có cách tính Salary, nhưng mỗi loại tính theo cách KHÁC NHAU
    //Tương tự như FullTimeEmployee có CÁCH TÍNH CỦA MIK, PartTimeEmployee cuz có CÁCH TÍNH CỦA MIK

    @Override
    public String toString() {
        return id + "-" + name + "-" + department + "-" + jobTitle + "-" + basicSalary + "VND"
                + countWorkingDays();
    }
    
    public void addAttendance(Attendance attendance){
        if(attendance!=null){
            attendanceList.add(attendance);
        }
    }
    //attendanceList: Danh sách lưu các điểm danh
    //.add(): Phương thức thêm phần tử vào danh sách
    //--> Thêm đối tượng attendance vào danh sách
    
    public int countWorkingDays(){
        int countWorkings =0;
        for (Attendance att : attendanceList) {
            if ("Present".equals(att.getStatus())) {
                countWorkings++;
            }
        }
        return countWorkings;
    }
    
    public boolean checkLowAttendance(int maxAbsentDays){
        int absentCount=0;
        for (Attendance att : attendanceList) {
            if ("Absent".equals(att.getStatus())) {
                absentCount++;
            }
        }
        return absentCount > maxAbsentDays;
    }
    
    
}
