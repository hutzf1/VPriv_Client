/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.ti.hutzf1.vpriv_client.location;

import java.util.Random;

/**
 *
 * @author fh
 */

public class Location {
    public final int LATIDUDE = randomLat();
    public final int LONGITUDE = randomLong();
    
    private int randomLat() {
        Random rand = new Random();
        return rand.nextInt(180 + 1);
    }
    
    private int randomLong() {
        Random rand = new Random();
        return rand.nextInt(90 + 1);
    }
}
