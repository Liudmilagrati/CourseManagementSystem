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
 *
 * @Liudmila Grati 
 */
public class CourseManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Create an instance of MyDatabaseIO
        MyDatabaseIO dbIO = new MyDatabaseIO();

        
    // Initialise user Functionalities 
        Authentication authentication = new Authentication(dbIO);
        UserManagement userService = new UserManagement(dbIO);
        
        // Initialise ReportGenerator
        ReportGenerator reportGenerator = new ReportGenerator(dbIO);
        
       
        cmsMenu menu = new cmsMenu(authentication,userService,reportGenerator);
        menu.displayLoginMenu();
        
}
}
