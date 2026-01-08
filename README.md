# HRMS
HRMS- Phần mềm quản lý nhân lực công ty.

BÁO CÁO KỸ THUẬT VÀ KẾ HOẠCH TRIỂN KHAI DỰ ÁN HRMS
1. Bảng Phân Công Nhiệm Vụ Chi Tiết (Team 5)
Dựa trên nguyên tắc "Vertical Slice" (Mỗi người phụ trách trọn vẹn 1 module từ xử lý dữ liệu đến giao diện) và yêu cầu "Ai cũng phải code".
Thành Viên
Vai Trò (Role)
Module Phụ Trách
Nhiệm Vụ Code (Coding Tasks)
Nhiệm Vụ Báo Cáo (Report Tasks)
Mạnh (Bạn)
Dev
Employee Management
- Thiết kế EmployeePanel.

- CRUD Nhân viên (Thêm/Sửa/Xóa).

- Validate dữ liệu nhập (Regex, try-catch).

- Xử lý ảnh thẻ nhân viên (nếu có).
- Viết phần System Architecture.

- Vẽ Class Diagram tổng thể.

- Tổng hợp báo cáo cuối cùng.
Khoa
Dev
Attendance Management
- Thiết kế AttendancePanel tích hợp JCalendar.

- Logic chấm công (Check-in/Check-out).

- Tính số ngày công thực tế.

- Chặn lỗi chấm công trùng ngày.
- Viết phần Data Design (Mô tả file attendance.csv).

- Viết Test Case cho phần Chấm công.
Phương Anh
Dev
Salary Management
- Thiết kế SalaryPanel (Bảng lương).

- Code thuật toán tính lương (Lương cứng + OT - Phạt).

- Logic tính thuế và bảo hiểm.

- Xuất phiếu lương ra Dialog/File.
- Viết phần Business Rules Implementation (Giải thích công thức tính lương).

- Mô tả thuật toán tính toán.
Phương Thu
Dev
Reporting & Analytics
- Thiết kế ReportPanel sử dụng JFreeChart.

- Logic thống kê: Top nhân viên, Tỉ lệ đi muộn.

- Filter dữ liệu theo tháng/năm/phòng ban.

- Export báo cáo ra file Excel/PDF (dùng thư viện ngoài).
- Viết phần User Interface Design (Mô tả các biểu đồ).

- Chụp ảnh màn hình minh họa (Screen Captures).
Tuấn Anh
UI Lead
Main UI & Integration
- Code MainFrame, LoginDialog.

- Tích hợp FlatLaf để làm giao diện "xịn" như MISA.

- Làm thanh điều hướng (Sidebar Menu).

- Ghép code của 4 thành viên kia vào ứng dụng chính.
- Viết phần Introduction & Scope.

- Soạn hướng dẫn sử dụng (User Manual).



