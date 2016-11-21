/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.bfh.ti.hutzf1.vpriv_client.dataexchange;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author fh
 */

public class DataExchange {
    
    private final String URL = "http://localhost:8080/VPriv_Server/webresources/ServiceProvider";
    
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

    public JSONObject getAllData() throws MalformedURLException, IOException {
        return get(new URL(URL + "/getAllData"));
    }
    
    public JSONObject getControlMethod() throws MalformedURLException, IOException {
        return get(new URL(URL + "/getControlMethod")); 
    }
    
    private void put(JSONObject jo, URL url) throws MalformedURLException, IOException {
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
    }
    
    public void putRoundPackage(JSONObject jo) throws MalformedURLException, IOException {
        put(jo, new URL(URL + "/putRoundPackage"));
    }
    
    public void putDrivingData(JSONObject jo) throws MalformedURLException, IOException {
        put(jo, new URL(URL + "/putDrivingData")); 
    }
    
    public void putCostData(JSONObject jo) throws MalformedURLException, IOException {
        put(jo, new URL(URL + "/putCostData"));
    }
    
    public void putPermutedPackage(JSONObject jo) throws MalformedURLException, IOException {
        put(jo, new URL(URL + "/putPermutedPackage"));
    }
    
    public void putControlData(JSONObject jo, int bi) throws MalformedURLException, IOException {
        put(jo, new URL(URL + "/putControlData" + bi));
    }
}