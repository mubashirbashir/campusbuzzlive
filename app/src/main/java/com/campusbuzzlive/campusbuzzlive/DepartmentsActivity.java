package com.campusbuzzlive.campusbuzzlive;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DepartmentsActivity extends AppCompatActivity {

    ArrayAdapter<HashMap<String, String>> arrayAdapter;
    JSONArray schoolArray = null;
    ArrayList<HashMap<String, String>> schoolList;
    HashMap<String, String> schoolMap = new HashMap<String, String>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    RequestQueue queue;
    String schoolId,schoolName;
    Double latD,lngD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);
        schoolId = getIntent().getStringExtra("id");
        schoolName=    getIntent().getStringExtra("name");

        TextView tvSchoolname =(TextView)findViewById(R.id.schoolName);
        tvSchoolname.setText(schoolName);
        schoolList=new ArrayList<HashMap<String, String>> ();
        ListView listView = (ListView) findViewById(R.id.lvDepartments) ;
        arrayAdapter= new ArrayAdapter<HashMap<String, String>>(this,R.layout.departments_design,R.id.tvHODname){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View  v = getLayoutInflater().inflate(R.layout.departments_design,parent,false);
                TextView tvDname = (TextView) v.findViewById(R.id.tvDname);
                TextView tvHOD = (TextView) v. findViewById(R.id.tvHODname);
                final TextView tvPhone = (TextView) v.findViewById(R.id.tvPhone);
                final TextView tvEmail = (TextView) v. findViewById(R.id.tvMail);
                final TextView tvLocation =(TextView)v.findViewById(R.id.tvLocation);
                final HashMap<String, String> item=getItem(position);
                tvDname.setText(item.get("Dname"));
                tvHOD.setText(item.get("HODname"));
                tvPhone.setText(item.get("phone"));
                tvEmail.setText(item.get("email"));
              //  final String locationid = item.get("email");
                final String departmentname = item.get("Dname");
                tvLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getCoordinates(departmentname);

                    }
                });
               tvEmail.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(Intent.ACTION_SENDTO);
                       intent.setType("text/plain");
                       //intent.setType("message/rfc822");
                       intent.setData(Uri.parse("mailto:"+tvEmail.getText().toString()));

                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       try {
                           startActivity(Intent.createChooser(intent, "Send Mail"));
                       } catch (android.content.ActivityNotFoundException ex) {
                           Toast.makeText(DepartmentsActivity.this, "No Email Client", Toast.LENGTH_SHORT).show();

                       }

                   }
               });
                tvPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent((Intent.ACTION_DIAL));
                        intent.setData(Uri.parse("tel:0"+tvPhone.getText().toString()));
                        startActivity(intent);
                    }
                });
                return v;
            }
        } ;

        listView.setAdapter(arrayAdapter);
        final ProgressDialog pg = new
                ProgressDialog(this);
        pg.setTitle("Loading");
        pg.setMessage("Please Wait...");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    //  boolean error = jsonResponse.getBoolean("error");

                    schoolArray= jsonResponse.getJSONArray("departments");




                    for(int i=0;i<schoolArray.length();i++) {
                        JSONObject c = schoolArray.getJSONObject(i);
                        String Dname = c.getString("Dname");


                        String HODname = c.getString("HODname");
                        String phone = c.getString("phone");
                        String email = c.getString("email");
                     //   String locationid = c.getString("locationid");


                        HashMap<String,String> schoolMap = new HashMap<String,String>();

                        schoolMap.put("Dname",Dname);
                        schoolMap.put("HODname",HODname);
                        schoolMap.put("phone",phone);
                        schoolMap.put("email",email);
                    //    schoolMap.put("locationid",locationid);




                        schoolList.add(schoolMap);


                    }



                    arrayAdapter.addAll(schoolList);





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetDepartmentsRequest getDepartmentsRequest = new GetDepartmentsRequest(schoolId , responseListener);
        queue = Volley.newRequestQueue(DepartmentsActivity.this);
        queue.add(getDepartmentsRequest);

pg.dismiss();
    }
    String lat,lng;
    private void getCoordinates(final String departmentname) {



        final ProgressDialog pg = new
                ProgressDialog(this);
        pg.setTitle("Fetching Coordinates");
        pg.setMessage("Please Wait...");
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    Boolean error = jsonResponse.getBoolean("error");
                    if(!error){



                        lat = jsonResponse.getString("lat");
                        lng = jsonResponse.getString("lng");
                        Log.e("TAdfsdfG", "minnn: " + lat + lng );



                        try {
                            latD = Double.parseDouble(lat);
                            lngD = Double.parseDouble(lng);
                        }
                        catch (NumberFormatException e)

                        {
                            latD = 34.1279630000000;
                            lngD = 74.83651700000;
                            e.printStackTrace();
                        }
                        pg.dismiss();
                        Intent intent = new Intent(DepartmentsActivity.this,MapsActivity.class);


                        intent.putExtra("lat",latD);
                        intent.putExtra("lng",lngD);
                        intent.putExtra("name",departmentname);
                        Log.e("TAdfsdfG", "between: " + latD+"   " + lngD + departmentname);
                        startActivity(intent);}
                    else
                    {
                        pg.dismiss();
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getApplicationContext());
                        builder.setMessage("Unavailable")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetEventLocationRequest getEventLocationRequest = new GetEventLocationRequest(departmentname, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DepartmentsActivity.this);
        queue.add(getEventLocationRequest);
        Log.e("TAdfsdfG", "beforeintent: " + lat + lng + departmentname);




    }
}
