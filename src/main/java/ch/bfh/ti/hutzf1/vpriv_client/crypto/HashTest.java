/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.ti.hutzf1.vpriv_client.crypto;
import ch.bfh.ti.hutzf1.vpriv_client.log.Log;
import ch.bfh.unicrypt.UniCryptException;
import ch.bfh.unicrypt.helper.array.classes.ByteArray;
import ch.bfh.unicrypt.helper.hash.HashAlgorithm;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import java.io.IOException;

/**
 *
 * @author fh
 */
public class HashTest {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, UniCryptException {
        // TODO code application logic here
        PedersenScheme ps = new PedersenScheme();
        Element tag = ps.getTag();
        Element key = ps.getKey();
        ByteArray baTag = tag.convertToByteArray();
        ByteArray baKey = key.convertToByteArray();
        HashAlgorithm ha = HashAlgorithm.getInstance();
        Log log = new Log();
        
        log.console(tag.toString());
        log.console(key.toString());
        log.console(baTag.toString());
        log.console(baKey.toString());
        log.console(ha.getAlgorithmName());
        
        ByteArray hash = ha.getHashValue(baTag, baKey);
        log.console(hash.toString());
        
        //ByteArray hash2 = hash.extract(30, 2);
        //log.console(hash2.toString());
        
        Element p = ps.getElement(hash);
        //Element p = ps.getElement(hash2);

    }
    
}
