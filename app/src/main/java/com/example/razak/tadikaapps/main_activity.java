package com.example.razak.tadikaapps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraDevice;
import android.location.Geocoder;
import android.location.Location;
import android.location.Address;
import com.google.android.gms.location.LocationListener;

import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.R.attr.data;
import static android.hardware.camera2.CameraDevice.*;
import static com.google.android.gms.tagmanager.zzbo.e;
import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by Razak on 1/22/2017.
 */

public class main_activity extends Activity implements com.google.android.gms.location.LocationListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    FloatingActionButton camerafloatbtn, clearpic;
    ImageView snapic;
    public Context context;
    public static String TIME_STAMP;
    int CAMERA_REQUEST = 0;
    Location myLocation;
    GoogleApiClient myGoogle;
    Spinner studentdropdown;
    ArrayList<String> studentLists = new ArrayList<>();
    public static String url = "http://dev.16mb.com/mykids/attendance/studentlist.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        camerafloatbtn = (FloatingActionButton) findViewById(R.id.camerafloatBtn);
        snapic = (ImageView) findViewById(R.id.snapphoto);
        studentdropdown = (Spinner) findViewById(R.id.studentDropdown);
        clearpic = (FloatingActionButton) findViewById(R.id.clearPic);
        buildGoogleApiClient();
        db_helper dh = new db_helper(url,getApplicationContext());
        String a = dh.db_conn();
        student_dropdown(a);
    }

    public void student_dropdown(String a){

        String output = "";
        try {
            JSONObject arrStudent = new JSONObject(a);
            JSONArray students = arrStudent.getJSONArray("students");

            for(int i = 0 ; i < students.length(); i++){
                JSONObject student_obj = students.getJSONObject(i);
                String fullname = student_obj.opt("fullname").toString();

                output = fullname;
                studentLists.add(output);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
              this,R.layout.support_simple_spinner_dropdown_item,studentLists
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            studentdropdown.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void camera(View view) {
        openCamera();
    }

    public void clearpic(View view) {
        snapic.setImageResource(0);
    }

    public void sendimg(View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        LocationRequest lr = LocationRequest.create();
        lr.setSmallestDisplacement(10);
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        currentLocation();
        myLocation = LocationServices.FusedLocationApi.getLastLocation(myGoogle);

        if(myLocation != null) {
            double latitude = myLocation.getLatitude();
            double longitude = myLocation.getLongitude();
            String latStr = Double.toString(latitude);
            String longStr = Double.toString(longitude);

            if (snapic.getDrawable() != null) {
                snapic.buildDrawingCache();
                Bitmap bmap = snapic.getDrawingCache();

                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/saved_images");
                myDir.mkdirs();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String hjkl = currentDateTimeString.replaceAll(" ", "_");
                String hiop = hjkl.replaceAll(":", "-");
                TIME_STAMP = hiop;
                String fname = TIME_STAMP + ".jpg";
                File file = new File(myDir, fname);
                if (file.exists()) file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                    MediaStore.Images.Media.insertImage(getContentResolver(), bmap, fname, "");
                    Toast.makeText(getApplicationContext(),longStr + "," + latStr,Toast.LENGTH_SHORT).show();
                    new main_background(this, bmap, myDir, fname, file, latStr, longStr,latitude,longitude,hiop).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Photo Absent", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Location Is Null", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode == Activity.RESULT_OK && requestCode == 0){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            snapic.setImageResource(0);
            snapic.setImageBitmap(photo);
            Toast.makeText(getApplicationContext(),"Photo Captured!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"No Picture",Toast.LENGTH_SHORT).show();
        }
    }

    protected synchronized void buildGoogleApiClient(){
        myGoogle = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void onStart(){
        super.onStart();
        myGoogle.connect();
    }

    protected void onStop(){
        super.onStop();
        myGoogle.disconnect();
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
        currentLocation();
    }

    public Location currentLocation(){
        return myLocation;
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
