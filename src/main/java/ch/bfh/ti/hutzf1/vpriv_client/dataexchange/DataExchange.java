/*
 * VPriv Client Server Simulator
 * Copyright 2017 Fabian Hutzli
 * Berner Fachhochschule
 *
 * All rights reserved.
 */
package ch.bfh.ti.hutzf1.vpriv_client.dataexchange;

import ch.bfh.ti.hutzf1.vpriv_client.log.Log;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.json.JSONObject;

/**
 * Methods to Transfer Data between Client and Server
 * @author Fabian Hutzli
 */

public class DataExchange {
    
    private final String URL = "http://localhost:8080/VPriv_Server/webresources/ServiceProvider";
    private final Log LOG;
    
    public DataExchange(Log log) {
        this.LOG = log;
    }
    
    private JSONObject get(URL url) throws MalformedURLException, IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true); 
        connection.setInstanceFollowRedirects(false); 
        connection.setRequestMethod("GET"); 
        connection.setRequestProperty("Content-Type", "application/json");
        
        try (InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
            byte[] buf = new byte[512];
            int read = -1;
            while ((read = inputStream.read(buf)) > 0) {
                byteArrayOutputStream.write(buf, 0, read);
            }
        String str = (new String(byteArrayOutputStream.toByteArray()));
        return (new JSONObject(str));
        }
    }

    /**
     *
     * @return
     */
    
    public JSONObject getAllData() {
        JSONObject jo = null;
        try {
            jo = get(new URL(URL + "/getAllData"));
        } catch (MalformedURLException ex) {
            this.LOG.exception(ex);
        } catch (IOException ex) {
            this.LOG.exception(ex);
        }
        return jo;
    }
    
    /**
     *
     * @return
     */
    
    public JSONObject getControlMethod() {
        JSONObject jo = null;
        try {
            jo = get(new URL(URL + "/getControlMethod"));
        } catch (MalformedURLException ex) {
            this.LOG.exception(ex);
        } catch (IOException ex) {
            this.LOG.exception(ex);
        }
        return jo;
    }
    
    private void put(JSONObject jo, URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            
            OutputStream os = connection.getOutputStream();
            os.write(jo.toString().getBytes());
            os.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);  
            }
        } catch (ProtocolException ex) {
            this.LOG.exception(ex);
        } catch (IOException ex) {
            this.LOG.exception(ex);
        }
    }
    
    /**
     *
     * @param jo
     */
    
    public void putRoundPackage(JSONObject jo) {
        try {
            put(jo, new URL(URL + "/putRoundPackage"));
        } catch (MalformedURLException ex) {
            this.LOG.exception(ex);
        }
    }
    
    /**
     *
     * @param jo
     */
    
    public void putDrivingData(JSONObject jo) {
        try {
            put(jo, new URL(URL + "/putDrivingData"));
        } catch (MalformedURLException ex) {
            this.LOG.exception(ex);
        }
    }
    
    /**
     *
     * @param jo
     */
    
    public void putCostData(JSONObject jo) {
        try {
            put(jo, new URL(URL + "/putCostData"));
        } catch (MalformedURLException ex) {
            this.LOG.exception(ex);
        }
    }
    
    /**
     *
     * @param jo
     */
    
    public void putPermutedPackage(JSONObject jo) {
        try {
            put(jo, new URL(URL + "/putPermutedPackage"));
        } catch (MalformedURLException ex) {
            this.LOG.exception(ex);
        }
    }
    
    /**
     *
     * @param jo
     * @param bi
     */
    
    public void putControlData(JSONObject jo, int bi) {
        try {
            put(jo, new URL(URL + "/putControlData" + bi));
        } catch (MalformedURLException ex) {
            this.LOG.exception(ex);
        }
    }
}