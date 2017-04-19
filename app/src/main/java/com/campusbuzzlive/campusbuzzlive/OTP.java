package com.campusbuzzlive.campusbuzzlive;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.password;

public class OTP extends AppCompatActivity implements View.OnClickListener {
    String enrollmentid;
    EditText etOTP;
    Button bOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        etOTP=(EditText)findViewById(R.id.etOTP);
        bOTP=(Button) findViewById(R.id.bNext);
        bOTP.setOnClickListener(this);
        enrollmentid= getIntent().getStringExtra("enrollmentid");

    }

    @Override
    public void onClick(View v) {

        Toast.makeText(this,enrollmentid,Toast.LENGTH_LONG).show();
        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Authenticating");
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

                        AlertDialog.Builder builder =new AlertDialog.Builder(OTP.this);
                        builder.setMessage("Success")
                                .setNegativeButton("ok",null)
                                .create()
                                .show();
                        Intent intent= new Intent(OTP.this,ChangePassword.class);
                        intent.putExtra("enrollmentid", enrollmentid);
                        startActivity(intent);

                    } else {
                        String msg =jsonResponse.getString("error_msg");
                        AlertDialog.Builder builder = new AlertDialog.Builder(OTP.this);
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

        OTPRequest otpRequest = new OTPRequest(enrollmentid, etOTP.getText().toString(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(OTP.this);
        queue.add(otpRequest);

    }
}
