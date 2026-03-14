# 📋 HRMS — Human Resource Management System

> **Project môn học | Java OOP**  
> Hệ thống quản lý nhân sự chạy trên console, hỗ trợ quản lý nhân viên, chấm công, tính lương và báo cáo.

---

## 📁 Cấu trúc project

```
hrms/
├── src/
│   ├── hrms/
│   │   ├── HRMS.java                  ← Main class, toàn bộ menu console
│   │   ├── models/
│   │   │   ├── Employee.java          ← Abstract class (lớp cha)
│   │   │   ├── Attendance.java        ← Chấm công
│   │   │   ├── FullTimeEmployee.java  ← Nhân viên toàn thời gian
│   │   │   └── PartTimeEmployee.java  ← Nhân viên bán thời gian
│   │   └── utils/
│   │       ├── Validator.java         ← Kiểm tra dữ liệu đầu vào
│   │       └── FileManager.java       ← Đọc/ghi file CSV
└── data/                              ← Tự tạo khi chạy lần đầu
    ├── employees.csv
    └── attendance.csv
```

---

## ▶️ Cách chạy

### Trong NetBeans / IntelliJ
1. Mở project, đảm bảo cấu trúc package đúng như trên
2. Chạy file `HRMS.java` (chứa `main()`)

### Compile thủ công (Terminal)
```bash
# Từ thư mục gốc chứa src/
javac -d out -sourcepath src src/hrms/HRMS.java

# Chạy
java -cp out hrms.HRMS
```

---

## 🖥️ Giao diện menu

```
======================================
      HUMAN RESOURCE MANAGEMENT
======================================
1. Manage Employees
2. Attendance Management
3. Salary Management
4. Reports
5. Exit
--------------------------------------
Choose an option: _
```

---

## ✅ Tính năng đã hoàn thiện

### 1. Manage Employees
| Task | Mô tả | Business Rule |
|------|-------|---------------|
| Add Employee | Nhập ID, tên, phòng ban, chức danh, loại NV, ngày vào, lương | BR1, BR2, BR11 |
| Update Employee | Sửa phòng ban, chức danh, lương (bỏ trống = giữ nguyên) | BR2, BR11 |
| Remove Employee | Soft delete — đánh dấu `active = false`, không xóa hẳn | BR10 |
| View All Employees | Hiển thị danh sách NV đang active dạng bảng | — |
| Search Employees | Tìm theo tên / phòng ban / chức danh | — |

**Ví dụ Add Employee:**
```
----------- ADD EMPLOYEE -----------
Employee ID: E01
Full Name: Nguyen Van An
Department: IT
Job Title: Software Engineer
Type: Full-time
Date of Joining: 01/03/2023
Basic Salary: 12000000
[1] Save  [2] Cancel
→ Employee added successfully.
```

**Ví dụ View All Employees:**
```
--------------- EMPLOYEE LIST -----------------------------------------------
ID       Name                 Department      Job Title              Salary (VND)
----------------------------------------------------------------------------
E01      Nguyen Van An        IT              Software Engineer      12,000,000
E02      Tran Thi Hoa         HR              HR Officer             10,000,000
----------------------------------------------------------------------------
Press ENTER to return...
```

---

### 2. Attendance Management
| Task | Mô tả | Business Rule |
|------|-------|---------------|
| Record Attendance | Ghi chấm công theo ngày: Present / Absent / Leave + giờ OT | BR3, BR4, BR5 |
| Update Attendance | Sửa trạng thái hoặc giờ OT của ngày đã có | BR4, BR5 |
| View Attendance History | Hiển thị toàn bộ lịch sử chấm công của 1 NV | BR6 |

**Ví dụ Record Attendance:**
```
----------- RECORD ATTENDANCE -----------
Employee ID: E01
Date: 15/12/2025
Status: Present
Overtime Hours: 2
→ Attendance recorded successfully.
```

**Ví dụ View Attendance History:**
```
Employee: Nguyen Van An (E01)
-----------------------------------------
Date          Status      Giờ OT
-----------------------------------------
15/12/2025    Present     2 hours
16/12/2025    Absent      0 hours
-----------------------------------------
Press ENTER to return...
```

---

### 3. Salary Management
| Task | Mô tả | Business Rule |
|------|-------|---------------|
| Calculate Monthly Salary | Tính lương theo tháng/năm cụ thể | BR7, BR8, BR9, BR10 |
| View Salary Details | Hiển thị chi tiết: lương cơ bản + OT + khấu trừ | BR7 |
| Generate Salary Report | Bảng lương tất cả NV active trong tháng | BR13 |

**Công thức tính lương (BR7):**
```
Tổng lương = Lương cơ bản
           + (Số giờ OT × Đơn giá OT)
           - (Số ngày vắng × 100,000)
```

| Loại NV | Đơn giá OT (BR8) | Trừ vắng (BR9) |
|---------|-----------------|----------------|
| Full-time | 80,000 VND/giờ | 100,000 VND/ngày |
| Part-time | 50,000 VND/giờ | 100,000 VND/ngày |

**Ví dụ Calculate Salary:**
```
----------- CALCULATE SALARY -----------
Employee ID: E01
Month: 12 / Year: 2025
→ Salary calculated successfully.
   Employee       : Nguyen Van An
   Total Working Days : 22
   Overtime Hours     : 10
   Absence Days       : 1
   Total Salary       : 12,700,000 VND
```

