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

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    EditText etNewPass;
    EditText etConfirmPass;
    Button bChange;
    boolean NPvalid=false,CPvalid=false;
    String prev;
    Session session= new Session();
    String enrollmentid, enroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        bChange=(Button) findViewById(R.id.bChange);
        etNewPass=(EditText) findViewById(R.id.etNewPass);
        etConfirmPass=(EditText) findViewById(R.id.etConfirmPass);
        enroll= getIntent().getStringExtra("enrollmentid");
        if(session.getEnrollSession()!=null){

            enrollmentid= session.getEnrollSession();
        }
        else if(enroll != null) {

            enrollmentid=enroll;
        }
        ChangePW();
       bChange.setOnClickListener(this);
    }

    public void ChangePW(){

        etNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etNewPass.getText().toString().length()<8){
                    etNewPass.setError("Password be 8-12 Characters");
                    bChange.setEnabled(false);
                }
                else{
                    prev=etNewPass.getText().toString();
                    NPvalid=true;
                    if(NPvalid&&CPvalid){
                        bChange.setEnabled(true);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!etConfirmPass.getText().toString().equals(etNewPass.getText().toString())){
                    etConfirmPass.setError("Passwords Don't Match");
                    bChange.setEnabled(false);
                }


            }
        });

        etConfirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!etConfirmPass.getText().toString().equals(etNewPass.getText().toString())){
                    etConfirmPass.setError("Passwords Don't Match");
                    bChange.setEnabled(false);
                }
                else{
                    CPvalid=true;
                    if(NPvalid&&CPvalid){
                        bChange.setEnabled(true);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });
    }



    @Override
    public void onClick(View v) {
        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Changing Password");
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

                        AlertDialog.Builder builder =new AlertDialog.Builder(ChangePassword.this);
                        builder.setMessage("Password successfully changed" )
                                .setNegativeButton("ok",null)
                                .create()
                                .show();
                        Intent gotoLogin = new Intent(ChangePassword.this,LoginActivity.class);
                        gotoLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(gotoLogin);
                    } else {
                        String msg =jsonResponse.getString("error_msg");
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                        builder.setMessage(msg)
                                .setNegativeButton("ok", null)
                                .create()
                                .show();

                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


         ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(enrollmentid, etNewPass.getText().toString(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(ChangePassword.this);
        queue.add(changePasswordRequest);

    }
}
