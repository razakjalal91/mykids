package com.example.razak.tadikaapps;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import static com.google.android.gms.internal.zzs.TAG;
/**
 * Created by Ajak on 1/26/2017.
 */

public class studentlist_background extends AsyncTask<Void, Void, String>{
    String url;
    Context context;
    URL newUrl;
    public studentlist_background(String url,Context context){
        this.url = url;
        this.context = context;

        try{
            newUrl = new URL(url);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(Void... voids) {
        String response = null;
        try {
            Toast.makeText(context,"Abu Dalam Try!",Toast.LENGTH_SHORT).show();

            HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
            conn.connect();

            conn.getResponseCode();

            InputStream in = new BufferedInputStream(conn.getInputStream()); //This Line Is The Problem
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

    protected void onPostExecute(){

    }
}
