/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseIntegration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @Liudmila Grati 
 */
public class MyDatabaseIO implements DB_IO {

     // Instance variables to store database connection details
    private String url;
    private String username;
    private String password;

// Default constructor with database connection details
    public MyDatabaseIO() {
        this("jdbc:mysql://localhost/cms", "pooa2024", "pooa2024");
    }
    
// Constructor with custom database connection details
    public MyDatabaseIO(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
// Method to obtain a database connection
    @Override
    public Connection getConnection() throws SQLException {
        // Establish a connection
        Connection conn = DriverManager.getConnection(url, username, password);
        // Print a console message indicating successful connection
        System.out.println("Database connected successfully!");
        // Return the connection
        return conn;
    }
    
     // Method to close a database connection
    @Override
    public void closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}