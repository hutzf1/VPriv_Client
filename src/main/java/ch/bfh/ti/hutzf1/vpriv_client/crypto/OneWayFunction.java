/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.bfh.ti.hutzf1.vpriv_client.crypto;

import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
//import java.math.BigInteger;

/**
 *
 * @author fh
 */

public class OneWayFunction {
        public Element getHash(Element message, Element key) {
            return message.invert();
            /*BigInteger bigMessage = message.convertToBigInteger();
            BigInteger bigKey = key.convertToBigInteger();
            bigMessage.add(bigKey);
            Element hash = null;
            return hash.selfApply(bigMessage.add(bigKey));*/
    }
}
