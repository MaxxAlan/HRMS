# HƯỚNG DẪN HRMS ĐƠN GIẢN

## 📌 ĐẶC ĐIỂM CODE ĐƠN GIẢN

Code này được viết **CỰC KỲ ĐỠN GIẢN** cho người mới học Java:

✅ **Không dùng Helper Methods phức tạp** - Tất cả code viết trực tiếp trong từng function
✅ **Dùng if-else thay vì switch** - Dễ đọc hơn cho người mới
✅ **Validate đơn giản** - Chỉ check những điều cần thiết
✅ **Không có try-catch lồng nhau** - Mỗi chỗ 1 try-catch đơn giản
✅ **Comment ít hơn** - Chỉ comment chỗ quan trọng
✅ **Format giống y PDF** - Theo đúng yêu cầu đề bài

## 🎯 5 CHỨC NĂNG ĐÃ LÀM

### 1️⃣ Add Employee (Thêm nhân viên)
```
Nhập theo thứ tự:
- Employee ID
- Full Name  
- Department
- Job Title
- Type (Full-time/Part-time)
- Date of Joining (dd/MM/yyyy)
- Basic Salary

Sau đó chọn:
[1] Save [2] Cancel
```

**Validate:**
- ID không được rỗng
- ID không được trùng (BR1)
- Name không được rỗng (BR2)
- Department không được rỗng (BR2)
- Date phải đúng format
- Salary phải > 0

### 2️⃣ Update Employee (Cập nhật)
```
Nhập Employee ID
→ Hiển thị thông tin hiện tại
→ Nhập thông tin mới (để trống = skip)
→ [1] Update [2] Cancel
```

**Có thể cập nhật:**
- Department
- Job Title

**Không thể cập nhật:**
- ID (theo BR1)

### 3️⃣ Remove Employee (Xóa nhân viên)
```
Nhập Employee ID
→ Xác nhận yes/no
→ Deactivate (không xóa hẳn)
```

### 4️⃣ View All Employees (Xem danh sách)
```
Hiển thị dạng bảng:
ID | Name | Department | Job Title | Salary
```

### 5️⃣ Search Employee (Tìm kiếm)
```
Chọn tìm theo:
1. Name
2. Department  
3. Job Title

Nhập từ khóa → Hiển thị kết quả
```

## 💡 SO SÁNH 2 PHIÊN BẢN

### Version 1 (HRMS.java) - PHỨC TẠP HƠN
```java
// Dùng helper methods
private static String inputEmployeeId() {
    while (true) {
        // ... nhiều code ...
    }
}

// Dùng switch-case
switch (choice) {
    case "1": addEmployee(); break;
    case "2": searchEmployee(); break;
    ...
}
```

### Version 2 (HRMS_Simple.java) - ĐƠN GIẢN
```java
// Không dùng helper, viết trực tiếp
System.out.print("Employee ID: ");
String id = sc.nextLine().trim();
if (id.isEmpty()) {
    System.out.println("Error!");
    return;
}

// Dùng if-else
if (choice.equals("1")) {
    addEmployee();
} else if (choice.equals("2")) {
    searchEmployee();
}
```

## 📖 CODE DỄ HIỂU NHƯ THẾ NÀO?

### Ví dụ 1: Thêm nhân viên
```java
static void addEmployee() {
    // Bước 1: Nhập ID
    System.out.print("Employee ID: ");
    String id = sc.nextLine().trim();
    
    // Bước 2: Check rỗng
    if (id.isEmpty()) {
        System.out.println("Error!");
        return; // Dừng luôn, không làm gì nữa
    }
    
    // Bước 3: Check trùng
    for (Employee emp : employeeList) {
        if (emp.getId().equals(id)) {
            System.out.println("ID exists!");
            return;
        }
    }
    
    // Bước 4: Nhập name...
    // ... cứ thế tiếp tục
}
```

**Dễ hiểu vì:**
- Từng bước rõ ràng
- Không có method phụ
- Có lỗi là return ngay
- Đọc từ trên xuống dưới

### Ví dụ 2: Tìm nhân viên
```java
// Không dùng findEmployeeById(id)
// Mà viết trực tiếp:

Employee emp = null;
for (Employee e : employeeList) {
    if (e.getId().equals(id)) {
        emp = e;
        break;
    }
}

if (emp == null) {
    System.out.println("Not found!");
}
```

## 🔧 CÁC ĐIỂM LƯU Ý

### 1. Static variables
```java
static List<Employee> employeeList = new ArrayList<>();
static Scanner sc = new Scanner(System.in);
```
- Dùng `static` để dùng chung trong tất cả methods
- Không cần tạo object HRMS

