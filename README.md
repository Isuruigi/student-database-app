# Student Database Management System

## Project Overview
A console-based Java application implementing CRUD operations for student management using JDBC and MySQL database. This project demonstrates proper database integration, exception handling, and clean code structure.

## System Requirements
- **Java Version:** Java 8 or later
- **Database:** MySQL Server 5.7+
- **JDBC Driver:** MySQL Connector/J

## Database Setup Instructions

### 1. Install MySQL Server
- Download and install MySQL Server from [mysql.com](https://dev.mysql.com/downloads/)
- Start MySQL service on your system

### 2. Create Database
Connect to MySQL and create the database:
```sql
CREATE DATABASE studentdb;
USE studentdb;
```
*Note: The application will automatically create the `students` table when first run.*

### 3. Configure Database Connection
Edit the `DatabaseConnection.java` file and update these constants with your MySQL credentials:
```java
private static final String USERNAME = "root";
private static final String PASSWORD = "your_mysql_password";
```

## Compilation and Execution

### Download MySQL JDBC Driver
1. Download MySQL Connector/J from [MySQL Downloads](https://dev.mysql.com/downloads/connector/j/)
2. Save the JAR file (e.g., `mysql-connector-j-8.2.0.jar`) in your project directory

### Compile the Application
```bash
# Windows
javac -cp "mysql-connector-j-8.2.0.jar;." *.java

# Linux/Mac
javac -cp "mysql-connector-j-8.2.0.jar:." *.java
```

### Run the Application
```bash
# Windows
java -cp "mysql-connector-j-8.2.0.jar;." StudentManagementSystem

# Linux/Mac
java -cp "mysql-connector-j-8.2.0.jar:." StudentManagementSystem
```

## Features Implemented

### Core CRUD Operations (Required)
1. **INSERT** - Add new students with validation
2. **VIEW** - Display all students in formatted table
3. **UPDATE** - Modify existing student information
4. **DELETE** - Remove students with confirmation prompt

### JDBC Best Practices (Required)
- PreparedStatement usage for SQL injection prevention
- Proper exception handling with meaningful error messages
- Automatic resource closing using try-with-resources
- Database connection management

### Code Structure (Required)
- Well-organized classes with single responsibilities
- Meaningful variable and method names
- Clear separation between data model, database access, and user interface
- Comprehensive comments and documentation

### Bonus Features (Optional)
- **Search by Name** - Find students using partial name matching
- **Transaction Handling** - Batch insert operations with rollback capability
- **Input Validation** - Email format and age range validation
- **Error Handling** - Specific handling for common database errors
- **Sample Data** - Demonstration of transaction usage

## Application Menu
```
1. Add New Student
2. View All Students
3. Find Student by ID
4. Update Student
5. Delete Student
6. Search Students by Name (Bonus)
7. Add Sample Data (Bonus)
8. Exit
```

## Database Schema
```sql
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    age INT NOT NULL,
    course VARCHAR(100) NOT NULL
);
```

## Class Structure
- **Student.java** - Data model with proper encapsulation
- **DatabaseConnection.java** - Centralized database connection management
- **StudentDAO.java** - Data Access Object implementing all CRUD operations
- **StudentManagementSystem.java** - Main application with user interface

## Assumptions and Design Decisions

### Technical Assumptions
- MySQL server is running on localhost:3306
- User has necessary permissions to create tables
- Single-user application (no concurrent access handling)
- Console-based interface is sufficient for demonstration

### Input Validation
- Email must contain '@' and '.' characters (basic validation)
- Age must be between 1 and 149
- Names and courses cannot be empty
- Duplicate emails are prevented by database constraint

### Error Handling Strategy
- SQLException is caught and handled with user-friendly messages
- Common MySQL error codes (1062, 1049) have specific handling
- Application continues running after recoverable errors
- Database connection issues are reported clearly

### Transaction Usage
- Single operations use auto-commit mode
- Batch operations (sample data) use explicit transaction management
- Rollback implemented for failed batch operations

## Testing the Application
1. Run the application and verify database connection
2. Test each CRUD operation:
    - Add a few students
    - View all students
    - Search by ID and name
    - Update student information
    - Delete a student
3. Test error conditions:
    - Try adding duplicate email
    - Enter invalid age values
    - Search for non-existent IDs

## Known Limitations
- Basic email validation (not RFC compliant)
- No password protection for database operations
- Console interface only
- Single database connection (no connection pooling)
- No logging framework integration

## Extension Possibilities
- Web-based interface using servlets/JSP
- REST API implementation
- Advanced search capabilities
- Student photo management
- Course enrollment system
- Grade management integration

---
**Student:** I.G.Isuru Chathuranga
**Student ID:** 2022s19630/s16847  
**Submission Date:** 02nd of September 2025  
**Assignment:** Individual Java Database Project