2. Kế Hoạch Làm Việc Theo Tuần (Weekly Plan)
Áp dụng mô hình Agile rút gọn (2 tuần Sprint) để kịp tiến độ gấp.
Tuần 1: Xây Dựng Core & Giao Diện Cơ Sở
Thứ 2:
Cả nhóm: Họp chốt cấu trúc file CSV (Thống nhất tên cột để không bị lỗi khi ghép code). Cài đặt môi trường (NetBeans 15 + JDK 8).
Tuấn Anh: Tạo Project khung, đẩy lên Git (hoặc tạo folder chung Google Drive).
Thứ 3 - Thứ 5 (Code Độc Lập):
Tuấn Anh: Code xong màn hình Đăng nhập (Login) và Khung chính (Dashboard layout).
Mạnh, Khoa, Phương Anh, Phương Thu: Code xong Class Model (Java Beans) và lớp DAO (Đọc ghi file) cho phần của mình.
Thứ 6 - CN:
Bắt đầu làm giao diện JPanel riêng lẻ.
Mạnh: Làm xong form nhập liệu nhân viên.
Khoa: Nhúng được JCalendar vào panel.
Tuần 2: Xử Lý Nghiệp Vụ & Tích Hợp
Thứ 2 - Thứ 4 (Code Logic Phức Tạp):
Phương Anh: Ráp công thức tính lương vào nút "Tính Lương". Đảm bảo số liệu khớp với chấm công của Khoa.
Phương Thu: Lấy dữ liệu từ file của Mạnh và Phương Anh để vẽ biểu đồ JFreeChart.
Tuấn Anh: Trang trí lại các nút bấm, thêm icon, chỉnh màu sắc theme FlatLaf.
Thứ 5 (Integration Day - Quan Trọng):
Cả nhóm: Gửi code cho Tuấn Anh ghép vào MainFrame.
Fix lỗi xung đột (Conflict) biến, tên hàm.
Thứ 6 - CN (Đóng gói & Báo cáo):
Test chéo: Mạnh test phần của Khoa, Phương Anh test phần Phương Thu...
Viết báo cáo Technical Report theo phần đã phân công ở mục 1.
Quay video demo.
3. Sơ Đồ Phần Mềm (Software Architecture Diagram)
Hệ thống tuân thủ kiến trúc MVC (Model - View - Controller) chia theo packages:
src/
├── com.hrms.main
│ └── HRMSApp.java (Chứa hàm main, gọi FlatLaf setup)
├── com.hrms.model (Chứa các Class đối tượng - POJO)
│ ├── Employee.java (Abstract)
│ ├── FullTimeEmployee.java
│ ├── PartTimeEmployee.java
│ ├── Attendance.java
│ └── SalarySlip.java
├── com.hrms.view (Giao diện - Mỗi người 1 file này)
│ ├── MainFrame.java (Tuấn Anh)
│ ├── LoginDialog.java (Tuấn Anh)
│ ├── EmployeePanel.java (Mạnh)
│ ├── AttendancePanel.java (Khoa)
│ ├── SalaryPanel.java (Phương Anh)
│ └── ReportPanel.java (Phương Thu)
├── com.hrms.controller (Xử lý logic đọc ghi file)
│ ├── EmployeeDAO.java
│ ├── AttendanceDAO.java
│ └── SalaryDAO.java
└── com.hrms.util (Tiện ích dùng chung)
├── CsvHelper.java (Hỗ trợ đọc/ghi CSV chuẩn)
├── Validation.java (Kiểm tra số, ngày tháng)
└── Constants.java (Lưu đường dẫn file, màu sắc chủ đạo)
4. Báo Cáo Dữ Liệu Ứng Dụng (Data Dictionary)
Hệ thống sử dụng File CSV làm cơ sở dữ liệu. Cấu trúc file cần thống nhất tuyệt đối:
4.1. File employees.csv (Mạnh quản lý)
Format: ID,FullName,DOB,Gender,Department,Position,SalaryBase,Type
Ví dụ: NV01,Nguyen Van A,1999-01-01,Nam,IT,Dev,15000000,FullTime
4.2. File attendance.csv (Khoa quản lý)
Format: ID,EmployeeID,Date,Status,CheckInTime,CheckOutTime
Ví dụ: 1,NV01,2023-10-25,Present,08:00,17:30
4.3. File salary_history.csv (Phương Anh quản lý)
Format: MonthID,EmployeeID,TotalWorkDays,OvertimeHours,Bonus,Deduction,FinalSalary
Ví dụ: 10-2023,NV01,22,5.0,500000,0,16500000
5. TECHNICAL REPORT (Phiên bản Doanh Nghiệp)
(Đây là khung sườn để nộp cho giảng viên, các bạn copy về điền nội dung chi tiết vào)
TECHNICAL DESIGN DOCUMENT
Project Name: Enterprise Human Resource Management System (E-HRMS)
Team: 5 | Version: 1.0.0
1. Executive Summary
Dự án E-HRMS được phát triển nhằm giải quyết bài toán quản lý nhân sự thủ công, hướng tới tự động hóa quy trình chấm công và tính lương. Ứng dụng được xây dựng trên nền tảng Java Swing, sử dụng kiến trúc MVC và thư viện giao diện hiện đại FlatLaf, mang lại trải nghiệm người dùng (UX) tương đương các phần mềm thương mại như MISA.
2. System Requirements
Functional Requirements:
Quản lý hồ sơ nhân viên (CRUD).
Theo dõi chấm công thời gian thực.
Tính lương tự động dựa trên tham số động.
Báo cáo trực quan (Dashboard).
Non-functional Requirements:
Performance: Xử lý >1000 bản ghi nhân viên không bị lag (sử dụng SwingWorker).
Usability: Giao diện tối giản, hỗ trợ phím tắt.
Compatibility: Chạy tốt trên JDK 8 (Windows/macOS).
3. Detailed Module Design
3.1. Employee Module (Dev: Mạnh)
Sử dụng JTable với DefaultTableModel tùy biến để hiển thị dữ liệu.
Áp dụng Factory Pattern để khởi tạo đối tượng FullTime và PartTime nhân viên.
Validation: Sử dụng Regex để kiểm tra định dạng Email, Số điện thoại ngay khi nhập (KeyListener).
3.2. Attendance Module (Dev: Khoa)
Tích hợp thư viện JCalendar để chọn ngày tháng trực quan, tránh lỗi nhập liệu sai định dạng ngày.
Logic nghiệp vụ: Tự động chặn chấm công nếu nhân viên đã nghỉ việc (check trạng thái từ Employee Module).
3.3. Salary Module (Dev: Phương Anh)
Áp dụng Strategy Pattern cho thuật toán tính lương:
FullTimeStrategy: Lương cứng + Phụ cấp.
PartTimeStrategy: Lương theo giờ * Số giờ làm.
Tính năng xuất phiếu lương: Sử dụng Graphics2D để vẽ phiếu lương và lưu thành ảnh/PDF.
3.4. Reporting Module (Dev: Phương Thu)
Sử dụng thư viện JFreeChart để vẽ biểu đồ tròn (Tỷ lệ nhân sự) và biểu đồ cột (Chi phí lương theo tháng).
Dữ liệu được tổng hợp (Aggregate) từ file attendance.csv và salary.csv thông qua các hàm Stream API của Java 8.
3.5. UI & Integration (Lead: Tuấn Anh)
Look & Feel: Sử dụng FlatLaf (Flat Light/Dark) để hiện đại hóa toàn bộ UI components.
Navigation: Thiết kế theo mô hình CardLayout, giúp chuyển đổi giữa các màn hình mượt mà mà không cần mở nhiều cửa sổ.
4. Conclusion
Hệ thống E-HRMS phiên bản 1.0 đã đáp ứng đầy đủ các yêu cầu nghiệp vụ cốt lõi. Việc áp dụng các Design Pattern và thư viện hiện đại giúp mã nguồn dễ bảo trì và mở rộng trong tương lai.


