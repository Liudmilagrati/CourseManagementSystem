/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reports;

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

}
