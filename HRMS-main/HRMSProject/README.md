# Hệ thống Quản lý Nhân sự (HRMS) - Enterprise Edition

Đây là một ứng dụng quản lý nhân sự chuyên nghiệp chạy trên nền tảng Console (Dòng lệnh), được thiết kế theo cấu trúc doanh nghiệp với hệ thống bảo mật và phân quyền chặt chẽ.

## 🚀 Tính năng chính

### 1. Bảo mật & Phân quyền (Security & Authorization)
*   **Xác thực người dùng:** Đăng nhập thông qua tài khoản lưu trữ trong cơ sở dữ liệu file (`db-sys-hrms.txt`).
*   **Phân quyền dựa trên vai trò (RBAC):**
    *   **IT & DIRECTOR:** Toàn quyền truy cập mọi module, bao gồm cả hệ thống Log bảo mật.
    *   **HR:** Quản lý nhân viên, chấm công và xem một số báo cáo.
    *   **ACCOUNTANT:** Truy cập module Quản lý Lương.
    *   **EMPLOYEE:** Chỉ thực hiện chấm công cá nhân.

### 2. Quản lý Nhân viên (Employee Management)
*   Thêm mới nhân viên (Hỗ trợ Full-time và Part-time).
*   Cập nhật thông tin và vô hiệu hóa (Soft delete) nhân viên.
*   Tìm kiếm và hiển thị danh sách nhân viên đang hoạt động.

### 3. Chấm công (Attendance Management)
*   Ghi nhận giờ vào (Check-in) và giờ ra (Check-out) theo thời gian thực.
*   Lưu trữ lịch sử chấm công chi tiết theo từng ngày.

### 4. Hệ thống Logs (Logging System)
*   **System Log (`log-time-sys.txt`):** Ghi lại lịch sử đăng nhập/đăng xuất và các thao tác check-in.
*   **Security Log (`log-permission.txt`):** Ghi lại các truy cập đặc quyền của ADMIN/Giám đốc để phục vụ kiểm toán.

### 5. Lương & Báo cáo (Salary & Reports)
*   Tính toán bảng lương dựa trên lương cơ bản và dữ liệu chấm công.
*   Thống kê số lượng nhân sự và ngân sách lương.

## 🛠 Công nghệ sử dụng
*   **Ngôn ngữ:** Java 8 (JDK 1.8)
*   **IDE:** NetBeans IDE 15
*   **Lưu trữ:** Plain text file (.txt) với cấu trúc Delimiter.

## 📁 Cấu trúc thư mục dữ liệu (`src/data/`)
*   `employees.txt`: Dữ liệu hồ sơ nhân viên.
*   `attendance.txt`: Nhật ký chấm công.
*   `db-sys-hrms.txt`: Danh sách tài khoản người dùng và quyền hạn.
*   `log-time-sys.txt`: Nhật ký hoạt động hệ thống.
*   `log-permission.txt`: Nhật ký truy cập đặc quyền.

## 🔐 Tài khoản Admin mặc định
*   **Username:** `group3`
*   **Password:** `0000`
*   **Role:** `IT` (Toàn quyền)

---
*Dự án được phát triển bởi Team 5 - 2026.*
