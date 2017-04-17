package com.campusbuzzlive.campusbuzzlive;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import static android.view.Gravity.CENTER;
import static android.view.Gravity.LEFT;
import static android.view.Gravity.START;
import static com.campusbuzzlive.campusbuzzlive.R.color.buzzcolor;

public class MapsListActivity extends AppCompatActivity implements View.OnClickListener {
    final Context context= this;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    HashMap<String,String> persons = new HashMap<String,String>();
     String dynammicCatogoriesText;
    int index;
    ImageView ivBack;

    LinearLayout linearLayout,linearLayout11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps_list);
        personList = new ArrayList<HashMap<String,String>>();
//       final ProgressDialog pgDialog=new ProgressDialog(this);
//        pgDialog.setTitle("loading...");
//        pgDialog.show();
        ivBack=(ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
       final ProgressDialog pb =new ProgressDialog(this);
        pb.setCanceledOnTouchOutside(false);

        pb.setTitle("Loading...");
        pb.setMessage("Please Wait");
        //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pb.getLayoutParams();
      //  params.
        //params.setMargins(10, 10,10, 10); //substitute parameters for left, top, right, bottom
      //  pb.setLayoutParams(params);
       pb.show();


       //HashMap<String,String> persons = new HashMap<String,String>();

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);


                    peoples = jsonResponse.getJSONArray("locations");
                    for(int i=0;i<peoples.length();i++) {
                        JSONObject c = peoples.getJSONObject(i);
                        String id = c.getString("categories");
                        HashMap<String,String> persons = new HashMap<String,String>();

                        persons.put("categories",id);



                        personList.add(persons);


                    }
                  //  pgDialog.dismiss();
                    pb.dismiss();


                    for (int i=0;i<personList.size();i++){
                   final      Button tvDyanamicCatogories = new Button(context);
                        String locationtext = (personList.get(i).get("categories"));
                        tvDyanamicCatogories.setText(locationtext);
                        tvDyanamicCatogories.setBackgroundResource(R.drawable.transparent_button);
                        tvDyanamicCatogories.setTextSize(19);


                        //tvDyanamicCatogories.setBackgroundResource(R.color.buzzcolor);
                        tvDyanamicCatogories.setPadding(10,10,10,10);
                        tvDyanamicCatogories.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        tvDyanamicCatogories.setGravity(CENTER);


                     //   tvDyanamicCatogories.setTypeface(Typeface.DEFAULT_BOLD);


                    // tvDyanamicCatogories.setInputType();
                       tvDyanamicCatogories.setAllCaps(false);
                        tvDyanamicCatogories.setTextColor(getResources().getColor(R.color.buzzcolor));
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvDyanamicCatogories.getLayoutParams();
                        params.setMargins(10, 10,10, 10); //substitute parameters for left, top, right, bottom
                        tvDyanamicCatogories.setLayoutParams(params);
                        linearLayout.addView(tvDyanamicCatogories);
                        tvDyanamicCatogories.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // linearLayout=(LinearLayout)findViewById(R.id.linearLayout);

                                final ProgressBar pb =new ProgressBar(context);
                                pb.setVisibility(View.VISIBLE);
                                linearLayout.addView(pb);
                                dynammicCatogoriesText = tvDyanamicCatogories.getText().toString();
                                Toast.makeText(context,dynammicCatogoriesText,Toast.LENGTH_LONG).show();

                                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                Response.Listener<String> responseListener1 = new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                        //    boolean error = jsonResponse.getBoolean("error");

                                          //  if (!error) {



                                              //  String msg =jsonResponse.getString("error_msg");



                                         //  peoples.;
                                               personList.clear();


                                            peoples = jsonResponse.getJSONArray("coordinates");
                                           for(int i=0;i<peoples.length();i++) {
                                                JSONObject c = peoples.getJSONObject(i);
                                               String names = c.getString("name");
                                                String lats = c.getString("lat");
                                                String lngs = c.getString("lng");
                                                HashMap<String,String> persons = new HashMap<String,String>();

                                                persons.put("name",names);
                                                persons.put("lat",lats);
                                                persons.put("lng",lngs);

                                                personList.add(persons);

//                                               AlertDialog.Builder builder = new AlertDialog.Builder(MapsListActivity.this);
//                                               builder.setMessage("cdf"+names+" DCD"+lats+"dssad"+lngs+" fd f")
//                                                       .setNegativeButton("Retry", null)
//                                                       .create()
//                                                       .show();


                                           }
                                          //  } else {
                                              //  String msg =jsonResponse.getString("error_msg");
                                             //   AlertDialog.Builder builder = new AlertDialog.Builder(MapsListActivity.this);
                                              //  builder.setMessage("msgelse")
                                              //          .setNegativeButton("Retry", null)
                                             //           .create()
                                            //            .show();

                                           // }
                                            //  pgDialog.dismiss();

                                            pb.setVisibility(View.INVISIBLE);

                                            final  Dialog dialog1 = new Dialog(context);
                                            dialog1.setContentView(R.layout.locations);
                                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            linearLayout=(LinearLayout)dialog1.findViewById(R.id.linearLayout11);
                                            for (int i=0;i<personList.size();i++){
                                                final Button dyanamicCatogories = new Button(dialog1.getContext());
                                                String text = personList.get(i).get("name");
                                                dyanamicCatogories.setText(text);
                                                final String abc = dyanamicCatogories.getText().toString();
                                                dyanamicCatogories.setBackgroundResource(R.drawable.transparent_button);
                                                dyanamicCatogories.setTextSize(17);
                                                dialog1.setCanceledOnTouchOutside(false);

                                                // dyanamicCatogories.setBackgroundResource(R.color.buzzcolor);
                                                //  dyanamicCatogories.setPadding(10,10,10,10);
                                                dyanamicCatogories.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                                                dyanamicCatogories.setGravity(CENTER);

                                               // dyanamicCatogories.setTypeface(Typeface.DEFAULT_BOLD);
                                                dyanamicCatogories.setAllCaps(false);
                                                dyanamicCatogories.setId(i);

                                                dyanamicCatogories.setTextColor(getResources().getColor(R.color.white));
                                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dyanamicCatogories.getLayoutParams();
                                                params.setMargins(20, 0,20, 0); //substitute parameters for left, top, right, bottom
                                                dyanamicCatogories.setLayoutParams(params);
                                                linearLayout.addView(dyanamicCatogories);
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                dyanamicCatogories.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        index=dyanamicCatogories.getId();
                                                        double lat = Double.parseDouble(personList.get(index).get("lat"));

                                                        double lng = Double.parseDouble(personList.get(index).get("lng"));;
                                                        Toast.makeText(context,abc,Toast.LENGTH_SHORT).show();
                                                        Intent showMap = new Intent(getApplicationContext(),MapsActivity.class);
                                                        showMap.putExtra("lat",lat);
                                                        showMap.putExtra("lng",lng);
                                                        showMap.putExtra("name",abc);
                                                        startActivity(showMap);
                                                    }
                                                });
                                            }




                                            dialog1.show();


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };



                                CoordinatesRequest coordinatesRequest = new CoordinatesRequest(dynammicCatogoriesText, responseListener1);
                                RequestQueue queue1 = Volley.newRequestQueue(MapsListActivity.this);
                                queue1.add(coordinatesRequest);



                                //


                            }
                        });
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        LocationRequest locationRequest = new LocationRequest( responseListener);
        RequestQueue queue = Volley.newRequestQueue(MapsListActivity.this);
        queue.add(locationRequest);










    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
