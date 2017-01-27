package com.example.razak.tadikaapps;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ajak on 1/27/2017.
 */

public class db_helper implements Runnable {
    String url;
    Context context;
    // Constructor
    db_helper(String url, Context context){
        this.url = url;
        this.context = context;
    }
    // Constructor END

     public String db_conn(){
         StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
         StrictMode.setThreadPolicy(policy);

         byte[] buf = new byte[4096];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String Tag="fSnd";
         String response;
        HttpURLConnection conn = null;
        FileInputStream fileInputStream = null;
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
            conn.connect();
            InputStream in = new BufferedInputStream(conn.getInputStream()); //This Line Is The Problem
            response = convertStreamToString(in);

            Toast.makeText(context,"In Try",Toast.LENGTH_SHORT).show();
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(context,"Malformed",Toast.LENGTH_SHORT).show();
            return null;
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(context,"IOException",Toast.LENGTH_SHORT).show();
            return null;
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context,"Exception",Toast.LENGTH_SHORT).show();
            return null;
        }
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
