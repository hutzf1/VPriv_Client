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
        JSONObject jo = null;
        jo.put("id", "This is a Test.");
        DataExchange de = new DataExchange(new Log());
        de.putRoundPackage(jo);
    }
    
    /**
     * Test of putDrivingData method, of class DataExchange.
     */
    @Test
    public void testPutDrivingData() {
        JSONObject jo = null;
        jo.put("id", "This is a Test.");
        DataExchange de = new DataExchange(new Log());
        de.putDrivingData(jo);
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
        JSONObject jo = null;
        jo.put("id", "This is a Test.");
        DataExchange de = new DataExchange(new Log());
        de.putCostData(jo);
    }
    
    /**
     * Test of putPermutedPackage method, of class DataExchange.
     */
    @Test
    public void testPutPermutedPackage() {
        JSONObject jo = null;
        jo.put("id", "This is a Test.");
        DataExchange de = new DataExchange(new Log());
        de.putPermutedPackage(jo);
    }
    
    /**
     * Tests if return of getControlMethod is 0 or 1
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
        JSONObject jo = null;
        jo.put("id", "This is a Test.");
        DataExchange de = new DataExchange(new Log());
        de.putControlData(jo, 0);
        de.putControlData(jo, 1);
    }
    
}
