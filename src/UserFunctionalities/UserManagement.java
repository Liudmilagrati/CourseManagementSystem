/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunctionalities;

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
    
}
