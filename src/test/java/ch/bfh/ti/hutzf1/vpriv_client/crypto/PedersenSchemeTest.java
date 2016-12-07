package ch.bfh.ti.hutzf1.vpriv_client.crypto;

import ch.bfh.unicrypt.UniCryptException;
import ch.bfh.unicrypt.helper.array.classes.ByteArray;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
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

public class PedersenSchemeTest {
    
    public PedersenSchemeTest() {
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
     * Test of getRandomElement method, of class PedersenScheme.
     */
    @Test
    public void testRandomElement() {
        System.out.println("Testing if getRandomElement gives random Elements in Message Space");
        PedersenScheme ps = new PedersenScheme();
        BigInteger value1 = ps.getRandomElement();
        BigInteger value2 = ps.getRandomElement();
        assertEquals(false, value1.equals(value2));
        fail("Both value are the same, no randomization!");
    }

    /**
     * Test of getElement method, of class PedersenScheme.
     */
    @Test
    public void testGetElement_BigInteger() {
        System.out.println("Testing if getElement Method returns the correct Element of a BigInteger.");
        PedersenScheme ps = new PedersenScheme();
        BigInteger bigInt = BigInteger.ONE;
        Element element = ps.getElement(bigInt);
        assertEquals(bigInt, element.convertToBigInteger());
        fail("Convert to Element from BigInteger isn't working as expected.");
    }

    /**
     * Test of getElement method, of class PedersenScheme.
     * @throws ch.bfh.unicrypt.UniCryptException
     */
    @Test
    public void testGetElement_ByteArray() throws UniCryptException {
        System.out.println("Testing if getElement Method returns the correct Element of a ByteArray.");
        PedersenScheme ps = new PedersenScheme(); 
        ByteArray byteArr = ByteArray.getInstance("Test");
        Element element = ps.getElement(byteArr);
        assertEquals(byteArr, element.convertToByteArray());
        fail("Convert to Element from ByteArray isn't working as expected.");
    }

    /**
     * Test of commit and decommit method, of class PedersenScheme.
     */
    @Test
    public void testCommitment() {
        System.out.println("Testing if decommitment of commitment is commitment.");
        PedersenScheme ps = new PedersenScheme();
        BigInteger message = BigInteger.ONE;
        BigInteger key = BigInteger.ZERO;
        Boolean result;
        result = ps.decommit(message, key, ps.commit(message, key));
        assertEquals(true, result);
        fail("Commit or decommit failed.");
    }
}