Sơ đồ luồng xử lý ứng dụng:

Dựa trên kiến trúc MVC (Model - View - Controller) và Kế hoạch triển khai trong tài liệu, Sơ đồ luồng xử lý ứng dụng (Application Processing Flow Diagram) của hệ thống E-HRMS có thể được mô tả như sau:1. Kiến Trúc Tổng Thể

Hệ thống được tổ chức theo mô hình MVC với các thành phần chính nằm trong các gói (package) tương ứng:
Package
Thành Phần
Vai Trò (Controller/Model/View)
Tương Tác Chính
com.hrms.view
Các Panel/Dialog (EmployeePanel, SalaryPanel, MainFrame)
View
Hiển thị giao diện, nhận thao tác từ người dùng.
com.hrms.controller
Các lớp DAO (EmployeeDAO, AttendanceDAO)
Controller
Xử lý logic nghiệp vụ, đọc/ghi dữ liệu từ Model.
com.hrms.model
Các Class đối tượng (Employee, Attendance, SalarySlip)
Model
Lưu trữ cấu trúc dữ liệu, phản ánh trạng thái của hệ thống.
com.hrms.util
CsvHelper, Validation, Constants
Tiện ích
Hỗ trợ chung cho Controller (kiểm tra dữ liệu, đọc/ghi file CSV).

