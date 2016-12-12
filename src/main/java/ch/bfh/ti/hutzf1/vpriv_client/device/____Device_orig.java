/*
 * VPriv Client Server Simulator
 * Copyright 2017 Fabian Hutzli
 * Berner Fachhochschule
 *
 * All rights reserved.
 */
package ch.bfh.ti.hutzf1.vpriv_client.device;

import ch.bfh.ti.hutzf1.vpriv_client.crypto.OneWayFunction;
import ch.bfh.ti.hutzf1.vpriv_client.crypto.PedersenScheme;
import ch.bfh.ti.hutzf1.vpriv_client.dataexchange.DataExchange;
import ch.bfh.ti.hutzf1.vpriv_client.location.Location;
import ch.bfh.ti.hutzf1.vpriv_client.log.Log;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.json.JSONObject;

/**
 * Defines the Device and all its function
 * @author Fabian Hutzli
 */

public final class ____Device_orig {
    
    private final String ID;
 
    private final ArrayList<BigInteger> ALLTAGS;
    private final ArrayList<BigInteger> TAGS;
    private final ArrayList<BigInteger> KEYS;
    private final ArrayList<BigInteger> DV;
    private final ArrayList<BigInteger> DK;
    private final ArrayList<BigInteger> DC;
    
    private final int I;
    private final int N;
    private final int S;
    
    private final PedersenScheme PS;
    private final OneWayFunction HASH;
    private final Log LOG;
    private final DataExchange DE;

    /**
     *
     * @param ps
     * @param hash
     * @param log
     * @param n
     * @param s
     * @param i
     */
    
    public ____Device_orig(PedersenScheme ps, OneWayFunction hash, Log log, int n, int s, int i) {
        
        this.PS = ps;
        this.HASH = hash;
        this.LOG = log;
        this.DE = new DataExchange(log);
        
        // Set vehicles license plate
        this.ID = generateID();
        
        // Set parameter
        this.I = i;
        this.N = n;
        this.S = s;
        
        // Set tags, keys, opening keys
        this.ALLTAGS = new ArrayList<>();
        this.TAGS = new ArrayList<>();
        this.KEYS = new ArrayList<>();
        this.DV = new ArrayList<>();
        this.DK = new ArrayList<>();
        this.DC = new ArrayList<>();
        
        this.LOG.file(this.ID);
        this.registration();
    }
    
    private String generateID() {
        String chars = "0123456789";
        int length = 15;
        
        Random rand = new Random();
        StringBuilder buf = new StringBuilder();
        for (int x = 0; x < length; x++) {
            buf.append(chars.charAt(rand.nextInt(chars.length())));
            if(x == 7 || x == 13){
                buf.append("-");
            }
        }
        return buf.toString();
    }
    
    private BigInteger getRandomTag() {
        Random rand = new Random();
        int index = rand.nextInt(this.N);
        if(index >= TAGS.size()) {
            index = TAGS.size() - 1;
        }
        return this.TAGS.remove(index);
    }

    private void registration() {
        // Start vehicle registration
        this.LOG.both(this.ID + " starts registration phase.");
        
        // Generate Fresh Tags
        this.LOG.both(this.ID + " generates fresh tags.");
        
        for (int x = 0; x < this.N; x++) {
            BigInteger tag = this.PS.getRandomElement();
            this.TAGS.add(tag);
            this.ALLTAGS.add(tag);
            this.LOG.both(this.ID + " generates tag: " + this.TAGS.get(x).toString());
        }
        
        // Generate Fresh Keys
        this.LOG.both(this.ID + " generates fresh keys.");
        
        for (int x = 0; x < this.S; x++) {
            this.KEYS.add(this.PS.getRandomElement());
            this.LOG.both(this.ID + " generates key: " + this.KEYS.get(x).toString());
        }
  
        // Generate Opening Keys for Tags
        this.LOG.both(this.ID + " generates opening keys for tags.");
        
        for (int x = 0; x < this.S; x++) {
            for (int y = 0; y < this.N; y++) {
                int index = x *(this.N - 1) + x + y;
                this.DV.add(this.PS.getRandomElement());
                this.LOG.both(this.ID + " generates tag opening key: " + this.DV.get(index).toString());
            }
        }
        
        // Generate Opening Keys for Keys
        this.LOG.both(this.ID + " generates opening keys for keys.");
        
        for (int x = 0; x < this.S; x++) {
            this.DK.add(this.PS.getRandomElement());
            this.LOG.both(this.ID + " generates key opening key: "+ this.DK.get(x).toString());
        }
        
        // Generate round package to send data to service provider
        for (int x = 0; x < this.I; x++) {
            JSONObject roundPackage = new JSONObject();
            
            //roundPackage.put("type", "roundpackage");
            //this.LOG.both(this.ID + " generates round package.");

            roundPackage.put("id", this.ID);
            this.LOG.both(this.ID + " adds ID " + this.ID + " to round package.");

            roundPackage.put("round", x);
            this.LOG.both(this.ID + " adds round " + x + " to round package.");
            
            //roundPackage.put("key", this.ps.commit(ps.getElement(this.KEYS.get(x)), ps.getElement(this.DK.get(x))));
            roundPackage.put("key", this.PS.commit(this.KEYS.get(x), this.DK.get(x)));
            this.LOG.both(this.ID + " adds key " + this.KEYS.get(x) + " to round package");
            
            for(int y = 0; y < this.N; y++) {
                int index = x *(this.N - 1) + x + y;
                //int index = y * x + x;
                BigInteger v = this.PS.commit(this.HASH.getHash(this.TAGS.get(y), this.KEYS.get(x)), this.DV.get(index));
                roundPackage.put("v" + y, v);
                this.LOG.both(this.ID + " adds commit of crypted " + this.TAGS.get(y).toString() + " to round package: " + v.toString());
            }
            
            this.LOG.both(this.ID + " sends round package to service provider.");
            this.DE.putRoundPackage(roundPackage);   
        }
    }
    
