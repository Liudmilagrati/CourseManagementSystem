/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public class ReportGenerator {
    // Instance variables
    private DB_IO dbIO; // Database IO interface for database operations
    private final ReportExporter reportExporter; // Object for exporting reports
    private ReportGenerator reportGenerator;

    /**
     * Constructor for ReportGenerator class.
     * @param dbIO Database IO object for database operations
     */
    
public ReportGenerator(DB_IO dbIO) {
    // Assigning the provided DB_IO object to the instance variable dbIO
        this.dbIO = dbIO;
        // Initializing an instance of ReportExporter class
        this.reportExporter = new ReportExporter(); 
    }

// Generate Course Report
    public String generateCourseReport(String courseId) {
        StringBuilder report = new StringBuilder();

        try (Connection connection = dbIO.getConnection()) {
            // Query to retrieve course name
            String courseQuery = "SELECT course_name FROM Courses WHERE course_id = ?";
            try (PreparedStatement courseStatement = connection.prepareStatement(courseQuery)) {
                courseStatement.setString(1, courseId);
                try (ResultSet courseResult = courseStatement.executeQuery()) {
                    if (courseResult.next()) {
                        // Extract course name
                        String courseName = courseResult.getString("course_name");
                        report.append("Course Name: ").append(courseName).append("\n\n");

                        // Query to retrieve information about modules related to the course
                        String moduleQuery = "SELECT * FROM Modules WHERE course_id = ?";
                        try (PreparedStatement moduleStatement = connection.prepareStatement(moduleQuery)) {
                            moduleStatement.setString(1, courseId);
                            try (ResultSet moduleResult = moduleStatement.executeQuery()) {
                                // Loop through modules and extract information
                                while (moduleResult.next()) {
                                    String moduleName = moduleResult.getString("module_name");
                                    String programme = moduleResult.getString("programme");
                                    int numberOfStudentsEnrolled = getNumberOfStudentsEnrolled(moduleResult.getInt("module_id"), connection);
                                    String lecturer = getLecturer(moduleResult.getInt("lecturer_id"), connection);
                                    String room = moduleResult.getString("room_assigned");

                                    // Append module information to the report
                                    report.append("Module Name: ").append(moduleName).append("\n");
                                    report.append("Programme: ").append(programme).append("\n");
                                    report.append("Number of Students Enrolled: ").append(numberOfStudentsEnrolled).append("\n");
                                    report.append("Lecturer: ").append(lecturer).append("\n");
                                    report.append("Room: ").append(room).append("\n\n");
                                }
                            }
                        }
                    } else {
                        report.append("No course found with ID: ").append(courseId).append("\n");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }

        return report.toString();
    }

    // Method to get the number of students enrolled in a module
    private int getNumberOfStudentsEnrolled(int moduleId, Connection connection) throws SQLException {
        String enrollmentQuery = "SELECT COUNT(*) AS num_students FROM Module_Enrollments WHERE module_id = ?";
        try (PreparedStatement enrollmentStatement = connection.prepareStatement(enrollmentQuery)) {
            enrollmentStatement.setInt(1, moduleId);
            try (ResultSet enrollmentResult = enrollmentStatement.executeQuery()) {
                if (enrollmentResult.next()) {
                    return enrollmentResult.getInt("num_students");
                }
            }
        }
        return 0; // Return 0 if no students are enrolled
    }

     // Method to get the lecturer of a module
    private String getLecturer(int lecturerId, Connection connection) throws SQLException {
        String lecturerQuery = "SELECT lecturer_name FROM Lecturers WHERE lecturer_id = ?";
        try (PreparedStatement lecturerStatement = connection.prepareStatement(lecturerQuery)) {
            lecturerStatement.setInt(1, lecturerId);
            try (ResultSet lecturerResult = lecturerStatement.executeQuery()) {
                if (lecturerResult.next()) {
                    return lecturerResult.getString("lecturer_name");
                }
            }
        }
        return "Unknown"; // Return "Unknown" if lecturer not found
    }

// Generate Student Report
    
    public String generateStudentReport(String studentId) {
        StringBuilder report = new StringBuilder();
        Connection conn = null;

        try {
            conn = dbIO.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occurred while generating the student report.";
        }
        return null;
    }
    // Method to generate student name and student number
    public String getStudentInfo(String studentId) {
        StringBuilder studentInfo = new StringBuilder();

        try (Connection connection = dbIO.getConnection()) {
            String studentQuery = "SELECT student_name, student_surname, student_number FROM Students WHERE student_id = ?";
            try (PreparedStatement studentStatement = connection.prepareStatement(studentQuery)) {
                studentStatement.setString(1, studentId);
                try (ResultSet studentResult = studentStatement.executeQuery()) {
                    if (studentResult.next()) {
                        String studentName = studentResult.getString("student_name");
                        String studentSurname = studentResult.getString("student_surname");
                        String studentNumber = studentResult.getString("student_number");

                        studentInfo.append("Student Name: ").append(studentName).append("\n");
                        studentInfo.append("Student Surname: ").append(studentSurname).append("\n");
                        studentInfo.append("Student Number: ").append(studentNumber).append("\n");
                    } else {
                        studentInfo.append("No student found with ID: ").append(studentId).append("\n");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }

        return studentInfo.toString();
    }

}
