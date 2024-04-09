/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reports;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * ReportExporter class responsible for exporting reports to different formats.
 * @author Liudmila Grati
 */
public class ReportExporter {

    /**
     * Exports the report content based on the specified report format.
     * @param reportContent The content of the report to be exported.
     * @param reportFormat The format in which the report should be exported.
     */
    public void exporterReport(String reportContent, ReportFormat reportFormat) {
        switch (reportFormat) {
            case CONSOLE:
                outputToConsole(reportContent);
                break;
            case TEXT_FILE:
                exportToTextFile(reportContent, "report.txt");
                break;
            case CSV_FILE:
                exportToCsvFile(reportContent, "report.csv");
                break;
            default:
                System.out.println("Not available the report format type");
                break;
        }
    }

    /**
     * Outputs the report content to the console.
     * @param reportContent The content of the report to be output to the console.
     */
    private void outputToConsole(String reportContent) {
        System.out.println(reportContent);
    }

    /**
     * Exports the report content to a text file.
     * @param reportContent The content of the report to be exported.
     * @param fileName The name of the text file to which the report should be exported.
     */
    private void exportToTextFile(String reportContent, String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(reportContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exports the report content to a CSV file.
     * @param reportContent The content of the report to be exported.
     * @param fileName The name of the CSV file to which the report should be exported.
     */
    private void exportToCsvFile(String reportContent, String fileName) {
        // CSV-specific formatting can be applied here if needed
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(reportContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enumeration representing different report formats.
     */
    enum ReportFormat {
        CONSOLE, TEXT_FILE, CSV_FILE
    }
}
