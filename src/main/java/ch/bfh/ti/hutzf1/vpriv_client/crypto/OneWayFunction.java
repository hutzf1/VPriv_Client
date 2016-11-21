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
/*
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

        BigInteger n = result.divide(BigInteger.TEN.pow(new BigDecimal(result).precision() - 6));*/

        //return n;
        return message;

        /*BigInteger bigMessage = message.convertToBigInteger();
        BigInteger bigKey = key.convertToBigInteger();
        bigMessage.add(bigKey);
        Element hash = null;
        return hash.selfApply(bigMessage.add(bigKey));*/
    }
    
    public BigInteger  doHash()  {
        /*
        Entschuldigen Sie bitte die stark verspätete Antwort. 
        Ich wollte aber zuerst den Ansatz gut verstehen, hatte aber fast keine Zeit, das Paper zu studieren.
        Das Hauptproblem ist die Frage, wie die "parametrisierte One-Way Funktion" f:XxK->Y bzw. f_k:X->Y genau aussieht, 
        inklusive die Frage, wie die Mengen K, X, Y aussehen.
        Im VPriv-Originalpaper von Popa und Blumberg steht es etwas genauer. 
        Demnach muss f_k eine "Pseudorandom Function" sein. Deshalb rate ich Ihnen, dass Sie dazu HMAC einsetzen, weil
        http://cseweb.ucsd.edu/~mihir/papers/hmac-new.html
        bewiesen haben, dass HMAC einer Pseudorandom-Function entspricht, 
        wenn die darunterliegende Hash-Funktion pseudorandom ist (was man z.B. bei SHA256 annehmen kann).
        Also, wenn Sie HMAC_SHA256 nehmen, dann bedeutet das, dass X die Menge der beliebig langen ByteArrays ist, 
        Y die Menge der ByteArrays der Länge 256 Bits (32 Bytes), und K die Menge der ByteArrays der Länge 64 Bits (8 Bytes).
        In UniCrypt wird HMAC unterstützt, allerdings im Moment nur in der Klasse HashAlgorithm mittels der Methode ByteArray getHashValue(ByteArray message, ByteArray key).
        Wenn Sie das so machen, dann müssen Sie anschlissend das erhaltene ByteArray in den Messagespace des PedersenCommitmentSchemes hineinmappen. 
        Dazu am einfachsten den MessageSpace holen mit GetMessageSpace() und dann das Element mit getElementFrom(ByteArray value) erzeugen. 
        Da der MessageSpace des PedersenCommitmentSchemes viel grösser ist als der Output-Space von HMAC, 
        wird es bei der Konvertierung nie eine Exception geben.
        
        */
        return null;
    }
}
