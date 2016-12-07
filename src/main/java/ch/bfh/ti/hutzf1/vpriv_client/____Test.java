/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.ti.hutzf1.vpriv_client;

import ch.bfh.ti.hutzf1.vpriv_client.crypto.OneWayFunction;
import ch.bfh.ti.hutzf1.vpriv_client.crypto.PedersenScheme;
import ch.bfh.unicrypt.UniCryptException;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
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
        System.out.println("Testing the One Way Function.");
        PedersenScheme ps = new PedersenScheme();
        BigInteger message = ps.getRandomElement();
        BigInteger key = ps.getRandomElement();
        OneWayFunction owf = new OneWayFunction();
        String expResult = "16027744783368409802036020174174900911857229567639966277710066841052296855447";
        BigInteger result = owf.getHash(message, key, ps);
        BigInteger v = ps.commit(owf.getHash(ps.getRandomElement(), ps.getRandomElement(), ps), ps.getRandomElement());
        System.out.println(expResult.equals(result.toString()));
    }
}
