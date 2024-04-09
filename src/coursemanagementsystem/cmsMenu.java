/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import Reports.ReportFormat;
import Reports.ReportGenerator;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import userFunctionalites.Authentication;
import userFunctionalites.Role;
import userFunctionalites.User;
import userFunctionalites.UserManagement;

/**
 * Class representing the Course Management System menu.
 * @author Liudmila Grati
 */
public class cmsMenu {
    private Authentication authentication;
    private UserManagement userManagement;
    private ReportGenerator reportGenerator;
    
    /**
     * Constructor for the cmsMenu object with the provided authentication, user management, and report generator.
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
     * This method displays the login menu to the terminal.
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
    
    /**
     * This method displays the user menu based on the user's role.
     * @param user The user object.
     * @param scanner The scanner object for user input.
     */
    public void UserMenu(User user, Scanner scanner) throws SQLException{
        Role userRole = user.getRole();
        
        switch(userRole){
            case OFFICE:
                officeMenu(user,scanner);
                break;
            case LECTURER:
                lecturerMenu(user,scanner);
                break;
            case ADMIN:
                adminMenu(user, scanner);
                break;
        }
    }

    /**
     * This method displays the office menu with the office possible actions.
     * @param user The user accessing the menu.
     * @param scanner The scanner object for user input.
     */
    public void officeMenu(User user, Scanner scanner){
        boolean validInput = false;
        while (!validInput){
            try{
                System.out.println("Welcome to the Course Management System - OFFICE menu.");
                System.out.println("Select one of the following options:");
                System.out.println("1. Generate student report.");
                System.out.println("2. Generate lecturer report");
                System.out.println("3. Generate course report");
                System.out.println("4. Change my username");
                System.out.println("5. Change my password");
                System.out.println("6. Logout the menu");
              
                int selection = scanner.nextInt();
                
                switch(selection){
                    // Cases for various office menu options...
                }
         
            }catch(Exception e){
                // Handle exceptions
            }
        }
    }
    
    /**
     * This method displays the admin menu with the admin possible actions.
     * @param user The user accessing the menu.
     * @param scanner The scanner object for user input.
     */
    public void adminMenu(User user, Scanner scanner) throws SQLException{
        boolean validInput = false;
        while (!validInput){
            try{
                System.out.println("Welcome to the Course Management System - ADMIN menu.");
                System.out.println("Please select one from the following options:.");
                System.out.println("1. Add User.");
                System.out.println("2. Update User.");
                System.out.println("3. Delete User.");
                System.out.println("4. Change my username");
                System.out.println("5. Change my password");
                System.out.println("6. Change my role");
                System.out.println("7. Logout");
                
                int selection = scanner.nextInt();
                
                switch(selection){
                    // Cases for various admin menu options...
                }
                
            }catch(Exception e){
                // Handle exceptions
            }
        }
    }
        
    /**
     * This method displays the lecturer menu with the lecturer possible actions.
     * @param user The user accessing the menu.
     * @param scanner The scanner object for user input.
     */
    public void lecturerMenu(User user, Scanner scanner){
        boolean validInput = false;
        while (!validInput){
            try{
                System.out.println("Welcome to the LECTURER menu.");
                
                System.out.println("1. Generate my report.");
                System.out.println("2. Change my username.");
                System.out.println("3. Change my password.");
                System.out.println("4. Logout.");
                System.out.println("Enter the number of your choice: ");
                
                int choice = scanner.nextInt();
                
                switch(choice){
                    // Cases for various lecturer menu options...
                }
            }catch(Exception e){
                scanner.nextLine(); // Clear scanner buffer
                System.out.println("An error occured please try again.");
            }
        }
    }

    /**
     * Helper method to get a ReportFormat enum based on user input.
     * @param outputSelection The user's selection.
     * @return The corresponding ReportFormat enum.
     */
    private ReportFormat getReportFormatFromSelection(int outputSelection) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Helper method to get a user by ID.
     * @param updateUserId The user ID to search for.
     * @return The User object corresponding to the ID.
     */
    private User getUserById(String updateUserId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }   
}
