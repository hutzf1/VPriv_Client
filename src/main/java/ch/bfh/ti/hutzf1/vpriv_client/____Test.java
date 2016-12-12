/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.ti.hutzf1.vpriv_client;

import ch.bfh.ti.hutzf1.vpriv_client.crypto.OneWayFunction;
import ch.bfh.ti.hutzf1.vpriv_client.crypto.PedersenScheme;
import ch.bfh.ti.hutzf1.vpriv_client.log.Log;
import ch.bfh.unicrypt.UniCryptException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author fh
 */
public class ____Test {

    /**
     * @param args the command line arguments
     * @throws ch.bfh.unicrypt.UniCryptException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.InvalidKeyException
     */
    public static void main(String[] args) throws UniCryptException, NoSuchAlgorithmException, InvalidKeyException {
        BigInteger message = BigInteger.ONE;
        BigInteger key = BigInteger.TEN;
        PedersenScheme ps = new PedersenScheme(new Log());
        OneWayFunction owf = new OneWayFunction(ps);
        BigInteger expResult = null;
        BigInteger result = owf.getHash(message, key);
        System.out.println(result.toString());
    }
}
