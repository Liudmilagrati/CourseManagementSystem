/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseIntegration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public interface DB_IO {
    Connection getConnection() throws SQLException;
    public void closeConnection(Connection conn) throws SQLException;
}
