package ch.bfh.ti.hutzf1.vpriv_client.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabian Hutzli
 */

public class Log {
    
    File file = null;
    FileWriter fw = null;
    BufferedWriter bw = null;
    
    /**
     *
     */
    
    public Log() {
        file = new File("log.txt");
        try {
            fw = new FileWriter(file.getAbsoluteFile());
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        bw = new BufferedWriter(fw);
    }
    
    /**
     *
     * @param message
     */
    
    public void console(String message) {
        System.out.println(message);
    }
    
    /**
     *
     * @param message
     */
    
    public void file(String message) {
        try {
            bw.append(message);
            bw.append("\r\n");
        } catch (IOException e) {
            System.out.println(e);
        }

    }
    
    /**
     *
     * @param message
     */
    
    public void both(String message) {
        this.file(message);
        System.out.println(message);
    }
    
    /**
     *
     * @param ex
     */
    
    public void exception(Throwable ex) {
        this.both(ex.getMessage());
    }
    
    /**
     *
     */
    
    public void close() {
        try {
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
