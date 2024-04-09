/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunctionalities;

import DataBaseIntegration.DB_IO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides authentication functionality for users.
 */
public class Authentication {

    private DB_IO dbIO; // Renamed DatabaseIO to DB_IO

    /**
     * Constructs an instance of Authentication with the specified DB_IO.
     *
     * @param dbIO The DB_IO instance used for database operations.
     */
    public Authentication(DB_IO dbIO) { // Renamed DatabaseIO to DB_IO
        this.dbIO = dbIO;
    }

    /**
     * Authenticates a user based on the entered username and password.
     *
     * @param username       The username of the user to authenticate.
     * @param enteredPassword The entered password by the user.
     * @return The authenticated User object, or null if authentication fails.
     * @throws SQLException If an SQL exception occurs.
     */
    public User authenticateUser(String username, String enteredPassword) throws SQLException {
        // SQL query to retrieve user information based on username
        String query = "SELECT user_id, username, role, lecturer_id, password, salt FROM users WHERE username = ?";

        try (Connection conn = dbIO.getConnection(); // Renamed DatabaseIO to DB_IO
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the username parameter in the prepared statement
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve user information from the result set
                    String userId = rs.getString("user_id");
                    String roleStr = rs.getString("role");
                    Role role = Role.valueOf(roleStr.toUpperCase());
                    String lecturerId = rs.getString("lecturer_id");
                    String storedHash = rs.getString("password");
                    String salt = rs.getString("salt");

                    // HashPassword the entered password with the stored salt using the same iterations used for hashing stored passwords
                    String hashedEnteredPassword = HashPassword.hashPassword(enteredPassword, salt, 1000);

                    // Compare the hashed entered password with the stored hash
                    if (hashedEnteredPassword.equals(storedHash)) {
                        // Authentication successful, create a User object
                        return new User(userId, username, role, lecturerId, salt);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null; // Authentication failed
        }
    }
}
