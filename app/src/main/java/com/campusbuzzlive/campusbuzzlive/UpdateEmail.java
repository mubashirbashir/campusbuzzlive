package com.campusbuzzlive.campusbuzzlive;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class UpdateEmail extends AppCompatActivity implements View.OnClickListener {
    Session session= new Session();
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
        bUpdateE.setOnClickListener(this);
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
                    bUpdateE.setEnabled(false);
                }
                    else{
                    bUpdateE.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
     final    ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Updating Email");
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

                        AlertDialog.Builder builder =new AlertDialog.Builder(UpdateEmail.this);
                        builder.setMessage("Email Successfully updated to "+etEmailUD.getText().toString())
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(),RadioActivity.class);
                                        startActivity(intent);

                                    }
                                })
                                .create()
                                .show();

                    } else {
                        String msg =jsonResponse.getString("error_msg");
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateEmail.this);
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


        UpdateEmailRequest updateEmailRequest = new UpdateEmailRequest(session.getEnrollSession(), etEmailUD.getText().toString(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(UpdateEmail.this);
        queue.add(updateEmailRequest);

    }
}
