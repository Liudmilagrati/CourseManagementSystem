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
import userFunctionalites.HashPassword;
import userFunctionalites.Role;
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
    public void UserMenu(User user, Scanner scanner) throws SQLException{
        Role userRole = user.getRole();
        
        switch(userRole){
            case OFFICE:
                // SHOW OFFICE MENU
                officeMenu(user,scanner);
                break;
            case LECTURER:
                //SHOW LECTURER MENU
                lecturerMenu(user,scanner);
                break;
            case ADMIN:
                //SHOW ADMIN MENU
                adminMenu(user, scanner);
                break;
        }
        
    }

    /**
     * This method displays the office menu with the office possible actions
     * 
     * @param user the users accessing the menu
     * @param scanner a scanner object
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
                    case 1:{
                        // generate Student Report
                        System.out.println("Enter the student ID to generate the student report: ");
                        String studentId = scanner.next();
                        
                        scanner.nextLine();
                        System.out.println("Please select what type of file you would prefer:");
                        System.out.println("1. CSV");
                        System.out.println("2. TEXT");
                        System.out.println("3. CONSOLE.");
                        
                        int outputSelection = scanner.nextInt();
                        ReportFormat outputType = getReportFormatFromSelection(outputSelection);
                        if (outputType != null){
                            reportGenerator.outputStudentReport(studentId,outputType);
                        }else{
                            System.out.println("Invalid output option, please try again.");
                        }
                        break;
                    }
                    case 2:{
                        // Generate Lecturer report
                        System.out.println("Enter the lecturer ID for the lecturer report: ");
                        String lecturerId = scanner.next();
                        
                        scanner.nextLine();
                        System.out.println("Please select an output type:");
                        System.out.println("1. CSV");
                        System.out.println("2. TEXT");
                        System.out.println("3. CONSOLE.");
                        System.out.println("Enter the number of your choice: ");
                        int outputSelection = scanner.nextInt();
                        ReportFormat outputType = getReportFormatFromSelection(outputSelection);
                        if (outputType != null){
                            ReportFormat reportFormat = outputType; // Initialize reportFormat
                            reportGenerator.outputLecturerReport(lecturerId, reportFormat);
                        }else{
                            System.out.println("Invalid output option, please try again.");
                        }
                        break;
                    }
                    case 3:{
                        //Generate course Report
                        System.out.println("Enter the course ID for the course report: ");
                        String courseId = scanner.next();
                        
                        scanner.nextLine();
                        System.out.println("Please select an output type:");
                        System.out.println("1. CSV");
                        System.out.println("2. TEXT");
                        System.out.println("3. CONSOLE.");
                        System.out.println("Enter the number of your choice: ");
                        int outputSelection = scanner.nextInt();
                        ReportFormat reportFormat = getReportFormatFromSelection(outputSelection);
                        if (reportFormat != null){
                            reportGenerator.outputCourseReport(courseId, reportFormat);
                        }else{
                            System.out.println("Invalid output option, please try again.");
                        }
                        break;
                    }
                    case 4:{
                        // change the usesr's username 
                        scanner.nextLine();
                        System.out.println("Enter your new username: ");
                        String newUsername = scanner.next();
                        
                       UserManagement userManagement = new UserManagement();
                       boolean usernameChanged = userManagement.updateUsername(user.getUserID(), newUsername);

                        if(usernameChanged){
                            System.out.println("Username Changed successfully.");
                        }else{
                            System.out.println("Failed to change username. please try again.");
                        }
                        break;
                    }
                        
                    case 5:
                        //change the user's passoword
                        scanner.nextLine();
                        System.out.println("Enter your new password");
                        String newPassword = scanner.next();
                        // HashPassword the password
                        String salt = HashPassword.generateSalt();
                        String hashedPassword = HashPassword.hashPassword(newPassword, salt, 1000);
                        // Call the changeMyPassword method passing the hashedPassword and salt
                        boolean passwordChanged = userManagement.updatePassword(user.getUserID(),hashedPassword,salt);
                        if(passwordChanged){
                            System.out.println("Password changed successfully.");
                        }else{
                            System.out.println("Error in updating the password. You can try again.");
                        }
                        break;                   
                case 6:
                        validInput = true;
                        break;
                    default:
                        System.out.println("Selection not valid, try again.");
                        break;
                    
                }
         
            }catch(Exception e){
                
            }
        }
    }
    /**
     * This method displays the admin menu with the admin possible actions 
     * 
     * @param user the users accessing the menu
     * @param scanner a scanner object
     */
    public void adminMenu(User user, Scanner scanner) throws SQLException{
//        UserService userService = new UserService(databaseIO);
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
                    case 1:{
                        //Option 1: Add user 
                        scanner.nextLine();
                        System.out.println("Enter username: ");
                        String username = scanner.nextLine();
                        
                        System.out.println("Enter password: ");
                        String password = scanner.nextLine();
                        
                        System.out.println("Enter role (ADMIN, OFFICE, LECTURER): ");
                        String roleStr = scanner.nextLine();
                        Role role = Role.valueOf(roleStr.toUpperCase());
                        
                        String lecturerId = null;
                        if(role == Role.LECTURER){
                            System.out.println("Enter lecturer ID: ");
                            lecturerId = scanner.nextLine();
                        }
                        
                        String salt = HashPassword.generateSalt();
                        String userId = null;
                        
                        User newUser = new User(userId, username, role, lecturerId, salt); // instanciate new User
                        boolean userAdded = userManagement.updateUser(newUser, password, salt);
                        
                        if(userAdded){
                            System.out.println("User was added.");
                        }else{
                            System.out.println("Error occured to add User, try again.");
                        }
                        break;
                    }

                    case 2: {
    // Option 2: Update user
    scanner.nextLine(); 
    System.out.println("Please Enter the user ID of the user that you would like to update: ");
    String updateUserId = scanner.nextLine();                       

    System.out.println("Enter new username (press Enter to keep current): ");
    String newUsername = scanner.nextLine();

    System.out.println("Enter new role (ADMIN, OFFICE, LECTURER) or press Enter to keep current: ");
    String newRoleStr = scanner.nextLine();
    
    // Check if the user wants to update the username or role
    if (!newUsername.isEmpty() || !newRoleStr.isEmpty()) {
        // Get the user object based on the user ID
        User userToUpdate = getUserById(updateUserId);

        // Update username if provided
        if (!newUsername.isEmpty()) {
            userToUpdate.setUsername(newUsername);
        }

        // Update role if provided
        if (!newRoleStr.isEmpty()) {
            Role newRole = Role.valueOf(newRoleStr.toUpperCase());
            userToUpdate.setRole(newRole);
        }

        // Call update User method 
        boolean updated = userManagement.updateUser(userToUpdate, null, null);
        if (updated) {
            System.out.println("User updated successfully.");
        } else {
            System.out.println("Failed to update user.");
        }
    } else {
        System.out.println("No changes provided. User not updated.");
    }
    break;
}
                    case 3:
                        //deleteUser
                        scanner.nextLine();
                        System.out.println("Enter the user ID of the user you wish to delete: ");
                        String deleteUserId = scanner.next();
                        
                        if(deleteUserId != null){
                            boolean deleted = userManagement.deleteUser(deleteUserId);
                            if(deleted){
                                System.out.println("User deleteded successfully.");
                            }else{
                                System.out.println("Error deleting user.Please try again.");
                            }
                        }
                        break;
                    case 4:{
                        // change the usesr's username 
                        scanner.nextLine();
                        System.out.println("Enter your new username: ");
                        String newUsername = scanner.next();
                        
                       UserManagement userManagement = new UserManagement();
                       boolean usernameChanged = userManagement.updateUsername(user.getUserID(), newUsername);

                        if(usernameChanged){
                            System.out.println("Username Changed successfully.");
                        }else{
                            System.out.println("Failed to change username. please try again.");
                        }
                        break;
                    }
                    case 5:
                        //change the user's passoword
                        scanner.nextLine();
                        System.out.println("Enter your new password");
                        String newPassword = scanner.next();
                        // HashPassword the password
                        String salt = HashPassword.generateSalt();
                        String hashedPassword = HashPassword.hashPassword(newPassword, salt, 1000);
                        // Call the changeMyPassword method passing the hashedPassword and salt
                        boolean passwordChanged = userManagement.updatePassword(user.getUserID(),hashedPassword,salt);
                        if(passwordChanged){
                            System.out.println("Password changed successfully.");
                        }else{
                            System.out.println("Error in updating the password. You can try again.");
                        }
                        break;   
                    
                    case 6:
                        // Change the users role 
                        scanner.nextLine();
                        System.out.println("Please select one of the roles that you would like to make a change:");
                        System.out.println("1. Admin");
                        System.out.println("2. Lecturer");
                        System.out.println("3. Office");
                        int roleSection = scanner.nextInt();
                        Role newRole = null;
                        switch(roleSection){
                            case 1:
                                newRole = Role.ADMIN;
                                break;
                            case 2:
                                newRole = Role.LECTURER;
                                break;
                            case 3:
                                newRole = Role.OFFICE;
                                break;
                            default:
                                System.out.println("Invalid sected role, try again.");
                                break;
                        }
                        if(newRole != null){
                            boolean roleChanged = userManagement.updateUserRole(user.getUserID(), newRole);
                            if (roleChanged){
                                System.out.println("Role was changed successfully.");
                            }else{
                                System.out.println("Failed to change role. You can try again.");
                            }
                        }
                        break;
                    case 7:
                        validInput = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                    
                }
                
                
                
            }catch(Exception e){
                
            }
        }
    }
        
        //---------------------------------------------------------------------------------------
        
        /**
     * This method displays the lecturer menu with the lecturer possible actions
     * 
     * @param user the users accessing the menu
     * @param scanner a scanner object
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
                    case 1:
                        scanner.nextLine();
                        // generate self report
                        System.out.println("Please select an output type:");
                        System.out.println("1. CSV");
                        System.out.println("2. TEXT");
                        System.out.println("3. CONSOLE.");
                        System.out.println("Enter the number of your choice: ");
                        int outputSelection = scanner.nextInt();
                        ReportFormat reportFormat = getReportFormatFromSelection(outputSelection);
                        if (reportFormat != null){
                        String lecturerId = null;
                            reportGenerator.outputLecturerReport(lecturerId, reportFormat);
                        }else{
                            System.out.println("Invalid output option, please try again.");
                        }
                        break;
                    case 2: {
    scanner.nextLine();
    // Change my username
    System.out.println("Enter your new username: ");
    String newUsername = scanner.nextLine();
    boolean usernameChanged = userManagement.updateUsername(user.getUserID(), newUsername);
    if (usernameChanged) {
        System.out.println("Username changed successfully.");
    } else {
        System.out.println("Failed to change username. Please try again.");
    }
    break;
}

case 3: {
    scanner.nextLine();
    // Change my password
    System.out.println("Enter your new password: ");
    String newPassword = scanner.nextLine();
    // Generate salt and hash the new password
    String salt = HashPassword.generateSalt();
    String hashedPassword = HashPassword.hashPassword(newPassword, salt, 1000);
    // Call the updatePassword method passing the hashedPassword and salt
    boolean passwordChanged = userManagement.updatePassword(user.getUserID(), hashedPassword, salt);
    if (passwordChanged) {
        System.out.println("Password changed successfully.");
    } else {
        System.out.println("Error updating password. Please try again.");
    }
    break;
}

case 4: {
    validInput = true;
    break;
}

default:
    System.out.println("Invalid option. Please try again.");
    break;
                }
            }catch(Exception e){
                scanner.nextLine(); // Clear scanner buffer
                System.out.println("An error occured please try again.");
            }
        }

    }

    private ReportFormat getReportFormatFromSelection(int outputSelection) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
        

    private User getUserById(String updateUserId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
            
        
    
    

}

   

  
}
