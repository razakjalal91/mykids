package com.example.razak.tadikaapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;

/**
 * Created by Razak on 1/22/2017.
 */

public class splash_activity extends Activity {
    TextView splashtittle;
    ImageView mykidsbg,kidicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        splashtittle = (TextView) findViewById(R.id.splashTittle);
        mykidsbg = (ImageView)  findViewById(R.id.mykidsBg);
        kidicon = (ImageView) findViewById(R.id.kidIcon);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(splash_activity.this,main_activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();
    }
}
