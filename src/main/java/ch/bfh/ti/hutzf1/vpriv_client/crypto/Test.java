/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.ti.hutzf1.vpriv_client.crypto;

import ch.bfh.ti.hutzf1.vpriv_client.log.Log;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import java.io.IOException;

/**
 *
 * @author fh
 */
public class Test {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        PedersenScheme ps = new PedersenScheme();
        int fd = 2167234;
        Element tag = ps.getElement(fd);
        int fd2 = 784362;
        Element roundKey = ps.getElement(fd2);
        Log log = new Log();
        log.console(tag.toString());
        Element tagCommit = ps.commit(tag, roundKey);
        log.console(tagCommit.toString());
        
        
    }
    
}
