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

    
    
}
