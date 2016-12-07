package ch.bfh.ti.hutzf1.vpriv_client.crypto;

import java.math.BigInteger;
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

public class OneWayFunctionTest {
    
    public OneWayFunctionTest() {
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
     * Test of getHash method, of class OneWayFunction.
     */
    @Test
    public void testGetHash() throws Exception {
        System.out.println("Testing One Way Function to generate Hash");
        BigInteger message = BigInteger.ONE;
        BigInteger key = BigInteger.TEN;
        PedersenScheme ps = new PedersenScheme();
        OneWayFunction owf = new OneWayFunction();
        BigInteger expResult = null;
        BigInteger result = owf.getHash(message, key, ps);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
