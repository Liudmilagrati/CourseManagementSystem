/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import Reports.ReportGenerator;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import userFunctionalites.Authentication;
import userFunctionalites.User;
import userFunctionalites.UserManagement;

/**
 *
 * @author user
 */
public class cmsMenu {
    private Authentication authentication;
    private UserManagement userManagement;
    private ReportGenerator reportGenerator;
    
    /**
     * Constructor for cmsMenu object with the provided authentication, user management, and report generator.
     *
     * @param authentication  The Authentication object for user authentication.
     * @param userManagement  The UserManagement object for user management operations.
     * @param reportGenerator The ReportGenerator object for generating reports.
     */
    public cmsMenu(Authentication authentication, UserManagement userManagement, ReportGenerator reportGenerator) {
        this.authentication = authentication;
        this.userManagement = userManagement;
        this.reportGenerator = reportGenerator;
    }
    
    /**
     * This method displays the login menu to the terminal 
     *
     */
    public void displayLoginMenu(){
        boolean validInput = false;
        while(!validInput){
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("*********************************************");
                System.out.println("Welcome to the Course Management System (CMS)");
                System.out.println("");               
                System.out.println("If you would prefer to cancel and exit please enter : 'exit'.");
                System.out.println("*********************************************");
                System.out.println("Please enter your: ");
                System.out.println("Username: ");
                String username = sc.nextLine();
                
                if (username.equalsIgnoreCase("exit"))
                    break;

                System.out.println("Password: ");
                String password = sc.nextLine();

                User user = authentication.authenticateUser(username, password);

                if(user != null){
                    System.out.println("Login Successful.");
                    displayLoginMenu();
                }else{
                    System.out.println("Login failed. Please check your username and password.");
                    // do a loop to keep going
                }
            } catch (SQLException ex) {
                Logger.getLogger(cmsMenu.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
}
