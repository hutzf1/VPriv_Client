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
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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

    public Vehicle(PedersenScheme ps, OneWayFunction hash, Log log, int n, int s, int i) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        
        this.ps = ps;
        this.hash = hash;
        this.log = log;
        this.de = new DataExchange();
        
        // Set vehicles license plate
        this.ID = generateID();
        
        // Set parameter
        this.i = i;
        this.n = n;
        this.s = s;
        
        // Set tags, keys, opening keys
        this.TAGS = new ArrayList<>();
        this.KEYS = new ArrayList<>();
        this.DV = new ArrayList<>();
        this.DK = new ArrayList<>();
        this.DC = new ArrayList<>();
        
        this.log.file(this.ID);
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
        return this.TAGS.get(rand.nextInt(this.n));
    }

    private void registration() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        // Start vehicle registration
        this.log.both(this.ID + " starts registration phase.");
        
        // Generate Fresh Tags
        this.log.both(this.ID + " generates fresh tags.");
        
        for (int x = 0; x < this.n; x++) {
            this.TAGS.add(this.ps.getTag().convertToBigInteger());
            this.log.both(this.ID + " generates tag: " + this.TAGS.get(x).toString());
        }
        
        // Generate Fresh Keys
        this.log.both(this.ID + " generates fresh keys.");
        
        for (int x = 0; x < this.s; x++) {
            this.KEYS.add(this.ps.getKey().convertToBigInteger());
            this.log.both(this.ID + " generates key: " + this.KEYS.get(x).toString());
        }
  
        // Generate Opening Keys for Tags
        this.log.both(this.ID + " generates opening keys for tags.");
        
        for (int x = 0; x < this.s; x++) {
            for (int y = 0; y < this.n; y++) {
                int index = x * y + y;
                this.DV.add(this.ps.getOpeningKey().convertToBigInteger());
                this.log.both(this.ID + " generates tag opening key: " + this.DV.get(index).toString());
            }
        }
        
        // Generate Opening Keys for Keys
        this.log.both(this.ID + " generates opening keys for keys.");
        
        for (int x = 0; x < this.s; x++) {
            this.DK.add(this.ps.getOpeningKey().convertToBigInteger());
            this.log.both(this.ID + " generates key opening key: "+ this.DK.get(x).toString());
        }
        
        // Generate round package to send data to service provider
        for (int x = 0; x <= this.i; x++) {
            JSONObject roundPackage = new JSONObject();
            
            roundPackage.put("type", "roundpackage");
            this.log.both(this.ID + " generates round package.");

            roundPackage.put("id", this.ID);
            this.log.both(this.ID + " adds ID " + this.ID + " to round package.");

            roundPackage.put("round", x);
            this.log.both(this.ID + " adds round " + x + " to round package.");
            
            roundPackage.put("key", this.ps.commit(this.ps.getElement(this.KEYS.get(x)), this.ps.getElement(this.DK.get(x))).convertToBigInteger());
            this.log.both(this.ID + " adds key " + this.KEYS.get(x) + " to round package");
            
            for(int y = 0; y < this.n; y++) {
                int index = y * x + x;
                //System.out.println((TAGS.get(y)).toString());
                //System.out.println(hash.getHash(TAGS.get(y), DK.get(x)).toString());
                BigInteger v = this.ps.commit(this.ps.getElement(this.hash.getHash(this.TAGS.get(y), this.DK.get(x))), this.ps.getElement(this.DV.get(index))).convertToBigInteger();
                roundPackage.put("v" + y, v);
                this.log.both(this.ID + " adds commit of crypted " + this.TAGS.get(y).toString() + " to round package: " + v.toString());
            }
            
            this.log.both(this.ID + " sends round package to service provider.");
            this.de.putRoundPackage(roundPackage);   
        }
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
        
        this.de.putDrivingData(drivingData);
        
        this.log.both(this.ID + " is driving. Tag " + randomTag.toString() + " (" + currentLocation.LATIDUDE + ", " + currentLocation.LONGITUDE + ") - " + timestamp);
    }

    public void reconciliation() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        int cost = 0;
        int bi;
        int round = 0;
        BigInteger Di = this.ps.getOpeningKey().convertToBigInteger();
        
        JSONObject W = this.de.getAllData();
        JSONObject costData = new JSONObject();
        JSONObject permutedPackage = new JSONObject();
        JSONObject reconPackage = new JSONObject();
        
        this.log.console(W.toString());
        
        this.log.both(this.ID + " is calculating cost...");
        
        for(int x = 0; x < this.n; x++) {
            for(int y = 1; y <= W.length()/2; y++){
                this.log.both("TAG " + this.TAGS.get(x).toString());
                this.log.both("W " + W.getBigInteger("w" + y).toString());
                if(this.TAGS.get(x).equals(W.getBigInteger("w" + y))) {
                    log.both("COST " + Integer.toString(W.getInt("c" + y)));
                    cost += W.getInt("c" + y);
                }
            }
        }

        this.log.both(this.ID + " calculated " + cost);
        
        costData.put("type", "costdata");
        costData.put("id", this.ID);
        costData.put("cost", cost);

        this.de.putCostData(costData);

        // Send permutated data to service provider
        // Opening Keys for Costs
        this.log.both(this.ID + " generates opening keys for costs");
        
        for (int x = 0; x <= this.i; x++) {
            for (int y = 0; y < W.length()/2; y++) {
                int index = x * y + y;
                this.DC.add(ps.getOpeningKey().convertToBigInteger());
                this.log.both(ID + " tag opening key: " + this.DC.get(index).toString());
            }
        }
        
        this.log.both(this.ID + " is permutating W and send the package to service provider");
        permutedPackage.put("id", this.ID);
        permutedPackage.put("U", this.i);
        BigInteger w;
        BigInteger c;
        
        List<Integer> shuffle = new ArrayList<>();
        for (int x = 1; x <= W.length() / 2; x++)
        {
            shuffle.add(x);
        }
        this.log.console(shuffle.toString());
        Collections.shuffle(shuffle);
        this.log.console(shuffle.toString());
        
        for (int x = 1; x <= W.length() / 2; x++) {
            w = W.getBigInteger("w" + x);
            c = W.getBigInteger("c" + x);
            permutedPackage.put("w" + shuffle.get(x - 1), this.hash.getHash(w, this.KEYS.get(this.i)));
            permutedPackage.put("c" + shuffle.get(x - 1), this.ps.commit(this.ps.getElement(c), this.ps.getElement(this.DC.get(x-1))).convertToBigInteger());
        }  
        
        this.de.putPermutedPackage(permutedPackage);
        
        this.log.console(W.toString());
        this.log.console(permutedPackage.toString());
        
        bi = this.de.getControlMethod().getInt("bi");

        this.log.both(this.ID + " bi is: " + Integer.toString(bi)); 
        /*
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
        
        de.putControlData(reconPackage, bi); */
    }
}