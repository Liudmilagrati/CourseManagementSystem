/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunctionalities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class UserManagement {
    private DB_IO dbIO;
     private List<User> users;
     
     public UserManagement() {
        this.users = new ArrayList<>();
        // Create the initial admin user
        User admin = new User("admin", "java", Role.ADMIN);
        users.add(admin);
    }
      public UserManagement(DB_IO dbIO) {
        this.dbIO = dbIO;
    }
      
      /**
     * Allows updating or adding a user in the database.
     *
     * @param user          The User object containing user information.
     * @param plainPassword Optional parameter if password is to be changed.
     * @param salt          Salt string for the password.
     * @return True if user was successfully updated or added, false otherwise.
     */
    public boolean updateUser(User user, String plainPassword, String salt) throws SQLException {
        // Check if the user already exists in the database
        boolean userExists = checkUserExists(user.getUsername());
     
    
}
