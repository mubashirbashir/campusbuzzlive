package com.campusbuzzlive.campusbuzzlive;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.EditTextPreference;
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

public class UpdatePhoneNo extends AppCompatActivity implements View.OnClickListener {
    Button bUpdate;
    EditText etAddNo;
    Session session=new Session();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone_no);
        bUpdate=(Button) findViewById(R.id.bUpdate);
        etAddNo=(EditText)findViewById(R.id.etAddNo);
        udP();
        bUpdate.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {


        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Updating Phone number");
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

                        AlertDialog.Builder builder =new AlertDialog.Builder(UpdatePhoneNo.this);
                        builder.setMessage("Contact Number Successfully updated to "+etAddNo.getText().toString())
                                .setNegativeButton("Ok",null)
                                .create()
                                .show();

                    } else {
                        String msg =jsonResponse.getString("error_msg");
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePhoneNo.this);
                        builder.setMessage(msg)
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                    progressDialog.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        UpdatePhoneRequest updatePhoneRequest = new UpdatePhoneRequest(session.getEnrollSession(), etAddNo.getText().toString(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(UpdatePhoneNo.this);
        queue.add(updatePhoneRequest);

    }
}
