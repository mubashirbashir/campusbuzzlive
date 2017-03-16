package com.campusbuzzlive.campusbuzzlive;

import android.content.Intent;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdatePhoneNo extends AppCompatActivity {
    Button bUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText etAddNo= (EditText) findViewById(R.id.etAddNo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone_no);
        bUpdate=(Button) findViewById(R.id.bUpdate);
    }
 public void add(View v)
 {
     Intent intent= new Intent(this,FeedbackComments.class);
     startActivity(intent);

 }

}
