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
import ch.bfh.ti.hutzf1.vpriv_client.log.Log;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
 
    private final ArrayList<BigInteger> TAGS;
    private final ArrayList<BigInteger> KEYS;
    private final ArrayList<BigInteger> DV;
    private final ArrayList<BigInteger> DK;
    private final ArrayList<BigInteger> DC;
    
    private final int i;
    private final int n;
    private final int s;
    
    private final PedersenScheme ps;
    private final OneWayFunction hash;
    private final Log log;
    private final DataExchange de;

    public Vehicle(PedersenScheme ps, OneWayFunction hash, Log log, int n, int s) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        
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
    
    public BigInteger getRandomTag() {
        Random rand = new Random();
        return TAGS.get(rand.nextInt(this.n));
    }
    
    public BigInteger getKey(int round) {
        return KEYS.get(round);
    }

    private void registration() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        // Start vehicle registration
        log.both(ID + " starts registration phase");
        
        // Generate Fresh Tags
        log.file(ID + " generates fresh tags");
        
        for (int x = 0; x < this.n; x++) {
            TAGS.add(ps.getTag().convertToBigInteger());
            log.file(ID + " tag: " + TAGS.get(x).toString());
        }
        
        // Generate Fresh Keys
        log.file(ID + " generates fresh keys");
        
        for (int x = 0; x < this.s; x++) {
            KEYS.add(ps.getKey().convertToBigInteger());
            log.file(ID + " key: " + KEYS.get(x).toString());
        }
  
        // Generate Opening Keys for Tags
        log.file(ID + " generates opening keys for tags");
        
        for (int x = 0; x < this.s; x++) {
            for (int y = 0; y < n; y++) {
                int index = x * y + y;
                DV.add(ps.getOpeningKey().convertToBigInteger());
                log.file(ID + " tag opening key: " + DV.get(index).toString());
            }
        }
        
        // Generate Opening Keys for Keys
        log.file(ID + " generate opening keys for keys");
        
        for (int x = 0; x < this.s; x++) {
            DK.add(ps.getOpeningKey().convertToBigInteger());
            log.file(ID + " key opening key: "+ DK.get(x).toString());
        }
        
        // Generate round package to send data to service provider
        JSONObject roundPackage = new JSONObject();
        roundPackage.put("type", "roundpackage");
        log.file(ID + " generates round package");
        
        roundPackage.put("id", ID);
        log.file(ID + " adds ID " + ID + " to round package");
        
        roundPackage.put("round", i);
        log.file(ID + " adds round " + i + " to round package");
        
        roundPackage.put("key", ps.commit(ps.getElement(KEYS.get(i)), ps.getElement(DK.get(i))).convertToBigInteger());
        log.file(ID + " adds key " + KEYS.get(i) + " to round package");
        
        for(int x = 0; x < this.n; x++) {
            // ENCRYPTION MISSING HERE!!!
            int index = x * i + i;
            System.out.println((TAGS.get(x)).toString());
            System.out.println(hash.getHash(TAGS.get(x), DK.get(i)).toString());
            roundPackage.put("v" + x, ps.commit(ps.getElement(hash.getHash(TAGS.get(x), DK.get(i))), ps.getElement(DV.get(index))).convertToBigInteger());
            log.file(ID + " adds commit of crypted " + TAGS.get(x).toString() + " to round package");
        }
        
        log.file(ID + " send round package to service provider");
        de.putRoundPackage(roundPackage);
    }
    
    public void drive() throws IOException {
        Location currentLocation = new Location();
        Date timestamp = new Date();
        BigInteger randomTag = this.getRandomTag();
        JSONObject drivingData = new JSONObject();
        
        drivingData.put("type", "drivingdata");
        drivingData.put("tag", randomTag);
        drivingData.put("longitude", currentLocation.LONGITUDE);
        drivingData.put("latitude", currentLocation.LATIDUDE);
        drivingData.put("timestamp", timestamp);
        
        de.putDrivingData(drivingData);
        
        log.both(ID + " is driving. Tag " + randomTag.toString() + " (" + currentLocation.LATIDUDE + ", " + currentLocation.LONGITUDE + ") - " + timestamp);
    }

    public void reconciliation() throws IOException {
        int c = 0;
        int bi;
        Element Di = ps.getOpeningKey();
        
        JSONObject W = de.getAllData();
        JSONObject costData = new JSONObject();
        
        /*log.file(ID + " is calculating cost...");
        for (int i = 0; i < W.length(); i++) {
            if(TAGS.contains(W.names().getInt(i)))
                c += W.names().getInt(i);
        }
        
        log.both(ID + " calculated " + c);
        costData.put("type", "costdata");
        costData.put("id", ID);
        costData.put("cost", c);

        de.putCostData(costData);
        
        // Send permutated data to service provider
        // Permute todo!
        
        // Opening Keys for Costs
        log.file(ID + " generates opening keys for costs");
        
        for (int x = 0; x <= i; x++) {
            for (int y = 0; y < W.length(); y++) {
                int index = x * y + y;
                DC.add(ps.getOpeningKey().convertToBigInteger());
                log.file(ID + " tag opening key: " + DC.get(index).toString());
            }
        }*/
        
        /*
        log.file(ID + " is permutating W and send the package to service provider");
        int m = 0;
        Ui.setId(ID);
        for (DrivingTuple dr : W) {
            Ui.addDrivingTuple(new DrivingTuple(hash.getHash(dr.tag, KEYS.get(i)), ps.commit(dr.cost, DC.get(m))));
            m++; 
        }
        sp.putPermutatedPackage(Ui);
        
        bi = sp.getCheckMethod();
        */
        bi = (int) de.getControlMethod().get("bi");
        /*
        
        log.both(ID + " bi is: " + Integer.toString(bi)); 
        
        // Vehicle sends to Service Provider
        if(bi == 0){
            log.both(ID + " service provider calculated " + sp.calculate0(ID, KEYS.get(i), DC));  
        }
        else if(bi == 1){
            log.both(ID + " service provider calculated " + sp.calculate1(ID, DV, Di));
        }*/
    }
}