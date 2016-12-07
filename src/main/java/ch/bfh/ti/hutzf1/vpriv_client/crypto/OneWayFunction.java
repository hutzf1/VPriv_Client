package ch.bfh.ti.hutzf1.vpriv_client.crypto;

/*import ch.bfh.unicrypt.UniCryptException;
import ch.bfh.unicrypt.helper.array.classes.ByteArray;
import ch.bfh.unicrypt.helper.hash.HashAlgorithm;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;*/
import java.math.BigInteger;

/**
 *
 * @author Fabian Hutzli
 */

public class OneWayFunction {

    /**
     *
     * @param message
     * @param key
     * @param ps
     * @return
     */
    
    public BigInteger getHash(BigInteger message, BigInteger key, PedersenScheme ps) {
        /*HashAlgorithm ha = HashAlgorithm.getInstance();    
        ByteArray hash = ha.getHashValue(ps.getElement(message).convertToByteArray(), ps.getElement(key).convertToByteArray());
        Element element = ps.getElement(hash);
        return element.convertToBigInteger();*/
        return ps.getElement(message).invert().convertToBigInteger();
    }
}
