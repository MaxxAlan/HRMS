# HRMS - Enterprise Management System (Final Version)

This is a comprehensive Human Resource Management System (HRMS) built as a high-performance console application. It strictly adheres to the **4-Milestone Development Guideline**, implementing advanced Object-Oriented Programming (OOP) principles and Computational Thinking (CT).

## 🚀 Key Enterprise Features

### 1. Security & RBAC (Role-Based Access Control)
*   **Encrypted Logic:** User accounts are stored in `data/db-sys-hrms.txt`.
*   **Roles:**
    *   **IT / DIRECTOR:** Full system access, including User Management and Security Logs.
    *   **HR:** Employee management and attendance tracking.
    *   **ACCOUNTANT:** Payroll and salary calculation access.
    *   **EMPLOYEE:** Personal attendance and profile viewing.

### 2. Advanced Employee & Attendance Management
*   **Dynamic CRUD:** Full life-cycle management of Full-time and Part-time employees.
*   **Smart Attendance:** Supports real-time Check-in, Manual Check-out, and a **Quick Check-out List** for active on-site workers.
*   **Relational Logic:** Uses a **Has-A relationship** where each Employee object manages its own history of Attendance records.

### 3. Polymorphic Payroll System
*   **Automated Calculation:** Salaries are calculated based on monthly attendance data.
*   **Polymorphism:** Different salary formulas for `FullTimeEmployee` (Basic + OT Rate 80k) and `PartTimeEmployee` (Basic + OT Rate 50k) using method overriding.

### 4. Traceability & Logging
*   **System Logs (`log-time-sys.txt`):** Tracks all login/logout activities.
*   **Security Logs (`log-permission.txt`):** Records high-privilege administrative actions for auditing purposes.

## 🔐 Default Admin Credentials
*   **Username:** `group5`
*   **Password:** `0000`
*   **Role:** `IT`

## 🛠 Technical Stack
*   **Language:** Java 8 (JDK 1.8)
*   **IDE:** NetBeans IDE 15
*   **Deployment:** Launch4j (Executable wrapper)
*   **Architecture:** Service-Oriented Architecture (SOA) with a clean separation of concerns.

## 📁 Portable Project Structure
To run the enterprise executable, ensure the following structure:
```text
HRMS_App/
├── HRMS.exe         # The main executable
├── data/            # Database folder (.txt files)
└── jre/             # Bundled Java Runtime Environment (Portable)
```

## 🏗 Build Instructions
1.  Open the project in **NetBeans 15**.
2.  Run **Clean and Build** to generate the `dist/HRMSProject.jar`.
3.  Use **Launch4j** with the provided `manifest.xml` to wrap the JAR into `HRMS.exe`.

---
*Developed by Team 5 - Milestone 4 Final Project.*