-----2. Luồng Xử Lý Chi Tiết (Flow)

Luồng xử lý diễn ra theo chu kỳ Tương tác Người dùng -> View -> Controller -> Model/Data -> View và sự phối hợp giữa các Module:A. Luồng Khởi Động & Điều Hướng (Tuấn Anh)
Khởi động: HRMSApp.java (main) gọi FlatLaf setup và hiển thị LoginDialog.
Đăng nhập: Người dùng đăng nhập thành công.
Điều hướng: LoginDialog gọi MainFrame. MainFrame sử dụng mô hình CardLayout để nhúng và quản lý việc chuyển đổi giữa các Panel chức năng (EmployeePanel, AttendancePanel, v.v.).
B. Luồng Nghiệp Vụ Cơ Bản (CRUD Nhân Viên - Mạnh)
View: Người dùng nhập dữ liệu nhân viên mới vào EmployeePanel.
Validation: EmployeePanel (View) sử dụng lớp Validation.java (Util) để kiểm tra định dạng dữ liệu (Regex cho Email, SĐT) ngay khi nhập (KeyListener).
Controller/Logic: Khi nhấn nút "Thêm/Sửa/Xóa":
Controller (lớp EmployeeDAO.java) được gọi.
EmployeeDAO sử dụng Factory Pattern để tạo ra đối tượng FullTimeEmployee hoặc PartTimeEmployee (Model) phù hợp.
EmployeeDAO sử dụng CsvHelper.java (Util) để ghi (hoặc sửa/xóa) đối tượng (Model) vào file employees.csv (Data).
Phản hồi: Dữ liệu mới được cập nhật trên JTable của EmployeePanel (View).
C. Luồng Nghiệp Vụ Liên Module (Tính Lương - Phương Anh)

Luồng này thể hiện sự phụ thuộc giữa 3 module: Employee, Attendance và Salary.
Module Chấm Công (Khoa):
AttendancePanel (View) nhận Check-in/out.
AttendanceDAO (Controller) kiểm tra trạng thái nhân viên (từ EmployeeDAO) và chặn lỗi nếu nhân viên đã nghỉ việc.
Ghi dữ liệu vào attendance.csv.
Module Tính Lương (Phương Anh):
SalaryPanel (View) kích hoạt nút "Tính Lương".
Controller (SalaryDAO) Strategy Pattern được áp dụng để chọn thuật toán tính lương:
Nếu Type = FullTime -> FullTimeStrategy (Lương cứng + Phụ cấp).
Nếu Type = PartTime -> PartTimeStrategy (Lương theo giờ * Số giờ làm).
Các Strategy này phải truy cập dữ liệu từ:
File employees.csv (Lương cơ bản, loại hợp đồng).
File attendance.csv (Số ngày công thực tế, giờ OT).
Kết quả được lưu vào salary_history.csv.
Xuất Phiếu Lương: SalaryPanel sử dụng Graphics2D để vẽ phiếu lương và xuất ra file PDF/Ảnh.
D. Luồng Báo Cáo (Phương Thu)
View: Người dùng chọn bộ lọc (tháng/năm) trên ReportPanel.
Logic Thống Kê:
ReportPanel gọi các hàm tổng hợp (Aggregate) logic.
Logic này sử dụng Stream API của Java 8 để đọc và xử lý tổng hợp (tính Tỷ lệ đi muộn, Top nhân viên, Chi phí lương theo tháng) từ file attendance.csv và salary.csv.
Hiển thị: Dữ liệu tổng hợp được chuyển tới thư viện JFreeChart để vẽ ra Biểu đồ Tròn/Cột, và hiển thị trên ReportPanel (View).
Xuất file: Sử dụng thư viện ngoài để Export báo cáo ra Excel/PDF.


