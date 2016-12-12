package ch.bfh.ti.hutzf1.vpriv_client.dataexchange;

import ch.bfh.ti.hutzf1.vpriv_client.log.Log;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fabian Hutzli
 */

public class DataExchangeTest {
    /**
     * Test of putRoundPackage method, of class DataExchange.
     */
    @Test
    public void testPutRoundPackage() {
        System.out.println("putRoundPackage");
        JSONObject jo = null;
        DataExchange instance = new DataExchange(new Log());
        instance.putRoundPackage(jo);
    }
    
    /**
     * Test of putDrivingData method, of class DataExchange.
     */
    @Test
    public void testPutDrivingData() {
        System.out.println("putDrivingData");
        JSONObject jo = null;
        DataExchange instance = new DataExchange(new Log());
        instance.putDrivingData(jo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * This Test checks if return (JSONObject) of getAllData method is empty.
     *  
     */
    @Test
    public void testGetAllData() {
        DataExchange de = new DataExchange(new Log());
        JSONObject jo = de.getAllData();
        assertTrue(jo.length() == 0);
    }
    
    /**
     * Test of putCostData method, of class DataExchange.
     */
    @Test
    public void testPutCostData() {
        System.out.println("putCostData");
        JSONObject jo = null;
        DataExchange de = new DataExchange(new Log());
        de.putCostData(jo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of putPermutedPackage method, of class DataExchange.
     */
    @Test
    public void testPutPermutedPackage() {
        System.out.println("putPermutedPackage");
        JSONObject jo = null;
        DataExchange instance = new DataExchange(new Log());
        instance.putPermutedPackage(jo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * This test checks if return of getControlMethod is 0 or 1
     */
    @Test
    public void testGetControlMethod() {
        DataExchange de = new DataExchange(new Log());
        JSONObject jo = de.getControlMethod();
        assertTrue(jo.getInt("bi") == 0 || jo.getInt("bi") == 1);
    }

    /**
     * Test of putControlData method, of class DataExchange.
     */
    @Test
    public void testPutControlData() {
        System.out.println("putControlData");
        JSONObject jo = null;
        int bi = 0;
        DataExchange instance = new DataExchange(new Log());
        instance.putControlData(jo, bi);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
