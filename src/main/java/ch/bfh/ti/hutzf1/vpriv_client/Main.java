/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.ti.hutzf1.vpriv_client;

import ch.bfh.ti.hutzf1.vpriv_client.crypto.OneWayFunction;
import ch.bfh.ti.hutzf1.vpriv_client.crypto.PedersenScheme;
import ch.bfh.ti.hutzf1.vpriv_client.vehicle.Vehicle;
import ch.bfh.ti.hutzf1.vpriv_client.log.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author fh
 */

public class Main {

    public static void main(String[] args) throws IOException {
        
        PedersenScheme ps = new PedersenScheme();
        Log log = new Log();
        OneWayFunction hash = new OneWayFunction();
        //ServiceProvider sp = new ServiceProvider(ps, log, hash);

        // Variables
        int numberOfVehicles = 5;
        int n = 25; // number of new tags
        int s = 1; // number of new keys
        int maxToll = n;
        int round = 1;
        int i = round-1; // round (i element of [1; s])
        Random rand = new Random();
        
        ////////////////////////
        // REGISTRATION PHASE //
        ////////////////////////
        
        log.both(" -- START REGISTRATION PHASE -- ");
        
        // Generate Vehicles
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        for (int x = 0; i < numberOfVehicles; i++) {
            //Vehicle newVehicle = new Vehicle(sp, ps, hash, log, n, s);
            Vehicle newVehicle = new Vehicle(ps, hash, log, n, s);
            vehicles.add(newVehicle);
        }
        
        log.both(" -- END REGISTRATION PHASE -- ");
        
        ///////////////////
        // DRIVING PHASE //
        ///////////////////
        
        log.both(" -- START DRIVING PHASE -- ");
        
        for (Vehicle vehicle : vehicles) {
            for(int y = 0; y < rand.nextInt(maxToll) + 1; y++) {
                vehicle.drive();
            }
        }
        
        log.both(" -- END DRIVING PHASE -- ");

        //////////////////////////
        // RECONCILIATION PHASE //
        //////////////////////////
        
        log.both(" -- START RECONCILIATION PHASE -- ");
        
        for (Vehicle vehicle : vehicles) {
            vehicle.reconciliation();         
        }
        
        log.both(" -- END RECONCILIATION PHASE -- ");
        log.close();
    }
}
