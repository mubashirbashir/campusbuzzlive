package com.campusbuzzlive.campusbuzzlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotPassword extends AppCompatActivity {
    Button bNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Button bNext = (Button) findViewById(R.id.bNext);


    }

    public void add(View v)

    {
        if (v.getId() == R.id.bNext) {
            Intent next = new Intent(ForgotPassword.this, ChangePassword.class);
            startActivity(next);
        }
    }
}