package hrms.models;

import java.util.Date;

public class FullTimeEmployee extends Employee {
    //extends: tính kế thừa
    //nhận tất cả thuộc tính, phương thức từ class Employee
    //có thể thêm thuộc tính mới (nếu muốn)

    private static final double OVERTIME_RATE = 80000;
    //tương tự như const (hằng số) trg C
    //mọi nhân viên FullTime làm thêm đều đc 80k, ko bao đổi 

    public FullTimeEmployee(String id, String name, String department, String jobTitle, Date joinDate, double basicSalary, boolean active) {
        super(id, name, department, jobTitle, joinDate, basicSalary, active);
    }// có thể insert constructor này khi đã có extends Employee
    // super: gọi constructor của class cha (Employee)  
    // nhằm tránh repeat vs code construct trg Employee 

    @Override
    //IMPORTANT 
    //báo cho Java bik: đang GHI ĐÈ phương thức của Class cha (Employee)
    //nếu viết sai tên/tham số, java sẽ báo lỗi ngay 
    public double caculateSalary(int month, int year) {
        //code here
    }

}
