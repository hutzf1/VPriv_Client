package ch.bfh.ti.hutzf1.vpriv_client.crypto;

import ch.bfh.unicrypt.UniCryptException;
import ch.bfh.unicrypt.crypto.schemes.commitment.classes.PedersenCommitmentScheme;
import ch.bfh.unicrypt.helper.array.classes.ByteArray;
import ch.bfh.unicrypt.math.algebra.general.classes.BooleanElement;
import ch.bfh.unicrypt.math.algebra.general.interfaces.CyclicGroup;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.multiplicative.classes.GStarModSafePrime;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabian Hutzli
 */

public class PedersenScheme_orig {
    private final CyclicGroup CYCLICGROUP = GStarModSafePrime.getFirstInstance(256);
    private final PedersenCommitmentScheme COMMITMENTSCHEME = PedersenCommitmentScheme.getInstance(CYCLICGROUP);
    
    /*public Element getRandomElement() {
        Element message = COMMITMENTSCHEME.getRandomizationSpace().getRandomElement();
        return message;
    }*/

    /**
     *
     * @return
     */
    
    public BigInteger getRandomElement() {
        Element message = COMMITMENTSCHEME.getRandomizationSpace().getRandomElement();
        return message.convertToBigInteger();
    }
    
    /**
     *
     * @param value
     * @return
     */
    
    public Element getElement(BigInteger value) {
        return COMMITMENTSCHEME.getMessageSpace().getElement(value);
    }
    
    /**
     *
     * @param value
     * @return
     */
    
    public Element getElement(ByteArray value) {
        Element bArray = null;
        try {        
            bArray = COMMITMENTSCHEME.getMessageSpace().getElementFrom(value);
        } catch (UniCryptException ex) {
            Logger.getLogger(PedersenScheme_orig.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bArray;
    }

    /**
     *
     * @param message
     * @param key
     * @return
     */
    
    public BigInteger commit(BigInteger message, BigInteger key) {
        Element commitment = COMMITMENTSCHEME.commit(this.getElement(message), this.getElement(key));
        return commitment.convertToBigInteger();
    }
    
    /*public Element commit(Element message, Element key) {
        Element commitment = COMMITMENTSCHEME.commit(message, key);
        return commitment;
    }*/

    /**
     *
     * @param message
     * @param key
     * @param commitment
     * @return
     */
    
    public Boolean decommit(BigInteger message, BigInteger key, BigInteger commitment) {
        BooleanElement result = COMMITMENTSCHEME.decommit(this.getElement(message), this.getElement(key), this.getElement(commitment));
        return result.getValue();
    } 
    
    /*public Boolean decommit(Element message, Element key, Element commitment) {
        BooleanElement result = COMMITMENTSCHEME.decommit(message, key, commitment);
        return result.getValue();
    }*/
}