package com.campusbuzzlive.campusbuzzlive;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    Button bNext;
    Button bSearch;
    EditText etEnrollFind, etEmailFind;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    boolean EFvalid=false, EMFvalid=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        bSearch=(Button)findViewById(R.id.bSearch);
        etEmailFind=(EditText)findViewById(R.id.etEmailFind);
        etEnrollFind=(EditText)findViewById(R.id.etEnrollFind);
        bSearch.setOnClickListener(this);
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
                    bSearch.setEnabled(false);
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
                    bSearch.setEnabled(false);
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


    @Override
    public void onClick(View v) {
        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Checking details");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);


                    boolean error = jsonResponse.getBoolean("error");

                    if (!error) {
                        progressDialog.dismiss();

                        AlertDialog.Builder builder =new AlertDialog.Builder(ForgotPassword.this);
                        builder.setMessage("Please check your email")
                                .setNegativeButton("ok",null)
                                .create()
                                .show();
                    } else {
                        progressDialog.dismiss();
                        String msg =jsonResponse.getString("error_msg");
                        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
                        builder.setMessage(msg)
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest( etEmailFind.getText().toString(), etEnrollFind.getText().toString(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(ForgotPassword.this);
        queue.add(forgotPasswordRequest);




    }
}