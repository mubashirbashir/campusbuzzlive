package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/6/2017.
 */


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class EventFragClass extends Fragment {
    int i = 0;
    Button bAdd;
    JSONArray eventArray = null;
    ArrayList<HashMap<String, String>> eventList;
    HashMap<String,String> eventMap = new HashMap<String,String>();
    Session session;
    host h =new host();


    ImageButton bDate, bTime, bLocation;
    TextView tvDate, tvTime, tvLocation;
    EditText etEvent;
    String stringTime, stringDate , locText,location,eventName;
    boolean setDate, setTime; // ad lo alater
    private LinearLayout linearLayout = null;
   // private LinearLayout linearLayoutin = null;
    private int mYear, mMonth, mDay, mHour, mMinute;
    SwipeRefreshLayout   mSwipeRefreshLayout;
    RequestQueue queue;




    public EventFragClass() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        //Inflate the layout for this fragment

        View rt = inflater.inflate(
                R.layout.event_frag_layout, container, false);
        getActivity().setTitle("Events");
        session = new Session();



        linearLayout = (LinearLayout) rt.findViewById(R.id.linearLayout);
       // linearLayoutin=(LinearLayout)rt.findViewById(R.id.linearLayoutin);

        FloatingActionButton fab = (FloatingActionButton) rt.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //Create your Controls(UI widget, Button,TextView) and add into layout
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.event_dialog_layout);
                bAdd = (Button) dialog.findViewById(R.id.bAdd);
                bDate = (ImageButton) dialog.findViewById(R.id.bDate);
                tvDate = (TextView) dialog.findViewById(R.id.tvDate);
                bTime = (ImageButton) dialog.findViewById(R.id.bTime);
                tvTime = (TextView) dialog.findViewById(R.id.tvTime);
                bLocation = (ImageButton) dialog.findViewById(R.id.bLoc);
                tvLocation = (TextView) dialog.findViewById(R.id.tvloc);
                etEvent = (EditText) dialog.findViewById(R.id.etEvent);


                dialog.show();


                bDate.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        stringDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

                                        tvDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                                        setDate = true;

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }

                });


                bTime.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // Get Current Time
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        stringTime = hourOfDay + ":" + minute+":00";


                                        tvTime.setText(hourOfDay + ":" + minute+":00");
                                        setTime = true;
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }

                });
                bLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        final  EditText etLocations = new EditText(getContext());
                        etLocations.setSingleLine(true);
                        etLocations.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

                        alertDialog.setView(etLocations);
                        alertDialog.setTitle("Enter location name");

                        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              locText= String.valueOf(etLocations.getText());
                                tvLocation.setText(locText);



                            }
                        });
                       // Toast.makeText(getContext(), "add code..", Toast.LENGTH_LONG).show();
                        alertDialog.show();

                    }
                });


                bAdd.setEnabled(true);
                bAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (setTime && setDate && !etEvent.getText().toString().matches("") && !locText.matches("")) {
                            eventName =etEvent.getText().toString();
                            location =tvLocation.getText().toString();

                            // add event db here

                          final  ProgressDialog progressDialog = new ProgressDialog(getContext());
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.setTitle("Loading...");
                            progressDialog.setMessage("Please wait!");
                            progressDialog.show();
                                                       Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean error = jsonResponse.getBoolean("error");
                                        if (!error) {
                                            progressDialog.dismiss();
                                            // Toast.makeText(getApplicationContext(),"Registration Successfull.Please Log In to continue.",Toast.LENGTH_LONG).show();
                                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                                            builder.setMessage(" Event Added Successfully!")
                                                    .setNegativeButton("ok", null)
                                                    .create()
                                                    .show();
                                            refreshItems();

                                            //   Intent intent = new Intent(SignupActivity.this, LoginActivity.class);


                                            //  intent.putExtra("enrollmentid",enrollmentid);
                                            //    SignupActivity.this.startActivity(intent);
                                        } else {
                                            String msg= jsonResponse.getString("error_msg");
                                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
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

                            AddEventRequest addEventRequestRequest = new AddEventRequest(eventName, stringDate, stringTime, location, session.getEnrollSession(),  responseListener);
                            queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                            queue.add(addEventRequestRequest);





                            //

                            setTime = false;
                            setDate = false;
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Fields are Empty..", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

        });
        return rt;
    }

    private void refreshItems() {
        linearLayout.removeAllViews();

    eventList = new ArrayList<HashMap<String,String>>();
    linearLayout=(LinearLayout)getActivity().findViewById(R.id.linearLayout);

    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                //  boolean error = jsonResponse.getBoolean("error");

                eventArray= jsonResponse.getJSONArray("events");




                for(int i=0;i<eventArray.length();i++) {
                    JSONObject c = eventArray.getJSONObject(i);
                    String userName = c.getString("name");


                    String eventName = c.getString("eventname");

                    String date = c.getString("date");

                    String time = c.getString("time");

                    String location = c.getString("location");

                    String enrollmentid = c.getString("enrollmentid");
                    String photo =c.getString("photo");

                    HashMap<String,String> eventMap = new HashMap<String,String>();

                    eventMap.put("name",userName);
                    eventMap.put("eventname",eventName);
                    eventMap.put("date",date);
                    eventMap.put("time",time);
                    eventMap.put("location",location);
                    eventMap.put("enrollmentid",enrollmentid);
                    eventMap.put("photo",photo);




                    eventList.add(eventMap);


                }
                mSwipeRefreshLayout.setRefreshing(false);

        display();








    } catch (JSONException e) {
        e.printStackTrace();
    }
}
    };

            GetEventRequest getEventRequest = new GetEventRequest(  responseListener);
             queue = Volley.newRequestQueue(getContext());
            queue.add(getEventRequest);

            // refresf from db



            Toast.makeText(getContext(),"Refreshing...",Toast.LENGTH_SHORT).show();
            }

