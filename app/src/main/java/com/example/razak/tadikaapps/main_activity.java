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
import android.location.LocationListener;
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
import android.util.Size;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.R.attr.data;
import static android.hardware.camera2.CameraDevice.*;

/**
 * Created by Razak on 1/22/2017.
 */

public class main_activity extends Activity {
    GoogleApiClient mGoogleApiClient;
    FloatingActionButton camerafloatbtn, clearpic;
    String ba1;
    ImageView snapic;
    Location mLocation;
    File sdImageMainDirectory;
    private Size previewsize;
    private CameraDevice cameraDevice;
    private Camera myCamera;
    public Context context;
    public static String TIME_STAMP;
    int CAMERA_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        camerafloatbtn = (FloatingActionButton) findViewById(R.id.camerafloatBtn);
        snapic = (ImageView) findViewById(R.id.snapphoto);
        clearpic = (FloatingActionButton) findViewById(R.id.clearPic);
    }


    public void camera(View view) {
        openCamera();
    }

    public void clearpic(View view) {
        snapic.setImageResource(0);
        snapic.setImageResource(R.drawable.splash_kid_icon);
    }

    public void sendimg(View view) {
//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            return;
//        }
//        mLocation = lm.getLastKnownLocation(lm.GPS_PROVIDER);
//        if(mLocation != null) {
//            Double latitude = mLocation.getLatitude();
//            Double longitude = mLocation.getLongitude();
//            Geocoder geo = new Geocoder(this, Locale.getDefault());
//            List<android.location.Address> addresses = null;
//
//            try {
//                addresses = geo.getFromLocation(latitude, longitude, 1);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            String cityName = addresses.get(0).getAddressLine(0);
//            String stateName = addresses.get(0).getAddressLine(1);
//            String countryName = addresses.get(0).getAddressLine(2);
//            String fullAdd = cityName + stateName + countryName;
//            String newAdd = fullAdd.replaceAll("[,\\s]", "_");
//            Toast.makeText(getApplicationContext(), newAdd, Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(getApplicationContext(), "Null bro", Toast.LENGTH_SHORT).show();
//        }

        if(snapic.getDrawable()!=null){
            snapic.buildDrawingCache();
            Bitmap bmap = snapic.getDrawingCache();

            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/saved_images");
            myDir.mkdirs();

            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            String hjkl=currentDateTimeString.replaceAll(" ", "_");
            String hiop=hjkl.replaceAll(":", "-");
            TIME_STAMP = hiop;
            String fname = TIME_STAMP+".jpg";
            File file = new File (myDir, fname);
            if (file.exists ()) file.delete ();
            try {
                FileOutputStream out = new FileOutputStream(file);
                bmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
                // use insertImage(ContentResolver cr, Bitmap source, String title, String description)
                MediaStore.Images.Media.insertImage(getContentResolver(), bmap,fname , "");
                Toast.makeText(getApplicationContext(),"Success!",Toast.LENGTH_SHORT).show();
                new main_background(this,bmap,myDir,fname,file).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Photo Absent",Toast.LENGTH_SHORT).show();
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

    private boolean checkCameraHardware(Context context){
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }else{
            return false;
        }
    }


}
