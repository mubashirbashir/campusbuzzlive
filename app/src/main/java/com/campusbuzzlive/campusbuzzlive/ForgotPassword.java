package com.campusbuzzlive.campusbuzzlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity {
    Button bNext;
    Button bSearch;
    EditText etEnrollFind, etEmailFind;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    boolean EFvalid=false, EMFvalid=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        bNext = (Button) findViewById(R.id.bNext);
        bSearch=(Button)findViewById(R.id.bSearch);
        etEmailFind=(EditText)findViewById(R.id.etEmailFind);
        etEnrollFind=(EditText)findViewById(R.id.etEnrollFind);

        searchU();

    }
    public void searchU(){
        etEnrollFind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etEnrollFind.getText().length()<11) {
                    etEnrollFind.setError("Enrollment must be 11 Digit");
                }
                    else{
                        EFvalid=true;
                        if(EMFvalid){
                        bSearch.setEnabled(true);
                    }
                    }


            }
        });

        etEmailFind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!etEmailFind.getText().toString().trim().matches(emailPattern)){
                    etEmailFind.setError("Not Valid E-mail");
                }
                else{
                    EMFvalid=true;
                    if(EFvalid){
                        bSearch.setEnabled(true);
                    }
                }

            }
        });

    }



}