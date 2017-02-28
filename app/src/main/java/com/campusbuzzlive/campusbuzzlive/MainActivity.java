package com.campusbuzzlive.campusbuzzlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {
 // Button bLogin =(Button) findViewById(R.id.bLogin);
  // Button bSign =(Button) findViewById(R.id.bSign);
  //  Button bGuest =(Button) findViewById(R.id.bGuest);
        Button bSign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bSign =(Button) findViewById(R.id.bSign);

    }

public void add(View v)
{
    Intent abc = new Intent(MainActivity.this,SignupActivity.class);
    startActivity(abc);
}
}