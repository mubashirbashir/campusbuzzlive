package com.campusbuzzlive.campusbuzzlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    Button bsign;
    Button BLogin;
    TextView tvForgot;
    EditText etEnroll,etPassword;
    boolean Evalid=false;
    boolean Pvalid=false;
    Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button bSign = (Button) findViewById(R.id.bSign);
        bLogin = (Button) findViewById(R.id.bLog);
        TextView tvForgot = (TextView) findViewById(R.id.tvForgot) ;
        etEnroll = (EditText) findViewById(R.id.etEnroll);
        etPassword =(EditText) findViewById(R.id.etPassword);

        LogInV();

    }
        public void LogInV(){

            etEnroll.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(etEnroll.getText().length()<11){
                        etEnroll.setError("Enrollment ID should be 11 Digits");
                    }
                    else {
                        Evalid= true;
                        if(Pvalid &&Evalid) {
                            bLogin.setEnabled(true);
                        }
                    }

                }
            });
            etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(etPassword.getText().toString().length()<8){
                        etPassword.setError("Password 8-12 Characters");
                    }
                    else{
                        Pvalid=true;
                        if(Pvalid &&Evalid) {
                            bLogin.setEnabled(true);
                        }
                        }
                    }


                });

        }
    public void add(View v)

    {
        if(Evalid&&Pvalid)
        {
            bLogin.setEnabled(true);
        }
        if (v.getId() == R.id.bSign) {
            Intent abc = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(abc);
        }
        if (v.getId() == R.id.bLog) {
            Intent Logintent = new Intent(LoginActivity.this, RadioActivity.class);
            startActivity(Logintent);

        }
        if (v.getId() == R.id.tvForgot) {
            Intent fp = new Intent(LoginActivity.this,ForgotPassword.class);
            startActivity(fp);

        }
    }
}
