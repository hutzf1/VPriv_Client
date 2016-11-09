/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.bfh.ti.hutzf1.vpriv_client.crypto;

import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
//import java.math.BigInteger;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author fh
 */

public class OneWayFunction {
        public BigInteger getHash(BigInteger message, BigInteger key) throws NoSuchAlgorithmException, InvalidKeyException {
            
            Mac mac = Mac.getInstance("HMACSHA256");
            // Converts BigInteger to byte array
            byte[] keyBytes = key.toByteArray();
            byte[] dataBytes = message.toByteArray();

            // Generates secret Key
            SecretKey secret = new SecretKeySpec(keyBytes, "HMACSHA256");

            // HMAC initialization
            mac.init(secret);

            // Generates HMAC
            byte[] hmac = mac.doFinal(dataBytes);

            // Converts byte array to BigInteger
            BigInteger result = new BigInteger(hmac);

            result = result.abs();
            
            BigInteger n = result.divide(BigInteger.TEN.pow(new BigDecimal(result).precision() - 6));

            return n;

            /*BigInteger bigMessage = message.convertToBigInteger();
            BigInteger bigKey = key.convertToBigInteger();
            bigMessage.add(bigKey);
            Element hash = null;
            return hash.selfApply(bigMessage.add(bigKey));*/
    }
}
