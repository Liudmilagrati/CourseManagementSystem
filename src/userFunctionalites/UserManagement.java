/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userFunctionalites;

import DataBaseIntegration.DB_IO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages user-related functionalities such as adding, updating, and deleting users in the database.
 * Provides methods for updating user information including username, password, and role.
 * Also provides methods for checking user existence in the database.
 * 
 * @Liudmila Grati 
 */
public class UserManagement {
    private DB_IO dbIO; // Database I/O object for interacting with the database
    private List<User> users; // List to store User objects
     
    /**
     * Constructs a UserManagement object with an initial admin user.
     * This constructor initializes the users list with an admin user.
     */
    public UserManagement() {
        this.users = new ArrayList<>();
        // Create the initial admin user
        User admin = new User("admin", "java", Role.ADMIN);
        users.add(admin);
    }
    
    /**
     * Constructs a UserManagement object with a provided DB_IO object.
     * 
     * @param dbIO The DB_IO object for interacting with the database.
     */
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
     * @throws SQLException If a database access error occurs.
     */
    public boolean updateUser(User user, String plainPassword, String salt) throws SQLException {
        // Check if the user already exists in the database
        boolean userExists = checkUserExists(user.getUsername());
     
        String hashedPassword = null;
        if (plainPassword != null) {
            hashedPassword = HashPassword.hashPassword(plainPassword, salt, 1000);
        }

        try (Connection conn = dbIO.getConnection()) {
            if (userExists) {
                // If user exists, update the user information in the database
                String updateUserQuery = "UPDATE users SET password = ?, role = ?, lecturer_id = ?, salt = ? WHERE username = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateUserQuery)) {
                    stmt.setString(1, hashedPassword); // Update password if provided
                    stmt.setString(2, user.getRole().name());
                    stmt.setString(3, user.getRole() == Role.LECTURER ? user.getLecturerId() : null); // Set lecturer_id only if role is LECTURER
                    stmt.setString(4, salt);
                    stmt.setString(5, user.getUsername());
                    int rowsAffected = stmt.executeUpdate();
                    return rowsAffected > 0;
                }
            } else {
                // If user doesn't exist, add the user to the database
                String addUserQuery = "INSERT INTO users (user_id, username, password, role, lecturer_id, salt) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(addUserQuery)) {
                    stmt.setString(1, user.getUserID());
                    stmt.setString(2, user.getUsername());
                    stmt.setString(3, hashedPassword);
                    stmt.setString(4, user.getRole().name());
                    stmt.setString(5, user.getRole() == Role.LECTURER ? user.getLecturerId() : null); // Set lecturer_id only if role is LECTURER
                    stmt.setString(6, salt);
                    int rowsAffected = stmt.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        } catch (SQLException e) {
            // Print the error code and return false in case of an exception
            System.out.println("Error Code: " + e.getErrorCode());
            return false;
        }
    }
    
    /**
     * Deletes a user from the database (including username, password and role) 
     *
     * @param username The username of the user to delete.
     * @return True if the user was successfully deleted, false otherwise.
     */
    public boolean deleteUser(String username) {
        // SQL query to delete user data from the database
        String deleteUserQuery = "DELETE FROM users WHERE username = ?";

        try (Connection conn = dbIO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteUserQuery)) {

            // Set the username parameter in the prepared statement
            stmt.setString(1, username);

            // Execute the delete query
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Print the error code and return false in case of an exception
            System.out.println("Error Code: " + e.getErrorCode());
            return false;
        }
    }

    /**
     * Checks if a user with the given username already exists in the database.
     * 
     * @param username The username to check.
     * @return True if the user exists, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    private boolean checkUserExists(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = dbIO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
    
    /**
     * Method that allows to update the username of a user in the database.
     *
     * @param userID       The ID of the user whose username to update.
     * @param newUsername  The new username to set.
     * @return True if the username was successfully updated, false otherwise.
     */
    public boolean updateUsername(String userID, String newUsername) {
        // SQL query to update username in the database
        String updateUsernameQuery = "UPDATE users SET username = ? WHERE user_id = ?";

        try (Connection conn = dbIO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateUsernameQuery)) {

            // Set parameters for the prepared statement
            stmt.setString(1, newUsername);
            stmt.setString(2, userID);

            // Execute the update query
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Print the error code and return false in case of an exception
            System.out.println("Error Code: " + e.getErrorCode());
            return false;
        }
    }
    
    /**
     * Method that allows to update the password of a user in the database.
     *
     * @param userID       The ID of the user whose password to update.
     * @param newPassword  The new password to set.
     * @param newSalt      The new salt to use for hashing the password.
     * @return True if the password was successfully updated, false otherwise.
     */
    public boolean updatePassword(String userID, String newPassword, String newSalt) {
        // Hash the new password before storing it in the database
        String hashedPassword = HashPassword.hashPassword(newPassword, newSalt, 1000);

        // SQL query to update password in the database
        String updatePasswordQuery = "UPDATE users SET password = ?, salt = ? WHERE user_id = ?";

        try (Connection conn = dbIO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updatePasswordQuery)) {

            // Set parameters for the prepared statement
            stmt.setString(1, hashedPassword);
            stmt.setString(2, newSalt);
            stmt.setString(3, userID);

            // Execute the update query
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Print the error code and return false in case of an exception
            System.out.println("Error Code: " + e.getErrorCode());
            return false;
        }
    }
    
    /**
     * Method that allows to update the role of a user in the database.
     *
     * @param userID The ID of the user whose role to update.
     * @param newRole The new role to set.
     * @return True if the role was successfully updated, false otherwise.
     */
    public boolean updateUserRole(String userID, Role newRole) {
        // SQL query to update role in the database
        String updateRoleQuery = "UPDATE users SET role = ? WHERE user_id = ?";

        try (Connection conn = dbIO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateRoleQuery)) {

            // Set parameters for the prepared statement
            stmt.setString(1, newRole.name());
            stmt.setString(2, userID);

            // Execute the update query
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Print the error code and return false in case of an exception
            System.out.println("Error Code: " + e.getErrorCode());
            return false;
        }
    }
}
