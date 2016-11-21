/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.ti.hutzf1.vpriv_client.dbtest;

import java.sql.SQLException;

/**
 *
 * @author fh
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        DB db = new DB();
        db.connect();
        db.execute("INSERT INTO Vehicles VALUES (N'38272739')");
        db.disconnect();
    }
    
}
