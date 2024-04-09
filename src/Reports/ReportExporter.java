/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reports;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * @author user
 */
public class ReportExporter {
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
                System.out.println(" Not available the report format type");
                break;
        }
    }

    private void outputToConsole(String reportContent) {
        System.out.println(reportContent);
    }

    private void exportToTextFile(String reportContent, String fileName) {
        try ( PrintWriter out = new PrintWriter(fileName)) {
            out.println(reportContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void exportToCsvFile(String reportContent, String fileName) {
        // CSV-specific formatting can be applied here if needed
        try ( PrintWriter out = new PrintWriter(fileName)) {
            out.println(reportContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void exporterReport(String reportContent, Reports.ReportFormat reportFormat) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    enum ReportFormat {
    CONSOLE, TEXT_FILE, CSV_FILE
}
}


