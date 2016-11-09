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

/**
 *
 * @author fh
 */

public class DataExchange {
    
    private final String URL = "http://localhost:8080/VPriv_Server/webresources/ServiceProvider";

    public JSONObject getAllData() throws MalformedURLException, IOException {
        URL url = new URL(URL + "/getAllData"); 
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
        System.out.println(new String(byteArrayOutputStream.toByteArray()));
        }
        return null;
    }
    
    public JSONObject getControlMethod() throws MalformedURLException, IOException {
        URL url = new URL(URL + "/getControlMethod"); 
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
        System.out.println(new String(byteArrayOutputStream.toByteArray()));
        }
        return null;
    }
    
    public void putRoundPackage(JSONObject roundPackage) throws MalformedURLException, IOException {
        URL url = new URL(URL + "/putRoundPackage"); 
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
        connection.setDoOutput(true); 
        connection.setInstanceFollowRedirects(false); 
        connection.setRequestMethod("PUT"); 
        connection.setRequestProperty("Content-Type", "application/json");
        
        OutputStream os = connection.getOutputStream();
        os.write(roundPackage.toString().getBytes());
        os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }
    }
    
    public void putDrivingData(JSONObject drivingData) throws MalformedURLException, IOException {
        URL url = new URL(URL + "/putDrivingData"); 
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
        connection.setDoOutput(true); 
        connection.setInstanceFollowRedirects(false); 
        connection.setRequestMethod("PUT"); 
        connection.setRequestProperty("Content-Type", "application/json");
        
        OutputStream os = connection.getOutputStream();
        os.write(drivingData.toString().getBytes());
        os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }
    }
    
    public void putCostData(JSONObject drivingData) throws MalformedURLException, IOException {
        URL url = new URL(URL + "/putCostData"); 
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
        connection.setDoOutput(true); 
        connection.setInstanceFollowRedirects(false); 
        connection.setRequestMethod("PUT"); 
        connection.setRequestProperty("Content-Type", "application/json");
        
        OutputStream os = connection.getOutputStream();
        os.write(drivingData.toString().getBytes());
        os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }
    }
    
    public void putPermutatedPackage(JSONObject drivingData) throws MalformedURLException, IOException {
        URL url = new URL(URL + "/putPermutatedPackage"); 
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
        connection.setDoOutput(true); 
        connection.setInstanceFollowRedirects(false); 
        connection.setRequestMethod("PUT"); 
        connection.setRequestProperty("Content-Type", "application/json");
        
        OutputStream os = connection.getOutputStream();
        os.write(drivingData.toString().getBytes());
        os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }
    }
    
    public void putControlData(JSONObject drivingData) throws MalformedURLException, IOException {
        URL url = new URL(URL + "/putControlData"); 
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
        connection.setDoOutput(true); 
        connection.setInstanceFollowRedirects(false); 
        connection.setRequestMethod("PUT"); 
        connection.setRequestProperty("Content-Type", "application/json");
        
        OutputStream os = connection.getOutputStream();
        os.write(drivingData.toString().getBytes());
        os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }
    }
}