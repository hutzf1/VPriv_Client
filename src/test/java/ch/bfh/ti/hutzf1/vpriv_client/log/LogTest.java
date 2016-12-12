/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.ti.hutzf1.vpriv_client.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fh
 */
public class LogTest {

    /**
     * Test of console method, of class Log.
     */
    @Test
    public void testLog() {
        try {
            String message = "This is a Test Message.";
            File file = new File("log.txt");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(message);
            bw.close();
            fw.close();
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
    }    
}
