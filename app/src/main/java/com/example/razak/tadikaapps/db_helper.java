package com.example.razak.tadikaapps;

import android.content.Context;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public db_helper(String url, Context context){
        this.url = url;
        this.context = context;
    }
    // Constructor END

    public boolean db_conn(){
        byte[] buf = new byte[4096];
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(context,"Malformed",Toast.LENGTH_SHORT).show();
            return false;
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(context,"IOException",Toast.LENGTH_SHORT).show();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context,"Exception",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void student_list(){
        if(db_conn()){
            Toast.makeText(context,"Connected",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Not Connected",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void run() {

    }
}
