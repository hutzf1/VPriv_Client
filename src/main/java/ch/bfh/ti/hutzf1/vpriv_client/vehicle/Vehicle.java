/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.ti.hutzf1.vpriv_client.vehicle;

import ch.bfh.ti.hutzf1.vpriv_client.crypto.OneWayFunction;
import ch.bfh.ti.hutzf1.vpriv_client.crypto.PedersenScheme;
import ch.bfh.ti.hutzf1.vpriv_client.dataexchange.DataExchange;
import ch.bfh.ti.hutzf1.vpriv_client.location.Location;
//import ch.bfh.ti.hutzf1.vpriv_client.serviceprovider.ServiceProvider;
//import ch.bfh.ti.hutzf1.vpriv_client.transport.DrivingTuple;
//import ch.bfh.ti.hutzf1.vpriv_client.transport.PermutatedPackage;
//import ch.bfh.ti.hutzf1.vpriv_client.transport.RoundPackage;
import ch.bfh.ti.hutzf1.vpriv_client.log.Log;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import org.json.JSONObject;

/**
 *
 * @author fh
 */

public final class Vehicle {
    
    private final String ID;
    private final ArrayList<Element> TAGS;
    private final ArrayList<Element> KEYS;
    private final ArrayList<Element> DV;
    private final ArrayList<Element> DK;
    private final ArrayList<Element> DC;
    
    private final int i;
    private final int n;
    private final int s;
    
    //private final ServiceProvider sp;
    private final PedersenScheme ps;
    private final OneWayFunction hash;
    private final Log log;
    private final DataExchange de;

    //public Vehicle(ServiceProvider sp, PedersenScheme ps, OneWayFunction hash, Log log, int n, int s) throws IOException {
    public Vehicle(PedersenScheme ps, OneWayFunction hash, Log log, int n, int s) throws IOException {
        
        //this.sp = sp;
        this.ps = ps;
        this.hash = hash;
        this.log = log;
        this.de = new DataExchange();
        
        // Set vehicles license plate
        ID = generateID();
        
        // Set parameter
        i = 0;
        this.n = n;
        this.s = s;
        
        // Set tags, keys, opening keys
        TAGS = new ArrayList<>();
        KEYS = new ArrayList<>();
        DV = new ArrayList<>();
        DK = new ArrayList<>();
        DC = new ArrayList<>();
        
        //final ArrayList<String> HASHES = new ArrayList<>();
        
        log.file(ID);
        this.registration();
    }
    
    private String generateID() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVW0123456789";
        int length = 8;
        
        Random rand = new Random();
        StringBuilder buf = new StringBuilder();
        for (int x = 0; x < length; x++) {
            buf.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return buf.toString();
    }
    
    public Element getRandomTag() {
        Random rand = new Random();
        return TAGS.get(rand.nextInt(this.n));
    }
    
    public Element getKey(int round) {
        return KEYS.get(round);
    }

