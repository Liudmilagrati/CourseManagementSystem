/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseIntegration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The DB_IO class provides methods for handling database connections.
 */

public interface DB_IO {
    
    /**
     * Retrieves a connection to the database.
     * 
     * @return a Connection object representing the database connection
     * @throws SQLException if a database access error occurs
     */
    
    Connection getConnection() throws SQLException;
    
       /**
     * Closes the database connection.
     * 
     * @param conn the Connection object representing the database connection to be closed
     * @throws SQLException if a database access error occurs
     */
    
    public void closeConnection(Connection conn) throws SQLException;
}
