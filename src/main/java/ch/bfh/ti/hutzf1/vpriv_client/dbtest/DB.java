/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.bfh.ti.hutzf1.vpriv_client.dbtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author fh
 */

public class DB {
    
    // Connect to database.
    String connectionString = 
        "jdbc:sqlserver://btdev.database.windows.net:1433;"
        + "database=btdev;"
        + "user=btdev@btdev;"
        + "password=abcd1234$$$$;"
        + "encrypt=true;"
        + "trustServerCertificate=false;"
        + "hostNameInCertificate=*.database.windows.net;"
        + "loginTimeout=30;";
    
    // Declare the JDBC objects.  
    Connection connection = null;   
    
    public void connect() {
        try {  
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionString);  
        }  
        catch (ClassNotFoundException | SQLException e) {  
            e.printStackTrace();  
        }    
    }
    
    public void disconnect() throws SQLException {
        connection.close();
    }
    
    public void execute(String query) throws SQLException {
        // Create and execute an SQL statement that returns some data.  
        Statement stmt = connection.createStatement();  
        stmt.execute(query);  
    }
    
    public ResultSet executeQuery(String query) throws SQLException {
        // Create and execute an SQL statement that returns some data.  
        Statement stmt = connection.createStatement();  
        return stmt.executeQuery(query);  
    }
}
