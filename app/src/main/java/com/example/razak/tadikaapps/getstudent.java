package com.example.razak.tadikaapps;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ajak on 1/24/2017.
 */

public class getstudent extends AsyncTask<Object, Object, JSONArray> {
    Context context;
    Spinner studentdropdown;
    private static String url = "http://dev.16mb.com/mykids/attendance/studentlist.php";

    public getstudent(){

    }

    protected void onPreExecute(){

    }

    @Override
    protected JSONArray doInBackground(Object... arg) {
        return null;
    }

    protected void onPostExecute(){

    }

}