### 2. Validation đơn giản
```java
// Check rỗng
if (id.isEmpty()) {
    System.out.println("Error!");
    return;
}

// Check trùng
for (Employee emp : employeeList) {
    if (emp.getId().equals(id)) {
        System.out.println("Error!");
        return;
    }
}
```

### 3. Try-catch đơn giản
```java
try {
    joinDate = dateFormat.parse(dateStr);
} catch (Exception e) {
    System.out.println("Error: Invalid date!");
    return;
}
```

### 4. Format output
```java
// Format số có dấu phẩy
String.format("%,.0f", emp.getBasicSalary())
// 10000000 → 10,000,000

// Format bảng với printf
System.out.printf("%-8s %-20s %-15s%n", "ID", "Name", "Department");
```

## ⚙️ CÁCH CHẠY

```bash
# 1. Compile các class models trước
javac hrms/models/*.java

# 2. Compile HRMS_Simple
javac hrms/HRMS_Simple.java

# 3. Run
java hrms.HRMS_Simple
```

## 📝 VÍ DỤ SỬ DỤNG

### Test Case 1: Thêm nhân viên Full-time
```
Employee ID: E01
Full Name: Nguyen Van An
Department: IT
Job Title: Software Engineer
Type: Full-time
Date of Joining: 01/03/2023
Basic Salary: 12000000
[1] Save [2] Cancel
Choose: 1

→ Output: Employee added successfully.
```

### Test Case 2: Thêm nhân viên Part-time
```
Employee ID: E02
Full Name: Tran Thi Hoa
Department: HR
Job Title: HR Officer
Type: Part-time
Date of Joining: 15/06/2023
Basic Salary: 10000000
[1] Save [2] Cancel
Choose: 1

→ Output: Employee added successfully.
```

### Test Case 3: Update thông tin
```
Enter Employee ID to update: E01

Current Information:
Name: Nguyen Van An
Department: IT
Job Title: Software Engineer
Basic Salary: 12,000,000

Enter new Department (leave blank to skip): R&D
Enter new Job Title (leave blank): Senior Engineer

[1] Update [2] Cancel
Choose: 1

→ Output: Employee updated successfully.
```

### Test Case 4: Tìm kiếm theo tên
```
Search by:
1. Name
2. Department
3. Job Title
Choose (1-3): 1

Enter name: nguyen

--- SEARCH RESULTS ---
E01 - Nguyen Van An - R&D
Found: 1 employee(s)
```

## ❌ CÁC LỖI THƯỜNG GẶP

### Lỗi 1: ID trùng
```
Employee ID: E01
Error: ID already exists!
```

### Lỗi 2: Name rỗng
```
Full Name: 
Error: Name cannot be empty!
```

### Lỗi 3: Date sai format
```
Date of Joining: 2023-01-15
Error: Invalid date format!

✓ Đúng: 15/01/2023
```

### Lỗi 4: Salary không hợp lệ
```
Basic Salary: abc
Error: Invalid salary!

Basic Salary: -1000
Error: Salary must be greater than 0!
```

## 🎓 HỌC ĐƯỢC GÌ TỪ CODE NÀY?

1. **Cấu trúc Menu đơn giản** với if-else
2. **Validate input cơ bản** (rỗng, trùng, format)
3. **Dùng ArrayList** để lưu danh sách
4. **Dùng for-each loop** để duyệt danh sách
5. **Parse Date** từ String
6. **Format output** đẹp với printf
7. **Try-catch** xử lý exception cơ bản

## 🆚 KHI NÀO DÙNG VERSION NÀO?

**Dùng HRMS_Simple.java nếu:**
- ✅ Mới học Java
- ✅ Muốn code ngắn gọn
- ✅ Chưa quen helper methods
- ✅ Chỉ cần pass assignment

**Dùng HRMS.java nếu:**
- ✅ Muốn code chuyên nghiệp hơn
- ✅ Hiểu về refactoring
- ✅ Muốn tái sử dụng code
- ✅ Làm project thật

## 💪 THÁCH THỨC TIẾP THEO

Sau khi hiểu code này, bạn có thể:

1. **Thêm Attendance Management** (BR3, BR4, BR5)
2. **Thêm Salary Management** (BR7, BR8, BR9)
3. **Thêm Reports** (BR12, BR13)
4. **Lưu file** (File I/O)
5. **Refactor** thành code đẹp hơn

---

**Chúc bạn học tốt! 🚀**
