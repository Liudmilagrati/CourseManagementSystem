/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseIntegration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MyDatabaseIO class provides methods to establish and close a database connection.
 * It implements the DB_IO interface.
 * 
 * @author Liudmila Grati
 */
public class MyDatabaseIO implements DB_IO {

    // Instance variables to store database connection details
    private String url;
    private String username;
    private String password;

    /**
     * Default constructor with default database connection details.
     * It connects to the MySQL database running on localhost using default credentials.
     */
    public MyDatabaseIO() {
        this("jdbc:mysql://localhost/cms", "pooa2024", "pooa2024");
    }
    
    /**
     * Constructor with custom database connection details.
     * 
     * @param url      the URL of the database
     * @param username the username for connecting to the database
     * @param password the password for connecting to the database
     */
    public MyDatabaseIO(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    /**
     * Obtains a database connection.
     * 
     * @return a Connection object representing the database connection
     * @throws SQLException if a database access error occurs
     */
    @Override
    public Connection getConnection() throws SQLException {
        // Establish a connection
        Connection conn = DriverManager.getConnection(url, username, password);
        // Print a console message indicating successful connection
        System.out.println("Database connected successfully!");
        // Return the connection
        return conn;
    }
    
    /**
     * Closes a database connection.
     * 
     * @param conn the Connection object representing the database connection to be closed
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}