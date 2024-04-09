/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunctionalities;

/**
 * @Liudmila Grati 
 * Represents the roles that a user can have in the system.
 * Three roles are defined: ADMIN, OFFICE, and LECTURER.
 * Each role corresponds to different levels of access and privileges.
 * 
 * @author user
 */
public enum Role {
    ADMIN,      // Represents an administrative user with full system access
    OFFICE,     // Represents an office user with restricted access for administrative tasks
    LECTURER    // Represents a lecturer user with access to course-related functionalities
}
