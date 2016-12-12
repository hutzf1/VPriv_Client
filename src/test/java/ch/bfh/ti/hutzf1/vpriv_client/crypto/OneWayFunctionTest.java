/*
 * VPriv Client Server Simulator
 * Copyright 2017 Fabian Hutzli
 * Berner Fachhochschule
 *
 * All rights reserved.
 */
package ch.bfh.ti.hutzf1.vpriv_client.crypto;

import ch.bfh.ti.hutzf1.vpriv_client.log.Log;
import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the "Hash" functionality
 * @author Fabian Hutzli
 */

public class OneWayFunctionTest {

    /**
     * Tests the One Way Function -> getHash.
     */
    @Test
    public void testGetHash() {
        BigInteger message = new BigInteger("123456789");
        BigInteger key = new BigInteger("987654321");
        PedersenScheme ps = new PedersenScheme(new Log());
        OneWayFunction owf = new OneWayFunction(ps);
        String expResult = "28948022309329048855892746252171976963317496166410141009864396001978282508222";
        BigInteger result = owf.getHash(message, key);
        assertEquals(expResult, result.toString());
    }
}