/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
// Method to generate the course the student is undertaking
    public String getStudentCourse(String studentId) {
        StringBuilder studentCourse = new StringBuilder();

        try (Connection connection = dbIO.getConnection()) {
            String courseQuery = "SELECT course_name FROM Courses WHERE course_id = " +
                                  "(SELECT course_id FROM Modules WHERE module_id IN " +
                                  "(SELECT module_id FROM Module_Enrollments WHERE student_id = ? LIMIT 1))";
            try (PreparedStatement courseStatement = connection.prepareStatement(courseQuery)) {
                courseStatement.setString(1, studentId);
                try (ResultSet courseResult = courseStatement.executeQuery()) {
                    if (courseResult.next()) {
                        String courseName = courseResult.getString("course_name");
                        studentCourse.append("Course: ").append(courseName);
                    } else {
                        studentCourse.append("No course found for the student with ID: ").append(studentId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }

        return studentCourse.toString();
    }
// Method to retrieve modules completed by a student with corresponding grades
    private List<String> getCompletedModules(String studentId, Connection connection) throws SQLException {
        List<String> completedModules = new ArrayList<>();
        String completedModulesQuery = "SELECT Modules.module_name, Grades.grade_score FROM Modules " +
                                        "INNER JOIN Grades ON Modules.module_id = Grades.module_id " +
                                        "WHERE student_id = ?";
        try (PreparedStatement completedModulesStatement = connection.prepareStatement(completedModulesQuery)) {
            completedModulesStatement.setString(1, studentId);
            try (ResultSet completedModulesResult = completedModulesStatement.executeQuery()) {
                while (completedModulesResult.next()) {
                    String moduleName = completedModulesResult.getString("module_name");
                    int gradeScore = completedModulesResult.getInt("grade_score");
                    completedModules.add(moduleName + " (Grade: " + gradeScore + ")");
                }
            }
        }
        return completedModules;
    }

    // Method toReportGe retrieve modules the student needs to repeat
    private List<String> getModulesToRepeat(String studentId, Connection connection) throws SQLException {
        List<String> modulesToRepeat = new ArrayList<>();
        String modulesToRepeatQuery = "SELECT module_name FROM Modules " +
                                       "WHERE module_id IN (SELECT module_id FROM Module_Enrollments WHERE student_id = ? " +
                                       "GROUP BY module_id HAVING MAX(grade_id) < (SELECT MAX(grade_id) FROM Grades WHERE student_id = ?))";
        try (PreparedStatement modulesToRepeatStatement = connection.prepareStatement(modulesToRepeatQuery)) {
            modulesToRepeatStatement.setString(1, studentId);
            modulesToRepeatStatement.setString(2, studentId);
            try (ResultSet modulesToRepeatResult = modulesToRepeatStatement.executeQuery()) {
                while (modulesToRepeatResult.next()) {
                    modulesToRepeat.add(modulesToRepeatResult.getString("module_name"));
                }
            }
        }
        return modulesToRepeat;
    }

// Generate Lecturer Report method
    public String generateLecturerReport(int lecturerId) {
        StringBuilder report = new StringBuilder();

        try (Connection connection = dbIO.getConnection()) {
            // Query to retrieve lecturer information
            String lecturerQuery = "SELECT lecturer_name, lecturer_surname, lecturer_role FROM Lecturers WHERE lecturer_id = ?";
            try (PreparedStatement lecturerStatement = connection.prepareStatement(lecturerQuery)) {
                lecturerStatement.setInt(1, lecturerId);
                try (ResultSet lecturerResult = lecturerStatement.executeQuery()) {
                    if (lecturerResult.next()) {
                        // Extract lecturer details
                        String lecturerName = lecturerResult.getString("lecturer_name");
                        String lecturerSurname = lecturerResult.getString("lecturer_surname");
                        String lecturerRole = lecturerResult.getString("lecturer_role");

                        // Append lecturer information to the report
                        report.append("Lecturer Name: ").append(lecturerName).append("\n");
                        report.append("Lecturer Surname: ").append(lecturerSurname).append("\n");
                        report.append("Lecturer Role: ").append(lecturerRole).append("\n\n");

                        // Retrieve modules taught by the lecturer this semester
                        List<String> modulesTaught = getModulesTaught(lecturerId, connection);
                        report.append("Modules Taught:\n");
                        for (String moduleName : modulesTaught) {
                            report.append("- ").append(moduleName).append("\n");
                        }
                        report.append("\n");

                        // Retrieve number of students taking the modules taught by the lecturer
                        int numberOfStudents = getNumberOfStudents(lecturerId, connection);
                        report.append("Number of Students: ").append(numberOfStudents).append("\n\n");

                        // Retrieve types of modules the lecturer can teach
                        List<String> moduleTypes = getModuleTypes(lecturerId, connection);
                        report.append("Types of Modules the Lecturer can Teach:\n");
                        for (String moduleType : moduleTypes) {
                            report.append("- ").append(moduleType).append("\n");
                        }
                    } else {
                        report.append("No lecturer found with ID: ").append(lecturerId).append("\n");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }

        return report.toString();
    }

    // Method to retrieve modules taught by the lecturer this semester
    private List<String> getModulesTaught(int lecturerId, Connection connection) throws SQLException {
        List<String> modulesTaught = new ArrayList<>();
        String moduleQuery = "SELECT module_name FROM Modules WHERE lecturer_id = ?";
        try (PreparedStatement moduleStatement = connection.prepareStatement(moduleQuery)) {
            moduleStatement.setInt(1, lecturerId);
            try (ResultSet moduleResult = moduleStatement.executeQuery()) {
                while (moduleResult.next()) {
                    modulesTaught.add(moduleResult.getString("module_name"));
                }
            }
        }
        return modulesTaught;
    }

    // Method to retrieve number of students taking the modules taught by the lecturer
    private int getNumberOfStudents(int lecturerId, Connection connection) throws SQLException {
        int numberOfStudents = 0;
        String studentCountQuery = "SELECT COUNT(DISTINCT student_id) AS num_students FROM Module_Enrollments WHERE module_id IN (SELECT module_id FROM Modules WHERE lecturer_id = ?)";
        try (PreparedStatement studentCountStatement = connection.prepareStatement(studentCountQuery)) {
            studentCountStatement.setInt(1, lecturerId);
            try (ResultSet studentCountResult = studentCountStatement.executeQuery()) {
                if (studentCountResult.next()) {
                    numberOfStudents = studentCountResult.getInt("num_students");
                }
            }
        }
        return numberOfStudents;
    }

    // Method to retrieve types of modules the lecturer can teach
    private List<String> getModuleTypes(int lecturerId, Connection connection) throws SQLException {
        List<String> moduleTypes = new ArrayList<>();
        String moduleTypeQuery = "SELECT DISTINCT module_type FROM Modules WHERE lecturer_id = ?";
        try (PreparedStatement moduleTypeStatement = connection.prepareStatement(moduleTypeQuery)) {
            moduleTypeStatement.setInt(1, lecturerId);
            try (ResultSet moduleTypeResult = moduleTypeStatement.executeQuery()) {
                while (moduleTypeResult.next()) {
                    moduleTypes.add(moduleTypeResult.getString("module_type"));
                }
            }
        }
        return moduleTypes;
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

    
    /** Output lecturer Report

     * @param lecturerId The ID of the lecturer for whom the report is generated.
     * @param reportFormat The format of the report (CSV, TEXT, CONSOLE).
     */
    public void outputLecturerReport(String lecturerId, ReportFormat reportFormat) {
        reportGenerator.outputLecturerReport(lecturerId, reportFormat);
    }

/**
     * Output lecturer Report.
     * @param courseId The ID of the course for which the report is generated.
     * @param reportFormat The format of the report (CSV, TEXT, CONSOLE).
     */
    public void outputCourseReport(String courseId, ReportFormat reportFormat) {
        reportGenerator.outputCourseReport(courseId, reportFormat);
    }
    
    
}
