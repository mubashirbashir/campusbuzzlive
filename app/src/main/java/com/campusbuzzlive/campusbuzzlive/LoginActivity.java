package com.campusbuzzlive.campusbuzzlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    Button bsign;
    Button BLogin;
    TextView tvForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button bSign = (Button) findViewById(R.id.bSign);
        Button bLogin = (Button) findViewById(R.id.bLogin);
        TextView tvForgot = (TextView) findViewById(R.id.tvForgot) ;
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
        if (v.getId() == R.id.tvForgot) {
            Intent fp = new Intent(LoginActivity.this,ForgotPassword.class);
            startActivity(fp);

        }
    }
}
