
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentDatabaseApp {
    private DatabaseManager dbManager;
    private Scanner scanner;

    public StudentDatabaseApp() throws SQLException {
        this.dbManager = new DatabaseManager();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("=== Student Database Management System ===");

        try {
            while (true) {
                displayMenu();
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> viewAllStudents();
                    case 3 -> searchStudent();
                    case 4 -> updateStudent();
                    case 5 -> deleteStudent();
                    case 6 -> viewStudentDetails();
                    case 7 -> {
                        System.out.println("Thank you for using Student Database System!");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }

                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        } finally {
            try {
                dbManager.close();
                scanner.close();
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Students by Name");
        System.out.println("4. Update Student Information");
        System.out.println("5. Delete Student");
        System.out.println("6. View Student Details");
        System.out.println("7. Exit");
        System.out.println("=".repeat(50));
    }

    private void addStudent() {
        System.out.println("\n--- Add New Student ---");

        try {
            String name = getValidName();
            String email = getValidEmail();
            int age = getValidAge();
            String course = getValidCourse();

            Student student = new Student(name, email, age, course);

            if (dbManager.addStudent(student)) {
                System.out.println("✓ Student added successfully with ID: " + student.getId());
            } else {
                System.out.println("✗ Failed to add student.");
            }

        } catch (SQLException e) {
            handleSQLException("adding student", e);
        }
    }

    private void viewAllStudents() {
        System.out.println("\n--- All Students ---");

        try {
            List<Student> students = dbManager.getAllStudents();

            if (students.isEmpty()) {
                System.out.println("No students found in the database.");
            } else {
                displayStudentTable(students);
            }

        } catch (SQLException e) {
            handleSQLException("retrieving students", e);
        }
    }

    private void searchStudent() {
        System.out.println("\n--- Search Students ---");

        try {
            String searchName = getStringInput("Enter name to search: ");
            List<Student> students = dbManager.searchStudentsByName(searchName);

            if (students.isEmpty()) {
                System.out.println("No students found matching: " + searchName);
            } else {
                displayStudentTable(students);
            }

        } catch (SQLException e) {
            handleSQLException("searching students", e);
        }
    }

    private void updateStudent() {
        System.out.println("\n--- Update Student ---");

        try {
            int id = getIntInput("Enter student ID to update: ");
            Student existingStudent = dbManager.getStudentById(id);

            if (existingStudent == null) {
                System.out.println("Student with ID " + id + " not found.");
                return;
            }

            System.out.println("Current details: " + existingStudent);
            System.out.println("Enter new details (press Enter to keep current value):");

            String name = getStringInputWithDefault("Name", existingStudent.getName());
            String email = getEmailInputWithDefault("Email", existingStudent.getEmail());
            int age = getAgeInputWithDefault("Age", existingStudent.getAge());
            String course = getStringInputWithDefault("Course", existingStudent.getCourse());

            Student updatedStudent = new Student(id, name, email, age, course);

            if (dbManager.updateStudent(updatedStudent)) {
                System.out.println("✓ Student updated successfully.");
            } else {
                System.out.println("✗ Failed to update student.");
            }

        } catch (SQLException e) {
            handleSQLException("updating student", e);
        }
    }

    private void deleteStudent() {
        System.out.println("\n--- Delete Student ---");

        try {
            int id = getIntInput("Enter student ID to delete: ");
            Student student = dbManager.getStudentById(id);

            if (student == null) {
                System.out.println("Student with ID " + id + " not found.");
                return;
            }

            System.out.println("Student to delete: " + student);
            String confirmation = getStringInput("Are you sure? (yes/no): ");

            if ("yes".equalsIgnoreCase(confirmation)) {
                if (dbManager.deleteStudent(id)) {
                    System.out.println("✓ Student deleted successfully.");
                } else {
                    System.out.println("✗ Failed to delete student.");
                }
            } else {
                System.out.println("Delete operation cancelled.");
            }

        } catch (SQLException e) {
            handleSQLException("deleting student", e);
        }
    }

    private void viewStudentDetails() {
        System.out.println("\n--- View Student Details ---");

        try {
            int id = getIntInput("Enter student ID: ");
            Student student = dbManager.getStudentById(id);

            if (student == null) {
                System.out.println("Student with ID " + id + " not found.");
            } else {
                displayStudentDetails(student);
            }

        } catch (SQLException e) {
            handleSQLException("retrieving student details", e);
        }
    }

    // Input helper methods with validation
    private String getValidName() {
        while (true) {
            String name = getStringInput("Enter student name: ");
            if (InputValidator.isValidName(name)) {
                return name.trim();
            }
            System.out.println("Invalid name! Name should contain only letters and spaces (2-50 characters).");
        }
    }

    private String getValidEmail() {
        while (true) {
            String email = getStringInput("Enter student email: ");
            if (InputValidator.isValidEmail(email)) {
                return email.trim().toLowerCase();
            }
            System.out.println("Invalid email format! Please enter a valid email address.");
        }
    }

    private int getValidAge() {
        while (true) {
            int age = getIntInput("Enter student age: ");
            if (InputValidator.isValidAge(age)) {
                return age;
            }
            System.out.println("Invalid age! Age should be between 1 and 149.");
        }
    }

    private String getValidCourse() {
        while (true) {
            String course = getStringInput("Enter course name: ");
            if (InputValidator.isValidCourse(course)) {
                return course.trim();
            }
            System.out.println("Invalid course! Course name should be 2-100 characters long.");
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String getStringInputWithDefault(String field, String defaultValue) {
        System.out.print(field + " [" + defaultValue + "]: ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

    private String getEmailInputWithDefault(String field, String defaultValue) {
        while (true) {
            String input = getStringInputWithDefault(field, defaultValue);
            if (input.equals(defaultValue) || InputValidator.isValidEmail(input)) {
                return input;
            }
            System.out.println("Invalid email format!");
        }
    }

    private int getAgeInputWithDefault(String field, int defaultValue) {
        while (true) {
            System.out.print(field + " [" + defaultValue + "]: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return defaultValue;
            }

            try {
                int age = Integer.parseInt(input);
                if (InputValidator.isValidAge(age)) {
                    return age;
                }
                System.out.println("Invalid age! Age should be between 1 and 149.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    private void displayStudentTable(List<Student> students) {
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("%-5s %-20s %-25s %-5s %-20s%n",
                "ID", "Name", "Email", "Age", "Course");
        System.out.println("=".repeat(80));

        for (Student student : students) {
            System.out.printf("%-5d %-20s %-25s %-5d %-20s%n",
                    student.getId(),
                    truncate(student.getName(), 20),
                    truncate(student.getEmail(), 25),
                    student.getAge(),
                    truncate(student.getCourse(), 20));
        }

        System.out.println("=".repeat(80));
        System.out.println("Total students: " + students.size());
    }

    private void displayStudentDetails(Student student) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("STUDENT DETAILS");
        System.out.println("=".repeat(40));
        System.out.println("ID:     " + student.getId());
        System.out.println("Name:   " + student.getName());
        System.out.println("Email:  " + student.getEmail());
        System.out.println("Age:    " + student.getAge());
        System.out.println("Course: " + student.getCourse());
        System.out.println("=".repeat(40));
    }

    private String truncate(String str, int maxLength) {
        return str.length() <= maxLength ? str : str.substring(0, maxLength - 3) + "...";
    }

    private void handleSQLException(String operation, SQLException e) {
        System.err.println("Database error while " + operation + ":");

        // Check for common SQL error codes
        switch (e.getErrorCode()) {
            case 1062 -> System.err.println("✗ Duplicate entry - Email already exists!");
            case 1452 -> System.err.println("✗ Foreign key constraint violation!");
            case 1406 -> System.err.println("✗ Data too long for column!");
            default -> System.err.println("✗ " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            StudentDatabaseApp app = new StudentDatabaseApp();
            app.run();
        } catch (SQLException e) {
            System.err.println("Failed to initialize database connection: " + e.getMessage());
            System.err.println("Please check your database configuration and ensure the database server is running.");
        }
    }
}