/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package coursemanagementsystem;

import DataBaseIntegration.MyDatabaseIO;
import Reports.ReportGenerator;
import userFunctionalites.Authentication;
import userFunctionalites.UserManagement;

/**
 * GitHub link: https://github.com/Liudmilagrati/CourseManagementSystem
 * Main class for the Course Management System.
 * @author Liudmila Grati
 */
public class CourseManagementSystem {

    /**
     * Main method, entry point of the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Create an instance of MyDatabaseIO for database operations
        MyDatabaseIO dbIO = new MyDatabaseIO();

        // Initialize user functionalities
        Authentication authentication = new Authentication(dbIO);
        UserManagement userService = new UserManagement(dbIO);
        
        // Initialize ReportGenerator for generating reports
        ReportGenerator reportGenerator = new ReportGenerator(dbIO);
        
        // Create a menu object to display the login menu
        cmsMenu menu = new cmsMenu(authentication, userService, reportGenerator);
        menu.displayLoginMenu();
    }
}