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
import java.util.Date;
import java.util.HashMap;

public class SchoolsClass extends AppCompatActivity {
    ArrayAdapter<HashMap<String, String>>arrayAdapter;
    JSONArray schoolArray = null;
    ArrayList<HashMap<String, String>> schoolList;
    HashMap<String, String> schoolMap = new HashMap<String, String>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools_class);
        schoolList=new ArrayList<HashMap<String, String>> ();
        ListView listView = (ListView) findViewById(R.id.lvSchool) ;
arrayAdapter= new ArrayAdapter<HashMap<String, String>>(this,R.layout.school_design,R.id.tvSchool){
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View  v = getLayoutInflater().inflate(R.layout.school_design,parent,false);
        TextView tvDean = (TextView) v.findViewById(R.id.tvDean);
        TextView tvSchool = (TextView) v. findViewById(R.id.tvSchool);
        HashMap<String, String> item=getItem(position);
        tvDean.setText(item.get("dean"));
        tvSchool.setText(item.get("schoolname"));
      return v;
    }
} ;
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String,String> item=arrayAdapter.getItem(position);
        Intent in=new Intent(getApplicationContext(),DepartmentsActivity.class);
        in.putExtra("name",item.get("schoolname"));
        in.putExtra("id",item.get("schoolid"));
        startActivity(in);


    }
});

listView.setAdapter(arrayAdapter);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    //  boolean error = jsonResponse.getBoolean("error");

                    schoolArray= jsonResponse.getJSONArray("schools");




                    for(int i=0;i<schoolArray.length();i++) {
                        JSONObject c = schoolArray.getJSONObject(i);
                        String deanName = c.getString("dean");


                        String schoolName = c.getString("schoolname");
                        String schoolid = c.getString("schoolid");



                        HashMap<String,String> schoolMap = new HashMap<String,String>();

                        schoolMap.put("dean",deanName);
                        schoolMap.put("schoolname",schoolName);
                        schoolMap.put("schoolid",schoolid);




                        schoolList.add(schoolMap);


                    }



arrayAdapter.addAll(schoolList);





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetSchoolsRequest getSchoolsRequest = new GetSchoolsRequest(  responseListener);
        queue = Volley.newRequestQueue(SchoolsClass.this);
        queue.add(getSchoolsRequest);


    }
}
