package hrms.services;

import hrms.models.Attendance;
import hrms.models.Employee;
import java.util.ArrayList;
import java.util.List;

public class HRManagementSystem {

    private List<Employee> employees = new ArrayList<>();
    //Tạo một DANH SÁCH RỖNG để chứa nhân viên 
    //List,Employee>: Danh sách chứa các đối tượng Employee -->Tủ chứa nhiều hồ sơ nhân viên
    //employees: Tên danh sách: "các nhân viên"	-->Nhãn dán "HỒ SƠ NHÂN VIÊN"
    //new ArrayList<>(): Tạo tủ trống mới -->Mua tủ mới mỗi khi có nhân viên mới 

    
    
    public void addEmployee(Employee employee) { //có thể code thêm nếu cần 
        employees.add(employee);
        //employees: danh sách nhân viên --> cái tủ
        //.add: thêm vào                 --> mở tủ, để vào
        //(employee): nhân viên cần thêm --> hồ sơ cụ thể 
        
        
    }
    //Employee employee: Tham số: Một đối tượng Employee
    //-->Hồ sơ nhân viên cụ thể (có đầy đủ thông tin)

    public void updateEmployee(Employee employee) {
        //code here
    }

    public void removeEmployee(String id) {
        //code here
    }

    public List<Employee> getAllEmployees() {
        //return code here
    }

    public List<Employee> searchEmployees(String keyword) {
        //return code here
    }

    public void recordAttendance(String id, Attendance attendance) {
        //code here
    }//phần này hơi lord nên có thể coi chừng  

    public List<Attendance> viewAttendanceHistory(String id) {
        //return code here
    }

    public double caculateSalary(String id, int month, int year) {
        //code here + return 
    }

    public void generateSalaryReport(int month, int year) {
        //code here
    }

    public List<Employee> listLowAttendance() {
        //returen code here
    }

    public List<Employee> listHighestPaid() {
        //return code here
    }

}
