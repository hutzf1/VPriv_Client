/*
 * VPriv Client Server Simulator
 * Copyright 2017 Fabian Hutzli
 * Berner Fachhochschule
 *
 * All rights reserved.
 */
package ch.bfh.ti.hutzf1.vpriv_client.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles the Logs to Console, File or Exceptions
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
            System.out.println(ex);
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
        } catch (IOException ex) {
            System.out.println(ex);
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
