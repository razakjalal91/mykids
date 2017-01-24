package com.example.razak.tadikaapps;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Ajak on 1/23/2017.
 */

public class main_background extends AsyncTask{
    Context context;
    Bitmap bmap;
    Double latitude,longitude;
    File myDir,file;
    String fname;
    private boolean upflag = false;

    public main_background(Context context,Bitmap bmap,File myDir,String fname,File file){
        this.context = context;
        this.bmap = bmap;
        this.latitude = latitude;
        this.longitude = longitude;
        this.myDir = myDir;
        this.fname = fname;
        this.file = file;
    }

    protected void onPreExecute(){

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String link,linkUpload;
        String data;

        try {
            FileInputStream fstrm = new FileInputStream(file);
            HttpFileUpload hfu = new HttpFileUpload("http://dev.16mb.com/mykids/attendance/upload.php","Title","Description",fname);
            upflag = hfu.Send_Now(fstrm);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(){

    }
}
