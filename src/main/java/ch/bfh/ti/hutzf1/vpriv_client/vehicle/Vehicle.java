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
import java.text.SimpleDateFormat;
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
    
    private BigInteger getRandomTag() {
        Random rand = new Random();
        return TAGS.get(rand.nextInt(this.n));
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
        SimpleDateFormat ft = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        BigInteger randomTag = this.getRandomTag();
        JSONObject drivingData = new JSONObject();
        
        drivingData.put("type", "drivingdata");
        drivingData.put("tag", randomTag);
        drivingData.put("longitude", currentLocation.LONGITUDE);
        drivingData.put("latitude", currentLocation.LATIDUDE);
        drivingData.put("timestamp", ft.format(timestamp));
        
        de.putDrivingData(drivingData);
        
        log.both(ID + " is driving. Tag " + randomTag.toString() + " (" + currentLocation.LATIDUDE + ", " + currentLocation.LONGITUDE + ") - " + timestamp);
    }

    public void reconciliation() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        int cost = 0;
        int bi;
        BigInteger Di = ps.getOpeningKey().convertToBigInteger();
        
        JSONObject W = de.getAllData();
        JSONObject costData = new JSONObject();
        JSONObject permutedPackage = new JSONObject();
        JSONObject reconPackage = new JSONObject();
        
        log.console(W.toString());
        
        log.both(ID + " is calculating cost...");
        
        for(int x = 0; x < n; x++) {
            for(int y = 1; y < W.length()/2; y++){
                log.both("TAG " + TAGS.get(x).toString());
                log.both("W " + W.getBigInteger("w" + y).toString());
                if(TAGS.get(x).equals(W.getBigInteger("w" + y))) {
                    log.both("COST " + Integer.toString(W.getInt("c" + y)));
                    cost += W.getInt("c" + y);
                }
            }
        }
        
        log.both(ID + " calculated " + cost);
        
        costData.put("type", "costdata");
        costData.put("id", ID);
        costData.put("cost", cost);

        de.putCostData(costData);
        
        // Send permutated data to service provider
        // Opening Keys for Costs
        log.both(ID + " generates opening keys for costs");
        
        for (int x = 0; x <= i; x++) {
            for (int y = 0; y < W.length()/2; y++) {
                int index = x * y + y;
                DC.add(ps.getOpeningKey().convertToBigInteger());
                log.both(ID + " tag opening key: " + DC.get(index).toString());
            }
        }
        
        log.both(ID + " is permutating W and send the package to service provider");
        permutedPackage.put("id", ID);
        permutedPackage.put("U", i);
        BigInteger w;
        BigInteger c;
        
        for (int x = 1; x <= W.length()/2; x++) {
            w = W.getBigInteger("w" + x);
            c = W.getBigInteger("c" + x);
            permutedPackage.put("w" + x, hash.getHash(w, KEYS.get(i)));
            permutedPackage.put("c" + x, ps.commit(ps.getElement(c), ps.getElement(DC.get(x-1))).convertToBigInteger());
        }
        
        de.putPermutedPackage(permutedPackage);
        
        bi = (int) de.getControlMethod().get("bi");

        log.both(ID + " bi is: " + Integer.toString(bi)); 
        
        // Vehicle sends to Service Provider
        reconPackage.put("id", ID);
        if(bi == 0){
            reconPackage.put("dki", KEYS.get(i));
            for (int x = 1; x <= W.length()/2; x++) {
                reconPackage.put("dci" + x, DC.get(x-1));
            }
        }
        else if(bi == 1){
            for (int x = 1; x <= DV.size(); x++) {
                reconPackage.put("dvi" + x, DV.get(x-1));
            }
            reconPackage.put("Di", Di);
        }
        
        de.putControlData(reconPackage, bi);
    }
}