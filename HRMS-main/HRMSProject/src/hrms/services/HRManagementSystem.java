package hrms.services;

import hrms.models.Employee;
import hrms.models.Attendance;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class HRManagementSystem {
    private Map<String, Employee> employees;

    public HRManagementSystem() {
        this.employees = new HashMap<>();
    }

    public void addEmployee(Employee emp) {
        employees.put(emp.getId(), emp);
    }

    public Employee getEmployee(String id) {
        return employees.get(id);
    }

    public Map<String, Employee> getAllEmployees() {
        return employees;
    }

    public List<Employee> searchEmployees(String keyword) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees.values()) {
            if (emp.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(emp);
            }
        }
        return result;
    }

    public void recordAttendance(String employeeId, Attendance att) {
        Employee emp = employees.get(employeeId);
        if (emp != null) {
            emp.addAttendance(att);
        }
    }

    // Các phương thức tính toán lương và báo cáo sẽ được gọi thông qua các object Employee
}
