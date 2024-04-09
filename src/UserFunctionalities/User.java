/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunctionalities;

/**
 *
 * @Liudmila Grati 
 */
public class User {
  // Intanciate Private Fields
    private String username;
    private String password;
    private String userID;
    private String lecturerId;
    private Role role;
    private String salt; 
    
    /**
     * Constructs a user with the given userID, username, and role.
     *
     * @param userID   The unique identifier of the user.
     * @param username The username of the user.
     * @param role     The role of the user.
     */
    public User(String userID, String username, Role role) {
        this.userID = userID;
        this.username = username;
        this.role = role;
    }
     /**
     * Constructs a user with the given userID, username, role, lecturerId, and salt.
     *
     * @param userID     The unique identifier of the user.
     * @param username   The username of the user.
     * @param role       The role of the user.
     * @param lecturerId The lecturer ID associated with the user.
     * @param salt       The salt used for password hashing.
     */
    public User(String userID, String username, Role role, String lecturerId, String salt) {
        this.userID = userID;
        this.username = username;
        this.role = role;
        this.lecturerId = lecturerId;
        this.salt = salt;
    }
// Getters and Setters

    /**
     * Gets the salt used for password hashing.
     *
     * @return The salt used for password hashing.
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Sets the salt used for password hashing.
     *
     * @param salt The salt used for password hashing.
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Gets the lecturer ID associated with the user.
     *
     * @return The lecturer ID associated with the user.
     */
    public String getLecturerId() {
        return lecturerId;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the role of the user.
     *
     * @return The role of the user.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The unique identifier of the user.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the role of the user.
     *
     * @param role The role of the user.
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
    
    
}
