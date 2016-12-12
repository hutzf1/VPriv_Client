/*
 * VPriv Client Server Simulator
 * Copyright 2017 Fabian Hutzli
 * Berner Fachhochschule
 *
 * All rights reserved.
 */
package ch.bfh.ti.hutzf1.vpriv_client.crypto;

import ch.bfh.ti.hutzf1.vpriv_client.log.Log;
import ch.bfh.unicrypt.helper.array.classes.ByteArray;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fabian Hutzli
 */

public class PedersenSchemeTest {

    /**
     * Test of getRandomElement method, of class PedersenScheme.
     */
    @Test
    public void testRandomElement() {
        System.out.println("Testing if getRandomElement gives random Elements in Message Space");
        PedersenScheme ps = new PedersenScheme(new Log());
        BigInteger value1 = ps.getRandomElement();
        BigInteger value2 = ps.getRandomElement();
        assertFalse(value1.equals(value2));
    }

    /**
     * Test of getElement method, of class PedersenScheme.
     */
    @Test
    public void testGetElement_BigInteger() {
        System.out.println("Testing if getElement Method returns the correct Element of a BigInteger.");
        PedersenScheme ps = new PedersenScheme(new Log());
        BigInteger bigInt = BigInteger.ONE;
        Element element = ps.getElement(bigInt);
        assertEquals(bigInt, element.convertToBigInteger());
    }

    /**
     * Test of getElement method, of class PedersenScheme.
     */
    @Test
    public void testGetElement_ByteArray() {
        System.out.println("Testing if getElement Method returns the correct Element of a ByteArray.");
        PedersenScheme ps = new PedersenScheme(new Log()); 
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
        PedersenScheme ps = new PedersenScheme(new Log());
        BigInteger message = BigInteger.ONE;
        BigInteger key = BigInteger.ZERO;
        assertEquals(true, ps.decommit(message, key, ps.commit(message, key)));
    }
}