private void display() {

        for (int i=0;i<eventList.size();i++)
        {
            LinearLayout ll =new LinearLayout(getActivity());
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout ln =new LinearLayout(getActivity());
            ln.setOrientation(LinearLayout.VERTICAL);
            ln.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));




        //display items

        TextView tvDynamicEvent = new TextView(getActivity());
        TextView tvDynamicEtc = new TextView(getActivity());
        TextView tvDynamicName = new TextView(getActivity());
        TextView tvDynamicEnroll = new TextView(getActivity());
        View vDynamicLine = new View(getActivity());
            final ImageView dynamicDelete = new ImageView(getActivity());

            ImageView imageViewdp= new ImageView(getActivity());
            LinearLayout.LayoutParams paramx =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramx.setMargins(0, 0, 10, 0); //substitute parameters for left, top, right, bottom
            imageViewdp.setLayoutParams(paramx);

            String photoURL = (h.address+"/uploads/"+eventList.get(i).get("photo"));

            Picasso.with(getContext())
                    .load(photoURL)

                    .placeholder(R.mipmap.userdummy)   // optional
                    .error(R.mipmap.userdummy)      // optional
                    .resize(150,150)                        // optional
                    .into(imageViewdp);

        //
        String nameText = (eventList.get(i).get("name"));
        String eventNameText = (eventList.get(i).get("eventname"));
        String dateText = (eventList.get(i).get("date"));
        String timeText = (eventList.get(i).get("time"));
        String locationText = (eventList.get(i).get("location"));
        String enrollmentText = (eventList.get(i).get("enrollmentid"));
        //
        tvDynamicEvent.setText(eventNameText);
        tvDynamicEvent.setTextSize(24);

        tvDynamicName.setTextSize(16);
        tvDynamicName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvDynamicName.setText(nameText);
            tvDynamicName.setTextColor(getResources().getColor(R.color.black));
        tvDynamicEnroll.setText(enrollmentText);
        tvDynamicEnroll.setTextSize(14);
        //  tvDynamicEvent.setGravity(Gravity.LEFT);
        tvDynamicEvent.setTextColor(getResources().getColor(R.color.buzzcolor));
        tvDynamicEtc.setTextSize(16);
        tvDynamicEtc.setGravity(Gravity.RIGHT);
        // tvDynamicEtc.setId(i++);
        vDynamicLine.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        vDynamicLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
        tvDynamicEvent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvDynamicEtc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vDynamicLine.getLayoutParams();
        params.setMargins(0, 10, 0, 10); //substitute parameters for left, top, right, bottom
        vDynamicLine.setLayoutParams(params);


            tvDynamicName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tvDynamicEnroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));


        tvDynamicEtc.setText(dateText + "  " + timeText + "  at" + " " + locationText);

            if (session.getEnrollSession().toString().equals(tvDynamicEnroll.getText().toString())) {
                // Toast.makeText(this,session.getEnrollSession(),Toast.LENGTH_LONG).show();
                dynamicDelete.setImageResource(R.drawable.ic_more_vert_black_24dp);

                dynamicDelete.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) dynamicDelete.getLayoutParams();
                params1.gravity = (Gravity.RIGHT);
                dynamicDelete.setLayoutParams(params1);
                linearLayout.addView(dynamicDelete);
            }

            linearLayout.addView(ll);
            ll.addView(imageViewdp);
            ll.addView(ln);
            ln.addView(tvDynamicName);
            ln.addView(tvDynamicEnroll);





            linearLayout.addView(tvDynamicEvent);
        linearLayout.addView(tvDynamicEtc);
        linearLayout.addView(vDynamicLine);






dynamicDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        PopupMenu menu = new PopupMenu(getContext(), v);

        menu.getMenu().add(Menu.NONE,1,1,"Delete");

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        menu.show();

    }
});


        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout=(SwipeRefreshLayout) getActivity().findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        refreshItems();


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