# 🎓 Grade Tracker

A modern Student Grade Management System built using **Java, Servlet, JSP, JDBC, MySQL, Maven, Tomcat, and Bootstrap 5**.

This project demonstrates backend development concepts such as CRUD operations, MVC architecture, JDBC, pagination, searching, sorting, validation, and clean code practices.

---

## 📸 Screenshots

> (Screenshots will be added later)

- Student List
- Add Student
- Edit Student
- Search
- Pagination
- Sorting

---

## ✨ Features

### Student Management
- ✅ Add Student
- ✅ Edit Student
- ✅ Delete Student
- ✅ View Student List

### Search & Navigation
- ✅ Search by First Name
- ✅ Search by Last Name
- ✅ Search by Email
- ✅ Search by Department

### Pagination
- ✅ Configurable Page Size
- ✅ Previous / Next Navigation
- ✅ Dynamic Page Numbers

### Sorting
- ✅ Sort by ID
- ✅ Sort by Name
- ✅ Sort by Email
- ✅ Sort by Department
- ✅ Sort by Semester
- ✅ Ascending / Descending Toggle

### Validation
- ✅ Required Field Validation
- ✅ Email Validation
- ✅ Semester Validation

### UI
- ✅ Bootstrap 5
- ✅ Responsive Layout
- ✅ Success Messages
- ✅ Clean User Interface

---

## 🏗️ Project Architecture

```
MVC Architecture

Browser
    │
    ▼
StudentServlet (Controller)
    │
    ▼
StudentDAO
    │
    ▼
MySQL Database
    ▲
    │
Student Model
    │
    ▼
JSP Views
```

---

## 🛠️ Tech Stack

| Technology | Version |
|------------|----------|
| Java | 25 |
| Servlet | Jakarta Servlet |
| JSP | JSTL |
| JDBC | Java JDBC |
| MySQL | 8.x |
| Bootstrap | 5 |
| Maven | 3.x |
| Apache Tomcat | 10.x |

---

## 📁 Project Structure

```
grade-tracker
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.gradetracker
│   │   │       ├── controller
│   │   │       ├── dao
│   │   │       ├── model
│   │   │       ├── util
│   │   │       └── validator
│   │   │
│   │   ├── resources
│   │   │   └── db.properties.example
│   │   │
│   │   └── webapp
│   │       ├── common
│   │       └── students
│   │
│   └── test
│
├── database
├── screenshots
├── pom.xml
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- Java 25
- Maven
- Apache Tomcat 10+
- MySQL 8+

---

### Clone the Repository

```bash
git clone https://github.com/sooraj2145/grade-tracker.git
```

---

### Configure Database

Copy

```
db.properties.example
```

to

```
db.properties
```

Update your database credentials.

---

### Create Database

```sql
CREATE DATABASE grade_tracker_db;
```

Import the SQL script from the **database** folder.

---

### Build

```bash
mvn clean package
```

---

### Run

Deploy the generated WAR file to Apache Tomcat.

---

## 📚 Concepts Demonstrated

- MVC Architecture
- JDBC
- DAO Pattern
- PreparedStatement
- ResultSet Mapping
- CRUD Operations
- Pagination
- Searching
- Dynamic Sorting
- SQL Injection Prevention
- Validation
- Session Management
- Flash Messages
- Bootstrap UI

---

## 🗺️ Roadmap

### ✅ Sprint 1
- Project Setup
- MVC
- JDBC

### ✅ Sprint 2
- Student CRUD

### ✅ Sprint 3
- Validation
- Flash Messages

### ✅ Sprint 4
- Search
- Pagination

### ✅ Sprint 5
- Dynamic Sorting

### 🚧 Upcoming

- Subject Module
- Grade Module
- Dashboard
- Authentication
- Authorization
- Reports
- Hibernate Migration
- Spring Boot Migration

---

## 👨‍💻 Author

**Sooraj C**

Backend Developer (Java)

GitHub:
https://github.com/sooraj2145

---

## 📄 License

This project is licensed under the MIT License.