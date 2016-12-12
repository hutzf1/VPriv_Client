/*
 * VPriv Client Server Simulator
 * Copyright 2017 Fabian Hutzli
 * Berner Fachhochschule
 *
 * All rights reserved.
 */
package ch.bfh.ti.hutzf1.vpriv_client.location;

import java.util.Random;

/**
 * Location Tuple with Longitude and Latitude.
 * @author Fabian Hutzli
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