    /**
     *
     */
    
    public void drive() {
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
        
        this.DE.putDrivingData(drivingData);
        
        this.LOG.both(this.ID + " is driving. Tag " + randomTag.toString() + " (" + currentLocation.LATIDUDE + ", " + currentLocation.LONGITUDE + ") - " + timestamp);
    }

    /**
     *
     */
    
    public void reconciliation() {
        int cost = 0;
        int bi;
        int round = 0;
        BigInteger Di = this.PS.getRandomElement();
        
        JSONObject W = this.DE.getAllData();
        JSONObject costData = new JSONObject();
        JSONObject permutedPackage = new JSONObject();
        JSONObject reconPackage = new JSONObject();
        
        this.LOG.console(W.toString());
        
        this.LOG.both(this.ID + " is calculating cost...");
        
        for(int x = 0; x < this.N; x++) {
            for(int y = 1; y <= W.length()/2; y++){
                //this.LOG.both("TAG " + this.ALLTAGS.get(x).toString());
                //this.LOG.both("W " + W.getBigInteger("w" + y).toString());
                if(this.ALLTAGS.get(x).equals(W.getBigInteger("w" + y))) {
                    //LOG.both("COST " + Integer.toString(W.getInt("c" + y)));
                    cost += W.getInt("c" + y);
                }
            }
        }

        this.LOG.both(this.ID + " calculated " + cost);
        
        costData.put("type", "costdata");
        costData.put("id", this.ID);
        costData.put("cost", cost);

        this.DE.putCostData(costData);

        // Send permutated data to service provider
        // Opening Keys for Costs
        this.LOG.both(this.ID + " generates opening keys for costs");
        
        for (int x = 0; x < this.I; x++) {
            for (int y = 0; y < W.length()/2; y++) {
                int index = x *(W.length()/2 - 1) + x + y;
                //int index = x * y + y;
                this.DC.add(PS.getRandomElement());
                this.LOG.both(ID + " tag opening key: " + this.DC.get(index).toString());
            }
        }
        
        this.LOG.both(this.ID + " is permutating W and send the package to service provider");
        permutedPackage.put("id", this.ID);
        permutedPackage.put("U", this.I);
        BigInteger w;
        BigInteger c;
        
        for (int x = 1; x <= W.length() / 2; x++) {
            permutedPackage.put("w" + x, this.HASH.getHash(W.getBigInteger("w" + x), this.KEYS.get(this.I - 1)));
            permutedPackage.put("c" + x, this.PS.commit(W.getBigInteger("c" + x), this.DC.get(x-1)));
        }
        
        /*
        List<Integer> shuffle = new ArrayList<>();
        for (int x = 1; x <= W.length() / 2; x++)
        {
            shuffle.add(x);
        }
        this.LOG.console(shuffle.toString());
        Collections.shuffle(shuffle);
        this.LOG.console(shuffle.toString());
        
        for (int x = 1; x <= W.length() / 2; x++) {
            w = W.getBigInteger("w" + x);
            c = W.getBigInteger("c" + x);
            permutedPackage.put("w" + shuffle.get(x - 1), this.HASH.getHash(w, this.KEYS.get(this.I - 1)));
            permutedPackage.put("c" + shuffle.get(x - 1), this.PS.commit(c, this.DC.get(x-1)));
            //this.LOG.console("PERMUT KEY " + this.KEYS.get(this.I - 1).toString());
        }*/
        
        this.DE.putPermutedPackage(permutedPackage);
        
        //this.LOG.console(W.toString());
        //this.LOG.console(permutedPackage.toString());
        
        bi = this.DE.getControlMethod().getInt("bi");
        
        this.LOG.both(this.ID + " bi is: " + Integer.toString(bi)); 
        
        // Vehicle sends to Service Provider
        reconPackage.put("id", ID);
        if(bi == 0){
            reconPackage.put("dki", KEYS.get(this.I));
            for (int x = 1; x <= W.length()/2; x++) {
                reconPackage.put("dci" + x, DC.get(x-1));
            }
        }
        else if(bi == 1){
            for (int x = 1; x <= DV.size(); x++) {
                reconPackage.put("dvi" + x, DV.get(x-1));
                //this.LOG.console("reconpack put " + DV.get(x-1).toString());
            }
            reconPackage.put("Di", Di);
        }
        
        DE.putControlData(reconPackage, bi);
    }
}