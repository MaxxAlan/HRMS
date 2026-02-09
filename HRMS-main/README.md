# Ứng dụng HRMS Console

Đây là một ứng dụng Hệ thống Quản lý Nhân sự (HRMS) đơn giản, được xây dựng dưới dạng ứng dụng Console sử dụng Java. Ứng dụng cung cấp các chức năng cơ bản để quản lý dữ liệu nhân viên với hệ thống phân quyền truy cập dựa trên vai trò.

## Tính năng

*   **Xác thực người dùng:** Hệ thống đăng nhập an toàn với các vai trò riêng biệt (ADMIN, USER).
*   **Kiểm soát truy cập theo vai trò:** Các chức năng khác nhau có thể truy cập tùy thuộc vào vai trò của người dùng (ví dụ: ADMIN có thể quản lý nhân viên).
*   **Quản lý nhân viên:**
    *   Thêm Nhân viên Mới
    *   Cập nhật Thông tin Nhân viên
    *   Vô hiệu hóa Nhân viên (soft delete)
    *   Xem Tất cả Nhân viên đang hoạt động
    *   Tìm kiếm Nhân viên theo Tên
*   **Giao diện người dùng Console:** Giao diện console thân thiện, được định dạng đẹp mắt để tương tác.
*   **Lưu trữ dữ liệu:** Dữ liệu nhân viên được tải từ và lưu vào tệp `src/data/employees.txt`.

## Công nghệ sử dụng

*   **Ngôn ngữ:** Java 8
*   **Môi trường phát triển:** NetBeans IDE 15
*   **Hệ thống Build:** Ant (thông qua `build.xml` và `nbproject/` của NetBeans)
*   **Thư viện cốt lõi:** Các thư viện Java SE tiêu chuẩn (java.util, java.io, java.text, java.util.Map, java.util.HashMap).

## Bắt đầu

### Điều kiện tiên quyết

*   **Java Development Kit (JDK) 8:** Đảm bảo bạn đã cài đặt JDK 8. Các lệnh `javac` và `java` mặc định được giả định là có sẵn trong môi trường của bạn.
*   **NetBeans IDE 15:** Được khuyến nghị để quản lý cấu trúc dự án và build.

### Cài đặt dự án

1.  **Tải xuống hoặc clone repository** (hoặc các tệp dự án).
2.  **Mở dự án trong NetBeans IDE 15.** Thư mục dự án nằm tại `D:\HRMS-main\HRMS-main`.

### Build dự án

Bạn có thể build dự án bằng NetBeans IDE hoặc qua dòng lệnh.

**Sử dụng NetBeans IDE:**
1.  Mở dự án `D:\HRMS-main\HRMS-main` trong NetBeans.
2.  Nhấp chuột phải vào dự án và chọn "Build". Thao tác này sẽ biên dịch các tệp mã nguồn Java.

**Sử dụng Command Line (Ant thông qua `build.xml` của NetBeans):**
*   Di chuyển đến thư mục gốc của dự án: `cd D:\HRMS-main\HRMS-main`
*   Chạy tập lệnh build Ant:
    ```bash
    # Trên Windows (sử dụng PowerShell hoặc cmd)
    ant
    ```
    Lệnh này sẽ biên dịch mã nguồn.

### Chạy ứng dụng

Sau khi build, bạn có thể chạy ứng dụng.

**Sử dụng NetBeans IDE:**
1.  Nhấp chuột phải vào dự án và chọn "Run".

**Sử dụng Command Line:**
*   **Nếu bạn biên dịch thủ công** (chỉ cho mục đích tham khảo, nên dùng NetBeans/Ant):
    1.  Di chuyển đến thư mục chứa các lớp đã biên dịch (ví dụ: `D:\HRMS-main\HRMS-main\build\classes`).
    2.  Chạy lớp chính:
        ```bash
        # Trên Windows Command Prompt/PowerShell
        java -cp . hrms.HRMS
        ```
        *Lưu ý:* Nếu các lớp model hoặc thư viện khác nằm ở cấu trúc khác, classpath (`-cp`) có thể cần điều chỉnh.

## Tài khoản Người dùng (Demo)

*   **Quản trị viên (Admin):**
    *   Tên đăng nhập: `admin`
    *   Mật khẩu: `admin`
    *   Quyền hạn: Truy cập đầy đủ (Quản lý Nhân viên, v.v.)
*   **Người dùng thường (User):**
    *   Tên đăng nhập: `user`
    *   Mật khẩu: `user`
    *   Quyền hạn: Hạn chế (chỉ xem nhân viên, không quản lý).

## Nâng cấp trong tương lai

*   Triển khai các module Quản lý Chấm công và Lương.
*   Phát triển module Báo cáo toàn diện.
*   Cải thiện giao diện người dùng (ví dụ: sử dụng Swing hoặc JavaFX cho giao diện đồ họa).
*   Thêm các tính năng bảo mật mạnh mẽ hơn.
*   Cải thiện xử lý lỗi và xác thực dữ liệu.

---
*(Tệp README này được tạo vào ngày 2026-02-09 và phản ánh trạng thái hiện tại của dự án.)*
