# HRMS — Hệ thống quản lý nhân sự

Mô tả ngắn
- HRMS — Phần mềm quản lý nhân lực công ty. (Repo: MaxxAlan/HRMS)
- Ngôn ngữ chính: Java (100%)

Tóm tắt trạng thái hiện tại
- Loại dự án: Ứng dụng console Java (NetBeans / Ant project).
- Java target: 1.8 (xác định từ `nbproject/project.properties`).
- Build: Ant / NetBeans (file: `HRMSProject/build.xml`, `nbproject/`).
- Trạng thái tổng quát: Đã có khung cơ bản của backend console và mô hình dữ liệu; tính năng chính chấp nhận đầu vào qua console. Nhiều module trợ giúp, ngoại lệ, và persistence chưa triển khai đầy đủ.
  - Giai đoạn: Development (proto / working prototype trên console).
  - Không có CI, không có tests tự động (chưa thấy `src/test` thực thi).
  - Chưa có cơ chế lưu trữ dữ liệu (hiện tại lưu trong memory: `List<Employee>`).

Bằng chứng từ mã nguồn (những file chính đã kiểm tra)
- Project config
  - `HRMSProject/build.xml` (Ant wrapper cho NetBeans)
  - `HRMSProject/nbproject/project.properties` (javac.source = 1.8)
- Entry point
  - `HRMSProject/src/hrms/HRMS.java`
    - Menu console: Employee Management hiện hoạt. Các menu Attendance / Salary / Reports được comment out.
    - Lưu dữ liệu trong memory: `private static List<Employee> employeeList = new ArrayList<>();`
    - Hỗ trợ: Add, Update, Remove (deactivate), View, Search nhân viên.
- Models
  - `HRMSProject/src/hrms/models/Employee.java` (abstract, fields + attendance list, toString, countWorkingDays, addAttendance, abstract calculateSalary)
  - `HRMSProject/src/hrms/models/Attendance.java` (date, status, overtimeHours, validation simple)
  - `HRMSProject/src/hrms/models/FullTimeEmployee.java` (implements calculateSalary)
  - `HRMSProject/src/hrms/models/PartTimeEmployee.java` (implements calculateSalary)
- Exceptions (chưa triển khai nội dung)
  - `HRMSProject/src/hrms/exceptions/AttendanceException.java` (empty class)
  - `HRMSProject/src/hrms/exceptions/InvalidEmployeeException.java` (empty class)
- Utils (chưa triển khai)
  - `HRMSProject/src/hrms/utils/ConsoleUI.java` (empty)
  - `HRMSProject/src/hrms/utils/FileHandler.java` (empty)
  - `HRMSProject/src/hrms/utils/Validator.java` (empty)

Những gì đã hoàn thành (chi tiết)
- Mô hình dữ liệu cơ bản: Employee (abstract), Attendance, FullTimeEmployee, PartTimeEmployee — có logic tính lương cơ bản dựa trên overtime và absence.
- Giao diện console cơ bản (`HRMS.java`) cho quản lý nhân viên:
  - Thêm nhân viên (Full-time / Part-time), validate đơn giản (id trống, trùng, salary > 0, ngày join parse).
  - Cập nhật department/jobTitle.
  - Deactivate nhân viên thay vì xóa.
  - Xem danh sách nhân viên (chỉ active).
  - Tìm kiếm theo tên.
- Có cấu hình project NetBeans/Ant để build.

Những phần còn thiếu / cần hoàn thiện
- Persistence: lưu dữ liệu vào file/DB (hiện chỉ lưu trong memory).
- Attendance UI & management: menu attendance được comment — cần implement (thêm điểm danh, sửa, xóa, import/export).
- Salary management UI: menu salary được comment — cần implement xuất phiếu lương, lịch sử lương.
- Reports: báo cáo tổng hợp (tổng lương, chấm công, vắng nhiều...) chưa có.
- Exception classes: `AttendanceException`, `InvalidEmployeeException` nên extend `Exception` hoặc `RuntimeException` và chứa logic.
- Utils: `ConsoleUI`, `FileHandler`, `Validator` hiện rỗng — cần triển khai để tái sử dụng.
- Tests: không thấy test unit/integration — cần thêm test (JUnit).
- CI/CD: không thấy `.github/workflows` — đề xuất thêm pipeline build + test.
- Documentation/API: thiếu docs, hướng dẫn cài đặt chi tiết, file `.env.example` nếu cần cấu hình DB.
- CODEOWNERS / quy trình duyệt PR: không thấy file -> chưa có người duyệt được chỉ định.

Ưu tiên công việc tiếp theo (gợi ý)
1. Triển khai persistence tối thiểu:
   - Option A: Lưu file JSON/CSV thông qua `FileHandler`.
   - Option B: Kết nối DB (H2 or SQLite for local, hoặc MySQL/Postgres).
2. Triển khai Attendance management (menu và CRUD).
3. Hoàn thiện Salary management & generate payslip.
4. Viết unit tests cho business logic (calculateSalary, countWorkingDays, validator).
5. Thiết lập CI (GitHub Actions): build → test → (optional) code style.
6. Thêm CODEOWNERS để xác định người duyệt PR.
7. Refactor utils và xử lý exceptions.

Hướng dẫn build & chạy (dựa trên cấu trúc hiện có)
- Yêu cầu:
  - Java JDK 8
  - Ant (nếu dùng build.xml), hoặc mở bằng NetBeans 8+/NetBeans 11+ (project NetBeans)
- Build & chạy bằng Ant:
  - Từ thư mục `HRMSProject`:
    - Build: `ant` (hoặc `ant jar`)
    - Chạy (nếu IDE không dùng Compile-on-Save): `ant -f build.xml run`
  - Hoặc mở project bằng NetBeans và nhấn Run.
- Chạy trực tiếp (IDE):
  - Mở `HRMSProject` trong NetBeans, run project; main class: `hrms.HRMS`.

Gợi ý về quy trình duyệt mã (Ai duyệt)
- Hiện tại repo không có `.github/CODEOWNERS` hay file tương tự => không có approver được chỉ định tự động.
- Đề xuất mẫu cơ bản (tùy chỉnh theo team):
  - Tech Lead: người chịu trách nhiệm code-review chính.
  - QA: kiểm thử trước khi merge vào `main`.
  - Quy tắc merge: ít nhất 1 approver, CI pass, không merge trực tiếp vào `main` (dùng branch + PR).
- Mẫu file CODEOWNERS (ví dụ đề xuất) — thêm vào `.github/CODEOWNERS` hoặc `docs/`:
  - Người duyệt mặc định: @MaxxAlan (owner). Thay thế bằng account GitHub thực tế của Tech Lead/QA khi có.

Gợi ý về file CODEOWNERS (mẫu)
```
# Các owner/approver mặc định
# Format: <pattern> <owner>
# Ví dụ: toàn repo do @MaxxAlan duyệt; thay bằng team hoặc người cụ thể nếu cần
* @MaxxAlan
```

Kiểm tra bảo mật & privacy
- Trong `nbproject/private/private.properties` có đường dẫn người dùng cục bộ (ví dụ: C:\Users\anhkhoacod123...) — tốt nhất loại bỏ hoặc thêm `.gitignore` để tránh rò rỉ thông tin môi trường phát triển người dùng.