    private void registration() throws IOException {
        // Start vehicle registration
        log.both(ID + " starts registration phase");
        
        // Generate Fresh Tags
        log.file(ID + " generates fresh tags");
        
        for (int x = 0; x < this.n; x++) {
            TAGS.add(ps.getTag());
            log.file(ID + " tag: " + TAGS.get(x).getValue());
        }
        
        // Fresh Keys
        log.file(ID + " generates fresh keys");
        
        for (int x = 0; x < this.s; x++) {
            KEYS.add(ps.getKey());
            log.file(ID + " key: " + KEYS.get(x).getValue());
        }
  
        // Opening Keys for Tags
        log.file(ID + " generates opening keys for tags");
        
        for (int x = 0; x < this.s; x++) {
            for (int y = 0; y < n; y++) {
                int index = x * y + y;
                DV.add(ps.getOpeningKey());
                log.file(ID + " tag opening key: " + DV.get(index).getValue());
            }
        }
        
        // Opening Keys for Keys
        log.file(ID + " generate opening keys for keys");
        
        for (int x = 0; x < this.s; x++) {
            DK.add(ps.getOpeningKey());
            log.file(ID + " key opening key: "+ DK.get(x).getValue());
        }
        
        // Generate round package to send data to service provider
        JSONObject roundPackage = new JSONObject();
        log.file(ID + " generates round package");
        
        roundPackage.put("id", ID);
        log.file(ID + " adds ID " + ID + " to round package");
        
        roundPackage.put("round", i);
        log.file(ID + " adds round " + i + " to round package");
        
        roundPackage.put("key", ps.commit(KEYS.get(i), DK.get(i)));
        log.file(ID + " adds key " + KEYS.get(i) + " to round package");
        
        for(int x = 0; x < this.n; x++) {
            // ENCRYPTION MISSING HERE!!!
            //RI.addCommit(ps.commit(hash.Hash(TAGS[x], KEYS[i]), DV[i][x]));
            int index = x * i + i;
            //log.console("----- TAG " + TAGS.get(x).convertToString());
            //log.console("----- ENC " + hash.getHash(TAGS.get(x), DK.get(i)).convertToString());
            roundPackage.put("v" + x, ps.commit(hash.getHash(TAGS.get(x), DK.get(i)), DV.get(index)));
            log.file(ID + " adds commit of crypted " + TAGS.get(x).getValue() + " to round package");
        }
        
        log.file(ID + " send round package to service provider");
        de.putRoundPackage(roundPackage);
    }
    
    public void drive() throws IOException {
        Location currentLocation = new Location();
        Date timestamp = new Date();
        Element randomTag = this.getRandomTag();
        JSONObject drivingDate = new JSONObject();
        drivingDate.put("tag", randomTag);
        drivingDate.put("location", currentLocation);
        drivingDate.put("timestamp", timestamp);
        
        //int toll = sp.putDrivingData(randomTag, currentLocation, timestamp);
        
        log.both(ID + " is driving. Tag " + randomTag.getValue() + " (" + currentLocation.LATIDUDE + ", " + currentLocation.LONGITUDE + ") - " + timestamp);
        //log.file(ID + " value of a toll station is " + toll);
    }

    /*public void reconciliation() throws IOException {
        int c = 0;
        int bi;
        Element Di = ps.getOpeningKey();
        
        ArrayList<DrivingTuple> W = sp.getAllTags();
        PermutatedPackage Ui = new PermutatedPackage();
        
        log.file(ID + " is calculating cost...");
        for (DrivingTuple dr : W) {
            if(TAGS.contains(dr.tag)) {
                c += dr.cost.convertToBigInteger().intValue();
            }   
        }
        log.both(ID + " calculated " + c);
        sp.putCostData(ID, c);
        
        // Send permutated data to service provider
        // Permute todo!
        
        // Opening Keys for Costs
        log.file(ID + " generates opening keys for costs");
        
        for (int x = 0; x <= i; x++) {
            for (int y = 0; y < W.size(); y++) {
                int index = x * y + y;
                DC.add(ps.getOpeningKey());
                log.file(ID + " tag opening key: " + DC.get(index).getValue());
            }
        }
        
        log.file(ID + " is permutating W and send the package to service provider");
        int m = 0;
        Ui.setId(ID);
        for (DrivingTuple dr : W) {
            Ui.addDrivingTuple(new DrivingTuple(hash.getHash(dr.tag, KEYS.get(i)), ps.commit(dr.cost, DC.get(m))));
            m++; 
        }
        sp.putPermutatedPackage(Ui);
        
        bi = sp.getCheckMethod();
        
        log.both(ID + " bi is: " + Integer.toString(bi)); 
        
        // Vehicle sends to Service Provider
        if(bi == 0){
            log.both(ID + " service provider calculated " + sp.calculate0(ID, KEYS.get(i), DC));  
        }
        else if(bi == 1){
            log.both(ID + " service provider calculated " + sp.calculate1(ID, DV, Di));
        }
    }*/
}