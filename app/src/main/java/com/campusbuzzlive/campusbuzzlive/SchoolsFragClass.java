package com.campusbuzzlive.campusbuzzlive;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mubas on 4/26/2017.
 */

public class SchoolsFragClass extends Fragment {

    ArrayAdapter<HashMap<String, String>> arrayAdapter;
    JSONArray schoolArray = null;
    ArrayList<HashMap<String, String>> schoolList;
    HashMap<String, String> schoolMap = new HashMap<String, String>();
    ProgressBar progreebar;

    RequestQueue queue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        View rt =inflater.inflate(
                R.layout.activity_schools_class, container, false);


        schoolList=new ArrayList<HashMap<String, String>> ();
        final ListView listView = (ListView) rt.findViewById(R.id.lvSchool) ;
        arrayAdapter= new ArrayAdapter<HashMap<String, String>>(getContext(),R.layout.school_design,R.id.tvSchool){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View  v = getActivity().getLayoutInflater().inflate(R.layout.school_design,parent,false);
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
                Intent in=new Intent(getActivity(),DepartmentsActivity.class);
                in.putExtra("name",item.get("schoolname"));
                in.putExtra("id",item.get("schoolid"));
                startActivity(in);


            }
        });





        listView.setAdapter(arrayAdapter);
        progreebar = (ProgressBar)rt.findViewById(R.id.pgbar);
        progreebar.setVisibility(View.VISIBLE);



        refreshitems();
     //   progreebar.setVisibility(View.INVISIBLE);










        return rt;
    }

    private void refreshitems() {
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


                 progreebar.setVisibility(View.INVISIBLE);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                progreebar.setVisibility(View.INVISIBLE);


                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle("No response");
                alert.setMessage("Please check your internet connection");

                alert.setNegativeButton("Ok",null);
                alert.show();


            }
        };
        GetSchoolsRequest getSchoolsRequest = new GetSchoolsRequest(  responseListener,errorListener);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(getSchoolsRequest);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Schools");
    }
    @Override
    public void onStop() {
        queue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        super.onStop();
    }
}

