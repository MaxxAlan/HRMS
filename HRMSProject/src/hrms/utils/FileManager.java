
package hrms.utils;

import hrms.models.*;
import java.io.*;   
// java.io.*: import tất cả class trong gói java.io (File, BufferedReader, BufferedWriter, v.v.)
import java.text.ParseException;
// ParseException: lỗi khi chuyển chuỗi → Date thất bại (vd: "abc" không phải ngày hợp lệ)
import java.text.SimpleDateFormat;
import java.util.*;


public class FileManager {
    
    private static final String dataDir = "data/";
    private static final String employeeFile = dataDir + "employees.csv";
    private static final String attendanceFile = dataDir + "attendance.csv";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    
    // ===============================================
    // PHẦN GHI FILE (SAVE)
    // ===============================================
    
    //"throws IOException": báo nơi gọi phải dùng try-catch bắt lỗi file
    public static void saveEmployees(List<Employee> employees) throws IOException{
        // Tạo thư mục data/ nếu chưa có
        File dir = new File(dataDir);
        
        if(!dir.exists()) dir.mkdirs(); // mkdirs(): tạo thư mục (kể cả thư mục cha nếu chưa có)
           
        //BufferedWriter: ghi nhanh hơn nhờ buffer (gom nhiều ký tự, ghi 1 lần)
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(employeeFile))){
            // Ghi dòng tiêu đề 
            bw.write("id,name,department,jobTitle,joinDate,basicSalary,active,type");
            bw.newLine();
            
            for (Employee emp : employees) {
                bw.write(emp.toCSV());  // toCSV() định nghĩa trong Employee
                bw.newLine();
            }
            // Kết quả file:
            // id,name,department,...
            // E01,Phuong Le,IT,...
            // E02,Phuong Tay,HR,...
        }
        // BufferedWriter tự đóng ở đây (try-with-resources)
    }
    
    // Ghi toàn bộ lịch sử chấm công ra file CSV
    public static void saveAttendance(List<Employee> employees) throws IOException{
        File dir = new File(dataDir);
        
        if(!dir.exists()) dir.mkdirs();
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(attendanceFile))){
            bw.write("employeeId,date,status,overtimeHours");
            bw.newLine();
            
            //Vòng lặp lồng: mỗi NV → ghi toàn bộ attendance của NV đó
            for (Employee emp : employees) {
                for (Attendance att : emp.getAttendanceList()) {
                    // Ví dụ 1 dòng: "E01,15/12/2025,Present,2"
                    bw.write(emp.getId() + "," + att.toCSV());
                    bw.newLine();
                }
            }
        }
    }
    // ================================================================
    // PHẦN ĐỌC FILE (LOAD)
    // ================================================================
    
    // Đọc danh sách NV từ file CSV, trả về List<Employee>
    public static List<Employee> loadEmployees() throws IOException{
        List<Employee> employees = new ArrayList<>();
        File file = new File(employeeFile);
        
        //chưa có file => trả về list rỗng
        if(!file.exists()){
            System.out.println("Not have file employee yet");
            return employees;
        }
        
        // BufferedReader: đọc file nhanh hơn nhờ buffer
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            boolean isHeader = true;
            int lineNumber = 0;
            
            // br.readLine(): đọc 1 dòng, trả về null khi hết file
            while((line = br.readLine()) != null){
                lineNumber++;
                
                if(isHeader){
                    isHeader=false; // đánh dấu đã qua header
                    continue;
                }
                
                if(line.trim().isEmpty())   continue; // bỏ qua dòng trống
                
                try {
                    Employee emp = parseEmployeeLine(line);
                    employees.add(emp);
                } catch (Exception e) {
                    System.out.println("Skip line" + lineNumber + "error: " +e.getMessage());
                }
            }
        }
        
        System.out.println("Load info" + employees.size() + "employees");
        return employees;
    }

    
    // Đọc lịch sử chấm công từ file CSV, gắn vào đúng NV trong List
    // Nhận vào List<Employee> đã load để tìm đúng NV cần gắn attendance
    public static void loadAttendance(List<Employee> employees) throws IOException{
        File file = new File(attendanceFile);
        if(!file.exists()){
            System.out.println("Attendance file is not exist yet");
            return;
        }
        
        // Tạo Map<String, Employee> để lookup nhanh theo ID
        Map<String, Employee> empMap = new HashMap<>();
        for (Employee emp : employees) {
            empMap.put(emp.getId(), emp);
            // put(key, value): thêm cặp vào Map
            // key = "E01", value = đối tượng Employee đó
        }
        
        int loaded = 0;     // đếm số bản ghi đọc thành công
        int skipped = 0;    // đếm số bản ghi bỏ qua do lỗi
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            boolean isHeader = true;
            int lineNumber=0;
            
            while((line = br.readLine()) != null){
                lineNumber++;
                
                if(isHeader){
                    isHeader = false;
                    continue;
                }
                
                if(line.trim().isEmpty())   continue;
                
                try {
                    // Tách dòng CSV bằng dấu phẩy
                    String[] parts = line.split(",");
                    
                    if(parts.length < 4){
                        throw new IllegalArgumentException("Missing data");
                    }
                    
                    String empId = parts[0].trim();
                    String dateStr      = parts[1].trim();
                    String status       = parts[2].trim();
                    int overtimeHours   = Integer.parseInt(parts[3].trim());
                    
                    // Tìm nhân viên theo ID trong Map
                    Employee emp = empMap.get(empId);
                    if(emp == null){
                         throw new IllegalArgumentException("Not found employee: " + empId);
                    }
                    
                    // Chuyển chuỗi => Date
                    Date date = dateFormat.parse(dateStr);
                    // .parse(): ném ParseException nếu dateStr sai format => vào catch bên ngoài
                    
                    Attendance att = new Attendance(date, status, overtimeHours);
                    emp.addAttendance(att);     //bỏ vào attendanceList!
                    loaded++;
                    
                } catch (Exception e) {
                    System.out.println("Skip line" + lineNumber + ":" +e.getMessage());
                    skipped++;
                }
            }
        }
        System.out.println("Load complety: " + loaded + "Skip: " + skipped);
    }
    
    // ================================================================
    // PHƯƠNG THỨC PRIVATE
    // ================================================================
    
    // Phân tích 1 dòng CSV → tạo đối tượng Employee phù hợp
    private static Employee parseEmployeeLine(String line) throws ParseException{
        String[] parts = line.split(",");
        
        // Kiểm tra đủ 8 cột: id,name,department,jobTitle,joinDate,basicSalary,active,type
        if(parts.length < 8){
            throw new IllegalArgumentException("Data missing (need 8): " + parts.length);
        }
        
        String id  = parts[0].trim();
        String name  = parts[1].trim();
        String department = parts[2].trim();
        String jobTitle = parts[3].trim();
        Date joinDate = dateFormat.parse(parts[4].trim());// String → Date
        double basicSalary = Double.parseDouble(parts[5].trim());// String → double
        boolean active = Boolean.parseBoolean(parts[6].trim()); // "true"/"false" → boolean
        String type = parts[7].trim();
        
        if(type.equalsIgnoreCase("Full-time")){
            return new FullTimeEmployee(id, name, department, jobTitle, joinDate, basicSalary, active);
        }else if(type.equalsIgnoreCase("Part-time")){
            return new PartTimeEmployee(id, name, department, jobTitle, joinDate, basicSalary, active);
        }else{
            throw new IllegalArgumentException("Invalid employee type" + type);
        }
    }
    
}
