package com.campusbuzzlive.campusbuzzlive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class UpdateEmail extends AppCompatActivity {
    EditText etEmailUD;
    Button bUpdateE;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);
        etEmailUD=(EditText)findViewById(R.id.etEmailUD);
        bUpdateE=(Button) findViewById(R.id.bUpdateE);
        udE();
    }
    public void udE(){
        etEmailUD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!etEmailUD.getText().toString().trim().matches(emailPattern)){
                    etEmailUD.setError("Not Valid E-mail");
                }
                    else{
                    bUpdateE.setEnabled(true);
                }
            }
        });
    }
}
