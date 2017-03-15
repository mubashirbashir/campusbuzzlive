package com.campusbuzzlive.campusbuzzlive;

import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class UpdatePhoneNo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText etAddNo= (EditText) findViewById(R.id.etAddNo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone_no);
    }


}
