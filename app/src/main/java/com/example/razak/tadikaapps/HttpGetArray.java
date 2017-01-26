package com.example.razak.tadikaapps;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by Ajak on 1/25/2017.
 */

public class HttpGetArray{
    Context context;
    public HttpGetArray(){
    }

    public String ServiceCall(String reqUrl,Context context){
        String response;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream()); //This Line Is The Problem
            Toast.makeText(context,"Abu Dalam Try!",Toast.LENGTH_SHORT).show();
            response = converStreamToString(in);
            return response;
        }catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
            Toast.makeText(context,"MalformedURLException:",Toast.LENGTH_SHORT).show();
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
            Toast.makeText(context,"ProtocolException:",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
            Toast.makeText(context,"IOException:",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
            Toast.makeText(context,"Exception:" + e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private String converStreamToString(InputStream is) {
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
}
