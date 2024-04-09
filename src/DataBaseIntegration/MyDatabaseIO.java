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
