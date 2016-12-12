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
     * Tests if getRandomElement gives random Elements from Message Space.
     */
    @Test
    public void testRandomElement() {
        PedersenScheme ps = new PedersenScheme(new Log());
        BigInteger value1 = ps.getRandomElement();
        BigInteger value2 = ps.getRandomElement();
        assertFalse(value1.equals(value2));
    }

    /**
     * Tests if converting a BigInteger to a Unicrypt Element works.
     */
    @Test
    public void testGetElement_BigInteger() {
        PedersenScheme ps = new PedersenScheme(new Log());
        BigInteger bigInt = BigInteger.TEN;
        Element element = ps.getElement(bigInt);
        assertEquals(bigInt, element.convertToBigInteger());
    }

    /**
     * Tests if converting a ByteArray to a Unicrypt Element works.
     */
    @Test
    public void testGetElement_ByteArray() {
        PedersenScheme ps = new PedersenScheme(new Log()); 
        ByteArray byteArr = ByteArray.getInstance("This is a Test.");
        Element element = ps.getElement(byteArr);
        assertEquals(byteArr, element.convertToByteArray());
    }

    /**
     * Tests if decommitment of commitment is the commitment.
     */
    @Test
    public void testCommitment() {
        PedersenScheme ps = new PedersenScheme(new Log());
        BigInteger message = BigInteger.TEN;
        BigInteger key = BigInteger.ONE;
        assertEquals(true, ps.decommit(message, key, ps.commit(message, key)));
    }
}