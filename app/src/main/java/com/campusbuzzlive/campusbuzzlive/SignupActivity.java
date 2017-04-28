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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {
    EditText etEnroll, etName, etDepartment, etEmail, etPassword, etConfirm;
    boolean Evalid = false;
    boolean Pvalid = false;
    boolean Nvalid = false;
    boolean Dvalid = false;
    boolean Cvalid = false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    RadioButton rbGender;
    RadioGroup rgGender;


    Button bLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etEnroll = (EditText) findViewById(R.id.etEnroll);
        etName = (EditText) findViewById(R.id.etName);
        etDepartment = (EditText) findViewById(R.id.etDepartment);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirm = (EditText) findViewById(R.id.etConfirm);
        bLog = (Button) findViewById(R.id.bLog);
        rgGender=(RadioGroup)findViewById(R.id.rgGender);
        rgGender.check(R.id.rgf);


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
                if (etEnroll.getText().length() < 11) {
                    etEnroll.setError("Enrollment ID should be 11 Digits");
                    bLog.setEnabled(false);
                } else {
                    Evalid = true;
                    if (Pvalid && Evalid && Nvalid && Dvalid && Cvalid) {
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
                } else {
                    Nvalid = true;
                    if (Pvalid && Evalid && Nvalid && Dvalid && Cvalid) {
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
                } else {
                    Dvalid = true;
                    if (Pvalid && Evalid && Nvalid && Dvalid && Cvalid) {
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

                if (!etEmail.getText().toString().trim().matches(emailPattern)) {
                    etEmail.setError("Not a Valid E-mail");
                    bLog.setEnabled(false);
                } else {
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
                if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("Password 8-12 Characters");
                } else {
                    Pvalid = true;
                    if (Pvalid && Evalid && Nvalid && Dvalid && Cvalid) {
                        bLog.setEnabled(true);
                    }

                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!etConfirm.getText().toString().equals(etPassword.getText().toString())) {
                    etConfirm.setError("Passwords Don't Match");
                    bLog.setEnabled(false);
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
                if (!etConfirm.getText().toString().equals(etPassword.getText().toString())) {
                    etConfirm.setError("Passwords Don't Match");
                    bLog.setEnabled(false);
                } else {
                    Cvalid = true;

                    if (Pvalid && Evalid && Nvalid && Dvalid && Cvalid) {
                        bLog.setEnabled(true);
                    }


                }
            }
        });

    }

    public void add(View v) {

        if (v.getId() == R.id.bLog) {

            final String name = etName.getText().toString();
            final String email = etEmail.getText().toString();
            final String password = etPassword.getText().toString();
            final String department = etDepartment.getText().toString();
            final String enrollmentid = etEnroll.getText().toString();

            int selectedId = rgGender.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            rbGender = (RadioButton) findViewById(selectedId);
            final String gender = rbGender.getText().toString();
            final ProgressDialog progressDialog= new ProgressDialog(this);
            progressDialog.setTitle("Registering User...");
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
                            Toast.makeText(getApplicationContext(),"Registration Successfull.Please Log In to continue.",Toast.LENGTH_LONG).show();
//                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
//                            builder.setMessage("Registration Successfull.Please Log In to continue.")
//                                    .setNegativeButton("Log In", null)
//                                    .create()
//                                    .show();

                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                         //  intent.putExtra("enrollmentid",enrollmentid);
                            SignupActivity.this.startActivity(intent);
                        } else {
                            String msg= jsonResponse.getString("error_msg");
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
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
            Response.ErrorListener errorListener = new Response.ErrorListener() {

                @Override
                public void onErrorResponse(final VolleyError error) {


                    final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(SignupActivity.this);
                    progressDialog.dismiss();

                    alert.setTitle("No response");
                    alert.setMessage("Please check your internet connection");

                    alert.setNegativeButton("ok",null);
                    alert.show();


                }
            };

            RegisterRequest registerRequest = new RegisterRequest(name, email, password, enrollmentid, department, gender, responseListener,errorListener);
            RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
            queue.add(registerRequest);


            // Intent Logintent = new Intent(SignupActivity.this, LoginActivity.class);
            //  startActivity(Logintent);

        }
    }
}


