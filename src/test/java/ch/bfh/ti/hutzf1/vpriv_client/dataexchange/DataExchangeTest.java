package ch.bfh.ti.hutzf1.vpriv_client.dataexchange;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fabian Hutzli
 */

public class DataExchangeTest {
    
    public DataExchangeTest() {
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
     * Test of getAllData method, of class DataExchange.
     */
    @Test
    public void testGetAllData() throws Exception {
        System.out.println("getAllData");
        DataExchange de = new DataExchange();
        JSONObject expResult = null;
        JSONObject result = de.getAllData();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getControlMethod method, of class DataExchange.
     */
    @Test
    public void testGetControlMethod() throws Exception {
        System.out.println("getControlMethod");
        DataExchange instance = new DataExchange();
        JSONObject expResult = null;
        JSONObject result = instance.getControlMethod();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putRoundPackage method, of class DataExchange.
     */
    @Test
    public void testPutRoundPackage() throws Exception {
        System.out.println("putRoundPackage");
        JSONObject jo = null;
        DataExchange instance = new DataExchange();
        instance.putRoundPackage(jo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putDrivingData method, of class DataExchange.
     */
    @Test
    public void testPutDrivingData() throws Exception {
        System.out.println("putDrivingData");
        JSONObject jo = null;
        DataExchange instance = new DataExchange();
        instance.putDrivingData(jo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putCostData method, of class DataExchange.
     */
    @Test
    public void testPutCostData() throws Exception {
        System.out.println("putCostData");
        JSONObject jo = null;
        DataExchange instance = new DataExchange();
        instance.putCostData(jo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putPermutedPackage method, of class DataExchange.
     */
    @Test
    public void testPutPermutedPackage() throws Exception {
        System.out.println("putPermutedPackage");
        JSONObject jo = null;
        DataExchange instance = new DataExchange();
        instance.putPermutedPackage(jo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putControlData method, of class DataExchange.
     */
    @Test
    public void testPutControlData() throws Exception {
        System.out.println("putControlData");
        JSONObject jo = null;
        int bi = 0;
        DataExchange instance = new DataExchange();
        instance.putControlData(jo, bi);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
