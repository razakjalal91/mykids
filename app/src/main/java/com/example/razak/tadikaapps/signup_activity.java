package com.example.razak.tadikaapps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Razak on 1/22/2017.
 */

public class signup_activity extends Activity {
    Button registerbtn,loginbtn;
    EditText emailfield,passwordfield,usernamefield;
    TextView signuptext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        registerbtn = (Button) findViewById(R.id.registerBtn);
        loginbtn = (Button) findViewById(R.id.loginBtn);
        emailfield = (EditText) findViewById(R.id.emailField);
        passwordfield = (EditText) findViewById(R.id.passwordField);
        usernamefield = (EditText) findViewById(R.id.usernameField);
        signuptext = (TextView) findViewById(R.id.signupText);
    }
}
