package com.example.razak.tadikaapps;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static com.google.android.gms.internal.zzs.TAG;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Ajak on 1/25/2017.
 */

public class HttpGetArray implements Runnable{
    Context context;
    URL url;
    public HttpGetArray(String reqUrl,Context context){
        try {
            url = new URL(reqUrl);
            this.context = context;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String ServiceCall(){
        String response;
        String result;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            Toast.makeText(context,"Abu Dalam Try!",Toast.LENGTH_SHORT).show();
            conn.getResponseCode();
            InputStream in = new BufferedInputStream(conn.getInputStream()); //This Line Is The Problem
            response = convertStreamToString(in);
            return response;
        }catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return null;
    }


    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }

    @Override
    public void run() {

    }
}
