# Kịch bản Kiểm thử HRMS (Test Cases)

Tài liệu này hướng dẫn Team 5 thực hiện kiểm thử các chức năng chính của hệ thống.

## 1. Kiểm thử Bảo mật & Phân quyền
| STT | Chức năng | Hành động | Kết quả mong đợi |
|-----|-----------|-----------|------------------|
| 1.1 | Đăng nhập Admin | User: `group5`, Pass: `0000` | Đăng nhập thành công, vào Main Menu. |
| 1.2 | Đăng nhập Sai | Nhập Pass lung tung | Hiện thông báo lỗi "Invalid credentials", ghi log FAIL. |
| 1.3 | Phân quyền HR | Đăng nhập user `hr_manager` | Truy cập được Employee Menu, nhưng không vào được mục 5 (System Logs). |
| 1.4 | Phân quyền NV | Đăng nhập user `nhanvien1` | Bị từ chối khi vào module Employee Management. |

## 2. Kiểm thử Quản lý Nhân viên (CRUD)
| STT | Chức năng | Hành động | Kết quả mong đợi |
|-----|-----------|-----------|------------------|
| 2.1 | Thêm nhân viên | Chọn Add, nhập ID mới `EMP006` | Nhân viên được lưu vào danh sách và file `employees.txt`. |
| 2.2 | Trùng ID | Thêm nhân viên với ID `EMP001` | Hệ thống báo lỗi "ID exists". |
| 2.3 | Xóa (Deactivate) | Chọn Deactivate `EMP001` | Trạng thái active thành false, không hiện ở View All. |

## 3. Kiểm thử Chấm công (Attendance)
| STT | Chức năng | Hành động | Kết quả mong đợi |
|-----|-----------|-----------|------------------|
| 3.1 | Check-in | Chọn mục 2.1, nhập ID `EMP001` | Ghi nhận giờ vào, thông báo thành công. |
| 3.2 | Check-out | Chọn mục 2.2, nhập ID `EMP001` | Cập nhật giờ ra cho bản ghi ngày hôm nay. |
| 3.3 | Xem lịch sử | Chọn mục 2.3, nhập ID `EMP001` | Hiển thị bảng danh sách các ngày đã đi làm. |

## 4. Kiểm thử Tính lương & Báo cáo
| STT | Chức năng | Hành động | Kết quả mong đợi |
|-----|-----------|-----------|------------------|
| 4.1 | Tính lương | Vào Salary Management | Hiển thị bảng lương dự kiến của toàn bộ nhân viên active. |
| 4.2 | Báo cáo | Vào Reports | Hiển thị tổng số nhân viên và tổng lượt chấm công. |

## 5. Kiểm thử Hệ thống Log (IT Only)
| STT | Chức năng | Hành động | Kết quả mong đợi |
|-----|-----------|-----------|------------------|
| 5.1 | Xem Log Đăng nhập | Vào mục 5.1 | Hiển thị danh sách các lần login/logout gần đây. |
| 5.2 | Kiểm tra Security | Vào mục 5.2 | Thấy log ghi lại lần Admin truy cập hệ thống. |

---
*Lưu ý: Sau mỗi lần chạy Test, hãy kiểm tra các file trong thư mục `src/data/` để xác nhận dữ liệu đã được đồng bộ chính xác.*
