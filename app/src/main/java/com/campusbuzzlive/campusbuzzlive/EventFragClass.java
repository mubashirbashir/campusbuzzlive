package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/6/2017.
 */


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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

    ImageButton bDate, bTime, bLocation;
    TextView tvDate, tvTime, tvLocation;
    EditText etEvent;
    String stringTime, stringDate , locText,location,eventName;
    boolean setDate, setTime; // ad lo alater
    private LinearLayout linearLayout = null;
    private int mYear, mMonth, mDay, mHour, mMinute;
    SwipeRefreshLayout   mSwipeRefreshLayout;


    public EventFragClass() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        //Inflate the layout for this fragment

        View rt = inflater.inflate(
                R.layout.event_frag_layout, container, false);
        getActivity().setTitle("Events");
        linearLayout = (LinearLayout) rt.findViewById(R.id.linearLayout);

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

                dialog.setTitle("ADD Event");
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

                            AddEventRequest addEventRequestRequest = new AddEventRequest(eventName, stringDate, stringTime, location, "14045110035",  responseListener);
                            RequestQueue queue = Volley.newRequestQueue(getContext());
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
                    HashMap<String,String> eventMap = new HashMap<String,String>();

                    eventMap.put("name",userName);
                    eventMap.put("eventname",eventName);
                    eventMap.put("date",date);
                    eventMap.put("time",time);
                    eventMap.put("location",location);
                    eventMap.put("enrollmentid",enrollmentid);




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
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(getEventRequest);

            // refresf from db



            Toast.makeText(getContext(),"refreshing",Toast.LENGTH_LONG).show();
            }

private void display() {

        for (int i=0;i<eventList.size();i++)
        {


        //display items

        TextView tvDynamicEvent = new TextView(getActivity());
        TextView tvDynamicEtc = new TextView(getActivity());
        TextView tvDynamicName = new TextView(getActivity());
        TextView tvDynamicEnroll = new TextView(getActivity());
        View vDynamicLine = new View(getActivity());

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

        tvDynamicName.setTextSize(18);
        tvDynamicName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvDynamicName.setText(nameText);
        tvDynamicEnroll.setText(enrollmentText);
        tvDynamicEnroll.setTextSize(16);
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
        params.setMargins(40, 40, 40, 40); //substitute parameters for left, top, right, bottom
        vDynamicLine.setLayoutParams(params);
        tvDynamicEtc.setText(dateText + "  " + timeText + "  at" + " " + locationText);
        linearLayout.addView(tvDynamicName);
        linearLayout.addView(tvDynamicEnroll);
        linearLayout.addView(tvDynamicEvent);
        linearLayout.addView(tvDynamicEtc);
        linearLayout.addView(vDynamicLine);









        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshItems();
        mSwipeRefreshLayout=(SwipeRefreshLayout) getActivity().findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });


    }
}