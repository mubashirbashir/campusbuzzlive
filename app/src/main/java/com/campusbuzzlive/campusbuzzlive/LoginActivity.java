package com.campusbuzzlive.campusbuzzlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    Button bsign;
    Button BLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button bSign = (Button) findViewById(R.id.bSign);
        Button bLogin = (Button) findViewById(R.id.bLogin);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void add(View v)

    {
        if (v.getId() == R.id.bSign) {
            Intent abc = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(abc);
        }
        if (v.getId() == R.id.bLogin) {
            Intent Logintent = new Intent(LoginActivity.this, LoginActivity.class);
            startActivity(Logintent);
        }
    }
}
