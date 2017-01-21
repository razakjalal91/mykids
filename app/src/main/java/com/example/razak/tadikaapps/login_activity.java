package com.example.razak.tadikaapps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Razak on 1/22/2017.
 */

public class login_activity extends Activity {
    Button loginbtn,registerbtn;
    EditText passwordfield,usernamefield;
    TextView logintext,notmember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        loginbtn = (Button) findViewById(R.id.loginBtn);
        registerbtn = (Button) findViewById(R.id.registerBtn);
        passwordfield = (EditText) findViewById(R.id.passwordField);
        usernamefield = (EditText) findViewById(R.id.usernameField);
        logintext =(TextView) findViewById(R.id.loginText);
        notmember = (TextView) findViewById(R.id.notMember);


    }
}
