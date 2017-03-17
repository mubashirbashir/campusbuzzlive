package com.campusbuzzlive.campusbuzzlive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class ChangePassword extends AppCompatActivity {
    EditText etNewPass;
    EditText etConfirmPass;
    Button bChange;
    boolean NPvalid=false,CPvalid=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        bChange=(Button) findViewById(R.id.bChange);
        etNewPass=(EditText) findViewById(R.id.etNewPass);
        etConfirmPass=(EditText) findViewById(R.id.etConfirmPass);

        ChangePW();
    }

    public void ChangePW(){

        etNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            if(etNewPass.getText().toString().length()<8){
                etNewPass.setError("Password be 8-12 Characters");
            }
            else{
                NPvalid=true;
                if(NPvalid&&CPvalid){
                    bChange.setEnabled(true);
                }
            }
            }
        });

        etConfirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if(!etConfirmPass.getText().toString().equals(etNewPass.getText().toString())){
                  etConfirmPass.setError("Passwords Don't Match");
              }
              else{
                  CPvalid=true;
                  if(NPvalid&&CPvalid){
                      bChange.setEnabled(true);
                  }
              }

            }
        });
    }

}
