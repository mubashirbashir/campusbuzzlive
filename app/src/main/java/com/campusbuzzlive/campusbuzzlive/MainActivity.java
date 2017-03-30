package com.campusbuzzlive.campusbuzzlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
 Button bLogin ;

  //  Button bGuest =(Button) findViewById(R.id.bGuest);
        Button bSign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bSign =(Button) findViewById(R.id.bSign);
        Button bLogin =(Button) findViewById(R.id.bLog);

    }

public void add(View v)

{
    if(v.getId() ==R.id.bSign) {
        Intent abc = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(abc);
    }
    if(v.getId()==R.id.bLog)
    {
        Intent Logintent= new Intent(MainActivity.this,LoginActivity.class);

        startActivity(Logintent);
    }
}
}