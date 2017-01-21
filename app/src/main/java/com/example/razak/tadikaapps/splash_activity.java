package com.example.razak.tadikaapps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    }
}
