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
    private List<Attendance> attendance = new ArrayList<>();
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    //ko setter id vì mỗi id là unique, ko thể tùy chỉnh sau khi tạo 

    public void addAttendance(Attendance attendance) {
        //code here
    }

    public void updateAttendance(Date date, String status, int overtimeHours) {
        //code here
    }

    public abstract double caculateSalary(int month, int year);
    //Mọi nv đều phải có cách tính Salary, nhưng mỗi loại tính theo cách KHÁC NHAU
    //Tương tự như FullTimeEmployee có CÁCH TÍNH CỦA MIK, PartTimeEmployee cuz có CÁCH TÍNH CỦA MIK

}
