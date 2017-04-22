package com.campusbuzzlive.campusbuzzlive;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button bsign;
    Button BLogin;
    TextView tvForgot;
    EditText etEnroll,etPassword;
    boolean Evalid=false;
    boolean Pvalid=false;
    Button bLogin;
    SharedPreferences sharedPreferences;
    public static final String MyPreferences="MyPrefes";




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        Button bSign = (Button) findViewById(R.id.bSignin);
        bLogin = (Button) findViewById(R.id.bLog);
        TextView tvForgot = (TextView) findViewById(R.id.tvForgot) ;
        etEnroll = (EditText) findViewById(R.id.etEnroll);
        etPassword =(EditText) findViewById(R.id.etPassword);
     //  String enroll ;
     //  enroll= getIntent().getStringExtra("enrollmentid");
     //   etEnroll.setText(enroll);



        LogInV();

    }
        public void LogInV(){


            etEnroll.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(etEnroll.getText().length()<11){
                        etEnroll.setError("Enrollment ID should be 11 Digits");
                        bLogin.setEnabled(false);
                    }
                    else {
                        Evalid= true;
                        if(Pvalid &&Evalid) {
                            bLogin.setEnabled(true);
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
                    if(etPassword.getText().toString().length()<8){
                        etPassword.setError("Password 8-12 Characters");
                        bLogin.setEnabled(false);
                    }
                    else{
                        Pvalid=true;
                        if(Pvalid &&Evalid) {
                            bLogin.setEnabled(true);
                        }
                    }


                }
            });


        }
    public void add(View v)

    {

        if (v.getId() == R.id.bSignin) {
            Intent abc = new Intent(LoginActivity.this, SignupActivity.class);

            startActivity(abc);
        }
        if (v.getId() == R.id.tvForgot) {
            Intent fp = new Intent(LoginActivity.this,ForgotPassword.class);
            startActivity(fp);

        }

        if (v.getId() == R.id.bLog) {
        final    String enrollmentid = etEnroll.getText().toString().trim();
         final    String password = etPassword.getText().toString().trim();
          final ProgressDialog progressDialog= new ProgressDialog(this);
            progressDialog.setTitle("logging in.....");
            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.show();

           // Toast.makeText(this,enrollmentid+" "+password,Toast.LENGTH_LONG).show();

            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);


                        boolean error = jsonResponse.getBoolean("error");

                        if (!error) {

                            String name = jsonResponse.getString("name");
                            //int age = jsonResponse.getInt("age");
                          //  String msg =jsonResponse.getString("error_msg");
                            sharedPreferences=getSharedPreferences(MyPreferences,Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor =sharedPreferences.edit();
                            editor.putString("sessionEnroll",etEnroll.getText().toString());
                            editor.putString("sessionName",name);

                            editor.putBoolean("isLoggedIn",true);
                            editor.commit();


                            Intent intent = new Intent(LoginActivity.this, RadioActivity.class);
                          //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                             intent.putExtra("name",name);
                            intent.putExtra("enrollmentid",enrollmentid);
                            progressDialog.dismiss();
                            startActivity(intent);
                        } else {
                            String msg =jsonResponse.getString("error_msg");
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage(msg)
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };


            LoginRequest loginRequest = new LoginRequest(enrollmentid, password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }
//            Intent Logintent = new Intent(LoginActivity.this, RadioActivity.class);
//            startActivity(Logintent);
//
//            etEnroll.setText("");
//            etPassword.setText("");
//            bLogin.setEnabled(false);




    }

}
