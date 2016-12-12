/*
 * VPriv Client Server Simulator
 * Copyright 2017 Fabian Hutzli
 * Berner Fachhochschule
 *
 * All rights reserved.
 */
package ch.bfh.ti.hutzf1.vpriv_client.crypto;

/*import ch.bfh.unicrypt.UniCryptException;
import ch.bfh.unicrypt.helper.array.classes.ByteArray;
import ch.bfh.unicrypt.helper.hash.HashAlgorithm;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;*/
import java.math.BigInteger;

/**
 * Generates the "Hash"
 * @author Fabian Hutzli
 */

public class OneWayFunction {
    
    private final PedersenScheme PS;
    
    public OneWayFunction(PedersenScheme ps) {
        this.PS = ps;
    }

    /**
     *
     * @param message
     * @param key
     * @return
     */
    
    public BigInteger getHash(BigInteger message, BigInteger key) {
        /*HashAlgorithm ha = HashAlgorithm.getInstance();    
        ByteArray hash = ha.getHashValue(ps.getElement(message).convertToByteArray(), ps.getElement(key).convertToByteArray());
        Element element = ps.getElement(hash);
        return element.convertToBigInteger();*/
        return message.add(new BigInteger("50" + key.toString()));
        //return this.PS.getElement(message).invert().convertToBigInteger();
    }
}
