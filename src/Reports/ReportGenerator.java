/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reports;

import DataBaseIntegration.DB_IO; // Corrected package name
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import userFunctionalites.User; // Corrected package name

/**
 * ReportGenerator class responsible for generating reports: Course Report, Student Report, and Lecturer Report.
 * @author Liudmila Grati
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
        // Implementation omitted for brevity
        return null;
    }

    // Method to generate the course the student is undertaking
    public String getStudentCourse(String studentId) {
        // Implementation omitted for brevity
        return null;
    }

    // Method to retrieve modules completed by a student with corresponding grades
    private List<String> getCompletedModules(String studentId, Connection connection) throws SQLException {
        // Implementation omitted for brevity
        return null;
    }

    // Method to retrieve modules the student needs to repeat
    private List<String> getModulesToRepeat(String studentId, Connection connection) throws SQLException {
        // Implementation omitted for brevity
        return null;
    }

    // Generate Lecturer Report method
    public String generateLecturerReport(int lecturerId) {
        // Implementation omitted for brevity
        return null;
    }

    // Method to retrieve modules taught by the lecturer this semester
    private List<String> getModulesTaught(int lecturerId, Connection connection) throws SQLException {
        // Implementation omitted for brevity
        return null;
    }

    // Method to retrieve number of students taking the modules taught by the lecturer
    private int getNumberOfStudents(int lecturerId, Connection connection) throws SQLException {
        // Implementation omitted for brevity
        return 0;
    }

    // Method to retrieve types of modules the lecturer can teach
    private List<String> getModuleTypes(int lecturerId, Connection connection) throws SQLException {
        // Implementation omitted for brevity
        return null;
    }
    
    // Output Student Report
    /**
     * Outputs a student report based on the provided student ID and report format.
     * @param studentId The ID of the student for whom the report is generated.
     * @param reportFormat The format of the report (CSV, TEXT, CONSOLE).
     */
    public void outputStudentReport(String studentId, ReportFormat reportFormat) {
        // Check if reportGenerator is not null before using it
        if (reportGenerator != null) {
            reportGenerator.outputStudentReport(studentId, reportFormat);
        } else {
            System.out.println("Error: Report generator is not initialized.");
        }
    }

    // Output Lecturer Report
    /**
     * Outputs a lecturer report based on the provided lecturer ID and report format.
     * @param lecturerId The ID of the lecturer for whom the report is generated.
     * @param reportFormat The format of the report (CSV, TEXT, CONSOLE).
     */
    public void outputLecturerReport(String lecturerId, ReportFormat reportFormat) {
        reportGenerator.outputLecturerReport(lecturerId, reportFormat);
    }

    /**
     * Outputs a course report based on the provided course ID and report format.
     * @param courseId The ID of the course for which the report is generated.
     * @param reportFormat The format of the report (CSV, TEXT, CONSOLE).
     */
    public void outputCourseReport(String courseId, ReportFormat reportFormat) {
        reportGenerator.outputCourseReport(courseId, reportFormat);
    }
}
