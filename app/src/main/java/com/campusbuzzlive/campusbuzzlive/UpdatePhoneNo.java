package com.campusbuzzlive.campusbuzzlive;

import android.content.Intent;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdatePhoneNo extends AppCompatActivity {
    Button bUpdate;
    EditText etAddNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone_no);
        bUpdate=(Button) findViewById(R.id.bUpdate);
        etAddNo=(EditText)findViewById(R.id.etAddNo);
        udP();
    }
    public void udP(){
        etAddNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(etAddNo.getText().toString().trim().length()<10) {
                    etAddNo.setError("Enter Valid 10 Digit Number");
                    bUpdate.setEnabled(false);
                }
                else{
                    bUpdate.setEnabled(true);
                }

            }
        });
    }

}
