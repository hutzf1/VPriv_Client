/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.ti.hutzf1.vpriv_client.log;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fh
 */
public class LogTest {
    
    public LogTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of console method, of class Log.
     */
    @Test
    public void testConsole() throws IOException {
        System.out.println("console");
        String message = "";
        Log instance = new Log();
        instance.console(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of file method, of class Log.
     */
    @Test
    public void testFile() throws Exception {
        System.out.println("file");
        String message = "";
        Log instance = new Log();
        instance.file(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of both method, of class Log.
     */
    @Test
    public void testBoth() throws Exception {
        System.out.println("both");
        String message = "";
        Log instance = new Log();
        instance.both(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class Log.
     */
    @Test
    public void testClose() throws Exception {
        System.out.println("close");
        Log instance = new Log();
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