---

### 4. Reports
| Task | Mô tả | Business Rule |
|------|-------|---------------|
| Low Attendance | Danh sách NV vắng nhiều hơn ngưỡng trong tháng | BR12 |
| Highest Paid | Xếp hạng NV theo tổng lương tháng (giảm dần) | BR13 |

**Ví dụ Low Attendance (ngưỡng mặc định = 3 ngày):**
```
----------- LOW ATTENDANCE REPORT -----------
E02      Tran Thi Hoa         5 days
--------------------------------------------
Press ENTER to return...
```

**Ví dụ Highest Paid:**
```
----------- HIGHEST PAID EMPLOYEES -----------
Rank  ID       Name                    Total Salary (VND)
1     E01      Nguyen Van An              12,700,000
2     E02      Tran Thi Hoa              10,000,000
----------------------------------------------
Press ENTER to return...
```

---

## 💾 File I/O (Milestone 4)

Dữ liệu được **tự động load** khi khởi động và **tự động lưu** khi thoát (chọn Exit).

### employees.csv
```
id,name,department,jobTitle,joinDate,basicSalary,active,type
E01,Nguyen Van An,IT,Software Engineer,01/03/2023,12000000.0,true,Full-time
E02,Tran Thi Hoa,HR,HR Officer,15/06/2022,10000000.0,true,Part-time
```

### attendance.csv
```
employeeId,date,status,overtimeHours
E01,15/12/2025,Present,2
E01,16/12/2025,Absent,0
E02,15/12/2025,Present,0
```

---

## 🏗️ Thiết kế OOP

### Class Diagram (tóm tắt)
```
          «abstract»
          Employee
         /         \
FullTimeEmployee  PartTimeEmployee
       (uses)
      Attendance
```

### Encapsulation
- Tất cả thuộc tính của `Employee` và `Attendance` đều là `private`
- Truy cập qua Getter/Setter có kiểm tra hợp lệ

### Inheritance
- `FullTimeEmployee` và `PartTimeEmployee` đều `extends Employee`
- Dùng `super(...)` trong constructor để tái sử dụng code của lớp cha

### Polymorphism
- `calculateSalary()` và `calculateSalaryInMonth()` là `abstract` ở `Employee`
- Mỗi lớp con Override theo cách tính riêng (khác `OVERTIME_RATE`)
- Gọi `emp.calculateSalaryInMonth(...)` → Java tự chọn đúng phiên bản

### Collections sử dụng
| Collection | Nơi dùng | Mục đích |
|-----------|---------|---------|
| `List<Employee>` | `HRMS.java` | Lưu danh sách NV theo thứ tự |
| `Map<String, Employee>` | `HRMS.java` | Tìm NV theo ID nhanh O(1) |
| `Set<String> usedIds` | `HRMS.java` | Kiểm tra ID trùng BR1 — O(1) |
| `List<Attendance>` | `Employee.java` | Lưu lịch sử chấm công |
| `Map<String, Attendance>` | `Employee.java` | Kiểm tra trùng ngày BR4 — O(1) |
| `Set<String> validStatus` | `Attendance.java` | Validate status hợp lệ BR5 |
| `Map<Employee, Double>` | `HRMS.java` | Sort lương cao nhất BR13 |

### Exception Handling
- `try-catch` bao quanh toàn bộ parse ngày, parse số
- Khi đọc file: dòng nào lỗi → in cảnh báo + bỏ qua → **không crash chương trình**
- `throw new IllegalArgumentException` trong Setter khi dữ liệu không hợp lệ

---

## 📐 Business Rules

| Rule | Mô tả | Xử lý ở |
|------|-------|---------|
| BR1 | Employee ID phải unique, không thay đổi được | `usedIds` (Set) + không có `setId()` |
| BR2 | Tên và phòng ban không được rỗng | `Validator.isValidName/Department()` |
| BR3 | NV phải tồn tại trước khi chấm công | `employeeMap.get(id)` kiểm tra null |
| BR4 | Mỗi ngày chỉ chấm công 1 lần / NV | `attendanceMap.containsKey(dateKey)` |
| BR5 | Status: Present / Absent / Leave | `Set<String> validStatus` trong `Attendance` |
| BR6 | Ngày làm tính từ bản ghi chấm công | `countWorkingDaysInMonth()` dùng `Calendar` |
| BR7 | Lương = Cơ bản + OT − Vắng | `calculateSalaryInMonth()` |
| BR8 | OT: 80k (Full-time), 50k (Part-time) | Hằng số `OVERTIME_RATE` trong mỗi lớp con |
| BR9 | Trừ 100k/ngày vắng | Hằng số `ABSENCE_DEDUCTION` |
| BR10 | Chỉ tính lương NV đang active | `if (!isActive()) return 0` |
| BR11 | Validate toàn bộ input | `Validator.java` |
| BR12 | Low attendance: vắng > ngưỡng/tháng | `countAbsentDaysInMonth() > limit` |
| BR13 | Highest paid theo tổng lương tháng | `LinkedHashMap` + lambda sort giảm dần |
