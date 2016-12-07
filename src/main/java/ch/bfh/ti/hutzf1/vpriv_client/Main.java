package ch.bfh.ti.hutzf1.vpriv_client;

import ch.bfh.ti.hutzf1.vpriv_client.crypto.OneWayFunction;
import ch.bfh.ti.hutzf1.vpriv_client.crypto.PedersenScheme;
import ch.bfh.ti.hutzf1.vpriv_client.device.Device;
import ch.bfh.ti.hutzf1.vpriv_client.log.Log;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Fabian Hutzli
 */

public class Main {

    /**
     *
     * @param args
     */
    
    public static void main(String[] args) {
        
        PedersenScheme ps = new PedersenScheme();
        Log log = new Log();
        OneWayFunction hash = new OneWayFunction();

        // Variables
        int numberOfDevices = 2;
        int n = 25; // number of new tags
        int s = 5; // number of new round keys // number of rounds
        int i = 1; // round (i element of [1; s])
        Random rand = new Random();
        
        // REGISTRATION PHASE
        log.both(" -- START REGISTRATION PHASE -- ");
        
        // Generate Vehicles
        ArrayList<Device> devices = new ArrayList<>();
        for (int x = 0; x < numberOfDevices; x++) {
            Device newDevice = new Device(ps, hash, log, n, s, i);
            devices.add(newDevice);
        }
        
        log.both(" -- END REGISTRATION PHASE -- ");
        
        // DRIVING PHASE
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            log.exception(ex);
        }
        log.both(" -- START DRIVING PHASE -- ");
        
        devices.forEach((device) -> {
            for(int y = 0; y < rand.nextInt(n); y++) {
                device.drive();
            }
        });
        
        log.both(" -- END DRIVING PHASE -- ");
        
        // RECONCILIATION PHASE
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            log.exception(ex);
        }
        log.both(" -- START RECONCILIATION PHASE -- ");
        
        devices.forEach((device) -> {
            //device.reconciliation();
        });
        
        log.both(" -- END RECONCILIATION PHASE -- ");
        log.close();
    }
}
