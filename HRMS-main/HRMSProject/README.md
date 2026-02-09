# Hệ thống Quản lý Nhân sự (HRMS) - Final Version

Ứng dụng HRMS Console hoàn thiện, được xây dựng theo lộ trình 4 Milestone chuẩn của đồ án học phần. Hệ thống áp dụng triệt để các nguyên lý OOP (Abstraction, Encapsulation, Inheritance, Polymorphism) và Computational Thinking (Decomposition, Pattern Recognition).

## 🚀 Tính năng & Cấu trúc Milestone

*   **Milestone 1 & 3 (Kiến trúc & Kế thừa):** 
    *   Sử dụng quan hệ **Has-A** (Employee chứa danh sách Attendance).
    *   Hệ thống phân cấp: `Employee` (Abstract) <- `FullTimeEmployee`, `PartTimeEmployee`.
*   **Milestone 2 (CRUD & Nghiệp vụ):** 
    *   Quản lý nhân viên toàn diện.
    *   Logic chấm công theo trạng thái (Present/Absent/Leave).
*   **Milestone 4 (Tích hợp & File I/O):**
    *   Xử lý file `.txt` cho Nhân viên, Chấm công, Tài khoản và Logs.
    *   Cơ chế bắt lỗi (Exception Handling) toàn diện.

## 💰 Logic Tính Lương (Theo Guideline)
*   **Full-time:** Lương tính theo ngày công thực tế + Làm thêm (OT Rate: 80,000/h).
*   **Part-time:** Lương tính theo ngày làm việc + Làm thêm (OT Rate: 50,000/h).

## 🔐 Bảo mật & Logs
*   **Phân quyền (RBAC):** IT, DIRECTOR, HR, ACCOUNTANT, EMPLOYEE.
*   **Audit Logs:** Ghi lại mọi biến động hệ thống và truy cập đặc quyền.
*   **Tài khoản Admin:** `group5` / `0000` (Role: IT).

## 📁 Cấu trúc Project
*   `hrms.services`: Chứa logic nghiệp vụ trung tâm (`HRManagementSystem`).
*   `hrms.models`: Các lớp thực thể và quan hệ đối tượng.
*   `hrms.utils`: Tiện ích File I/O và Console UI.
*   `src/data/`: Lưu trữ cơ sở dữ liệu dạng `.txt`.

---
*Hoàn thành theo tiêu chuẩn kỹ thuật Milestone 4 - Team 5.*
