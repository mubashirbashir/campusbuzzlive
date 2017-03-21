package com.campusbuzzlive.campusbuzzlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {
    EditText etEnroll,etName,etDepartment,etEmail,etPassword,etConfirm;
    boolean Evalid=false;
    boolean Pvalid=false;
    boolean Nvalid=false;
    boolean Dvalid=false;
    boolean Cvalid=false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    Button bLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etEnroll = (EditText) findViewById(R.id.etEnroll);
        etName =(EditText) findViewById(R.id.etName);
        etDepartment = (EditText) findViewById(R.id.etDepartment);
        etEmail =(EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirm =(EditText) findViewById(R.id.etConfirm);
        bLog=(Button) findViewById(R.id.bLog) ;




        SigInV();
    }

    public void SigInV() {

        etEnroll.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etEnroll.getText().length()<11) {
                    etEnroll.setError("Enrollment ID should be 11 Digits");
                    bLog.setEnabled(false);
                }
                else
                {
                    Evalid= true;
                   if(Pvalid &&Evalid&&Nvalid&&Dvalid&&Cvalid) {
                        bLog.setEnabled(true);
                    }
                }

            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etName.getText().toString().isEmpty()) {
                    etName.setError("Your Name Is Mandatory");
                    bLog.setEnabled(false);
                }
                else
                {
                    Nvalid= true;
                    if(Pvalid &&Evalid&&Nvalid&&Dvalid&&Cvalid) {
                        bLog.setEnabled(true);
                    }
                }



            }
        });

        etDepartment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etDepartment.getText().toString().isEmpty()) {
                    etDepartment.setError("Your Name Is Mandatory");
                    bLog.setEnabled(false);
                }
                else
                {
                    Dvalid= true;
                   if(Pvalid &&Evalid&&Nvalid&&Dvalid&&Cvalid) {
                        bLog.setEnabled(true);
                    }
                }



            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!etEmail.getText().toString().trim().matches(emailPattern)){
                    etEmail.setError("Not a Valid E-mail");
                    bLog.setEnabled(false);
                }
                else {
                    Evalid = true;

                    if (Pvalid && Evalid && Nvalid && Dvalid && Cvalid) {
                        bLog.setEnabled(true);
                    }
                }
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("Password 8-12 Characters");
                } else {
                    Pvalid = true;
                   if(Pvalid &&Evalid&&Nvalid&&Dvalid&&Cvalid) {
                        bLog.setEnabled(true);
                    }

                }
            }
        });

        etConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!etConfirm.getText().toString().equals(etPassword.getText().toString())){
                    etConfirm.setError("Passwords Don't Match");
                    bLog.setEnabled(false);
                }
                else {
                    Cvalid = true;

                    if(Pvalid &&Evalid&&Nvalid&&Dvalid &&Cvalid) {
                        bLog.setEnabled(true);
                    }


                }
            }
        });

    }

    public void add(View v) {

        if (v.getId() == R.id.bLog) {
            Intent Logintent = new Intent(SignupActivity.this, RadioActivity.class);
            startActivity(Logintent);

        }
    }

}
