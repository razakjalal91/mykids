package com.example.razak.tadikaapps;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ajak on 1/23/2017.
 */

public class main_background extends AsyncTask<Void,Void,String>{
    Context context;
    Bitmap bmap;
    Double latitude,longitude;
    File myDir,file;
    String fname,latStr,longStr,hiop;
    private boolean upflag = false;

    public main_background(Context context,Bitmap bmap,File myDir,String fname,File file,String longStr,String latStr,Double latitude , Double longitude,String hiop){
        this.context = context;
        this.bmap = bmap;
        this.latitude = latitude;
        this.longitude = longitude;
        this.myDir = myDir;
        this.fname = fname;
        this.file = file;
        this.latStr = latStr;
        this.longStr = longStr;
        this.hiop = hiop;
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(Void... arg) {
        String link;
        String data;
        String result = null;

        data = "?longitude=" + longitude;
        data += "&latitude=" + latitude;
        data += "&imagepath=http://dev.16mb.com/mykids/attendance/mykids/attendance/attendanceimage/" + fname;
        data += "&date=" + hiop;
        data += "&studentid=" + 1;
        try {
            FileInputStream fstrm = new FileInputStream(file);
            HttpFileUpload hfu = new HttpFileUpload("http://dev.16mb.com/mykids/attendance/upload.php","Title","Description",fname);
            upflag = hfu.Send_Now(fstrm);

            link = "http://dev.16mb.com/mykids/attendance/insert_attendance.php" + data;
            URL url = new URL(link);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        }catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }

    }

    protected void onPostExecute(String result){
        String jsonStr = result;

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Failed To Update Location.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to Parse JSON!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
}
