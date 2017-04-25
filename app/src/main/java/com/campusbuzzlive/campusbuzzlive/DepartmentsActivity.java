package com.campusbuzzlive.campusbuzzlive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);
        schoolId = getIntent().getStringExtra("id");
        schoolName=    getIntent().getStringExtra("name");

        TextView tvSchoolname =(TextView)findViewById(R.id.schoolName);
        tvSchoolname.setText(schoolName);
        schoolList=new ArrayList<HashMap<String, String>> ();
        ListView listView = (ListView) findViewById(R.id.lvSchool) ;
        arrayAdapter= new ArrayAdapter<HashMap<String, String>>(this,R.layout.departments_design,R.id.tvDname){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View  v = getLayoutInflater().inflate(R.layout.departments_design,parent,false);
                TextView tvDname = (TextView) v.findViewById(R.id.tvDname);
                TextView tvHOD = (TextView) v. findViewById(R.id.tvHODname);
                TextView tvPhone = (TextView) v.findViewById(R.id.tvPhone);
                TextView tvEmail = (TextView) v. findViewById(R.id.tvMail);
                HashMap<String, String> item=getItem(position);
                tvDname.setText(item.get("DName"));
                tvHOD.setText(item.get("HODname"));
                tvPhone.setText(item.get("phone"));
                tvEmail.setText(item.get("email"));
                return v;
            }
        } ;

        listView.setAdapter(arrayAdapter);

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
                        String locationid = c.getString("locationid");


                        HashMap<String,String> schoolMap = new HashMap<String,String>();

                        schoolMap.put("Dname",Dname);
                        schoolMap.put("HODname",HODname);
                        schoolMap.put("phone",phone);
                        schoolMap.put("email",email);
                        schoolMap.put("locationid",locationid);




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


    }
}
