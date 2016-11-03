/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.ti.hutzf1.vpriv_client.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author fh
 */

public class Log {
    
    File file = null;
    FileWriter fw = null;
    BufferedWriter bw = null;
    
    public Log() throws FileNotFoundException, IOException {
        file = new File("log.txt");
        fw = new FileWriter(file.getAbsoluteFile());
        bw = new BufferedWriter(fw);
    }
    
    public void console(String message) {
        System.out.println(message);
    }
    
    public void file(String message) throws IOException {
        bw.append(message);
        bw.append("\r\n");
    }
    
    public void both(String message) throws IOException {
        System.out.println(message);
        bw.append(message);
        bw.append("\r\n");
    }
    
    public void close() throws IOException {
        bw.close();
        fw.close();
    }
    
}
