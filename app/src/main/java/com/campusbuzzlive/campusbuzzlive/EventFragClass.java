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
import android.drm.DrmManagerClient;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class EventFragClass extends Fragment {
    int i = 0;
    Button bAdd;
     Double latD,lngD;
    JSONArray eventArray = null;
    ArrayList<HashMap<String, String>> eventList;
    HashMap<String,String> eventMap = new HashMap<String,String>();
    Session session;
    host h =new host();
     String lat,lng;
   final Context context=getContext();
    ScrollView scrollView;


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
    JSONArray nameArray = null;
    ArrayList<HashMap<String, String>> nameList;
    HashMap<String,String> nameMap = new HashMap<String,String>();




    public EventFragClass() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater,
                             @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {


        //Inflate the layout for this fragment

        View rt = inflater.inflate(
                R.layout.event_frag_layout, container, false);
        getActivity().setTitle("Events");
        session = new Session();
        scrollView =(ScrollView) rt.findViewById(R.id.eventScrollView);


                linearLayout = (LinearLayout) rt.findViewById(R.id.linearLayout);
       // linearLayoutin=(LinearLayout)rt.findViewById(R.id.linearLayoutin);
       // scrollView.

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
                         final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        final LinearLayout ln = new LinearLayout(getContext());
                        ln.setOrientation(LinearLayout.VERTICAL);


                        final LinearLayout ll = new LinearLayout(getContext());
                        ll.setOrientation(LinearLayout.VERTICAL);
                        final ScrollView sc =new ScrollView(getContext());
                        sc.addView(ll);

                        final  EditText etLocations = new EditText(getContext());

                        etLocations.setSingleLine(true);
                        etLocations.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                        ln.addView(etLocations);
                        ln.addView(sc);



                        alertDialog.setTitle("Enter location name");
                        etLocations.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {



                                nameList = new ArrayList<HashMap<String,String>>();


                             //Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                           boolean error = jsonResponse.getBoolean("error");
                                            if (!error) {

                                                nameArray= jsonResponse.getJSONArray("suggestions");




                                                for(int i=0;i<nameArray.length();i++) {
                                                    JSONObject c = nameArray.getJSONObject(i);
                                                    String userName = c.getString("name");


                                                    HashMap<String,String> nameMap = new HashMap<String,String>();

                                                    nameMap.put("name",userName);




                                                    nameList.add(nameMap);


                                                }
                                                ll.removeAllViews();
                                                int id=0;

                                                for (int i=0;i<nameList.size();i++) {

                                                    final TextView tvDynamicName = new TextView(getActivity());
                                                    id=tvDynamicName.getId();


                                                    //

                                                    String nameText = (nameList.get(i).get("name"));

                                                    tvDynamicName.setTextSize(16);
                                                    tvDynamicName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                                                    tvDynamicName.setText(nameText);
                                                    tvDynamicName.setTextColor(getResources().getColor(R.color.black));

                                                    tvDynamicName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                                                    tvDynamicName.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            etLocations.setText(tvDynamicName.getText().toString());
                                                        }
                                                    });

                                                    ll.addView(tvDynamicName);


                                                }












                                                } else {
                                                String msg= jsonResponse.getString("error_msg");
                                                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                                                builder.setMessage(msg)
                                                        .setNegativeButton("Retry", null)
                                                        .create()
                                                        .show();

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                String g= String.valueOf(s);
                                GetLocationSuggestionsRequest getLocationSuggestionsRequest = new GetLocationSuggestionsRequest(g,  responseListener);
                          RequestQueue      queue1 = Volley.newRequestQueue(getActivity().getApplicationContext());
                                queue1.add(getLocationSuggestionsRequest);




                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        alertDialog.setView(ln);
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
                                            refreshItems();
                                            progressDialog.dismiss();
                                            // Toast.makeText(getApplicationContext(),"Registration Successfull.Please Log In to continue.",Toast.LENGTH_LONG).show();
                                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                                            builder.setMessage(" Event Added Successfully!")
                                                    .setNegativeButton("ok", null)
                                                    .create()
                                                    .show();


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
                            Response.ErrorListener errorListener = new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(final VolleyError error) {





                                    progressDialog.dismiss();

                                    final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                                    alert.setTitle("No response");
                                    alert.setMessage("Please check your internet connection");

                                    alert.setNegativeButton("Ok",null);
                                    alert.show();


                                }
                            };

                            AddEventRequest addEventRequestRequest = new AddEventRequest(eventName, stringDate, stringTime, location, session.getEnrollSession(),  responseListener,errorListener);
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
                    String eventid = c.getString("eventid");

                    String date = c.getString("date");

                    String time = c.getString("time");

                    String location = c.getString("location");

                    String enrollmentid = c.getString("enrollmentid");
                    String photo =c.getString("photo");

                    HashMap<String,String> eventMap = new HashMap<String,String>();

                    eventMap.put("name",userName);
                    eventMap.put("eventid",eventid);
                    eventMap.put("eventname",eventName);
                    eventMap.put("date",date);
                    eventMap.put("time",time);
                    eventMap.put("location",location);
                    eventMap.put("enrollmentid",enrollmentid);
                    eventMap.put("photo",photo);




                    eventList.add(eventMap);


                }
                linearLayout.removeAllViews();
                mSwipeRefreshLayout.setRefreshing(false);

        display();








    } catch (JSONException e) {
        e.printStackTrace();
    }
}
    };
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                mSwipeRefreshLayout.setRefreshing(false);
                ImageView errorshow =new ImageView(getActivity());
                errorshow.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp);
                linearLayout.removeAllViews();




                linearLayout.addView(errorshow);

                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle("No response");
                alert.setMessage("Please check your internet connection");
                alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSwipeRefreshLayout.setRefreshing(true);

                        refreshItems();

                        linearLayout.removeAllViews();

                    }
                });
                alert.setNegativeButton("Cancel",null);
                alert.show();


            }
        };

            GetEventRequest getEventRequest = new GetEventRequest(  responseListener,errorListener);
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
            TextView tvDynamicloc = new TextView(getActivity());
        TextView tvDynamicEtc = new TextView(getActivity());
        TextView tvDynamicName = new TextView(getActivity());
      final  TextView tvDynamicEnroll = new TextView(getActivity());
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
            final String eventIdText = (eventList.get(i).get("eventid"));
        final String nameText = (eventList.get(i).get("name"));
        final String eventNameText = (eventList.get(i).get("eventname"));
        final String dateText = (eventList.get(i).get("date"));
        final String timeText = (eventList.get(i).get("time"));
        final String locationText = (eventList.get(i).get("location"));
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
            tvDynamicloc.setGravity(Gravity.RIGHT);
        // tvDynamicEtc.setId(i++);
        vDynamicLine.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        vDynamicLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
        tvDynamicEvent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvDynamicEtc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vDynamicLine.getLayoutParams();
        params.setMargins(0, 10, 0, 10); //substitute parameters for left, top, right, bottom
        vDynamicLine.setLayoutParams(params);



            tvDynamicName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tvDynamicEnroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));


        tvDynamicEtc.setText(dateText + "  " + timeText);
            tvDynamicloc.setText(locationText);
            tvDynamicEtc.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_access_time_black_24dp,0);
            tvDynamicloc.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_location_on_black_14dp,0);
            dynamicDelete.setImageResource(R.drawable.ic_more_vert_black_24dp);

            dynamicDelete.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) dynamicDelete.getLayoutParams();
            params1.gravity = (Gravity.RIGHT);
            dynamicDelete.setLayoutParams(params1);
            linearLayout.addView(dynamicDelete);



            linearLayout.addView(ll);
            ll.addView(imageViewdp);
            ll.addView(ln);
            ln.addView(tvDynamicName);
            ln.addView(tvDynamicEnroll);





            linearLayout.addView(tvDynamicEvent);
        linearLayout.addView(tvDynamicEtc);
            linearLayout.addView(tvDynamicloc);
        linearLayout.addView(vDynamicLine);






dynamicDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        PopupMenu menu = new PopupMenu(getContext(), v);

        menu.getMenu().add(Menu.NONE,1,1,"Get Directions");


        if (session.getEnrollSession().toString().equals(tvDynamicEnroll.getText().toString())) {
            // Toast.makeText(this,session.getEnrollSession(),Toast.LENGTH_LONG).show();


            menu.getMenu().add(Menu.NONE, 2, 2, "Delete");

        }

         menu.getMenu().add(Menu.NONE,3,3,"Add to Calender");


        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==1) {


                    final ProgressDialog pg = new
                            ProgressDialog(getContext());
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
                                    Log.e("TAdfsdfG", "minnn: " + lat + lng + locationText);



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
                                Intent intent = new Intent(getActivity(),MapsActivity.class);


                                intent.putExtra("lat",latD);
                                intent.putExtra("lng",lngD);
                                intent.putExtra("name",locationText);
                                Log.e("TAdfsdfG", "between: " + latD+"   " + lngD + locationText);
                                startActivity(intent);}
                                else
                                {
                                    pg.dismiss();
                                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
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

                    GetEventLocationRequest getEventLocationRequest = new GetEventLocationRequest(locationText, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    queue.add(getEventLocationRequest);
                    Log.e("TAdfsdfG", "beforeintent: " + lat + lng + locationText);

                }


                if(item.getItemId()==2) {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to delete this item? ")
                            .setIcon(R.drawable.ic_delete_black_24dp)

                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteEvent(eventIdText);
                                }
                            })
                            .show();
                }
                if(item.getItemId()==3)

                {

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
                    try {
                        cal.setTime(sdf.parse(dateText+" "+timeText));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.e("errr", "onMenuItemClick: " + cal.getTimeInMillis());
                  //  Date date = d

                    if (Build.VERSION.SDK_INT >= 14) {

                        Intent intent = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,cal.getTimeInMillis())
                             //   .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
                                .putExtra(CalendarContract.Events.TITLE,eventNameText)
                                .putExtra(CalendarContract.Events.DESCRIPTION, "Event added from Campus Buzz Live")
                                .putExtra(CalendarContract.Events.EVENT_LOCATION,"Venue: " +locationText)
                                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                                .putExtra(Intent.EXTRA_EMAIL, "campusbuzzlive@gmail.com");
                        startActivity(intent);
                    }

                    else {

                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra("beginTime", cal.getTimeInMillis());
                       intent.putExtra("allDay", true);
                        intent.putExtra("rrule", "FREQ=YEARLY");
                        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                        intent.putExtra("title", eventNameText);
                        startActivity(intent);
                    }


                }





                return true;
            }

        });
        menu.show();

    }
});


        }

    }

    private void deleteEvent(String eventIdText) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
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
                        refreshItems();
                        progressDialog.dismiss();
                        // Toast.makeText(getApplicationContext(),"Registration Successfull.Please Log In to continue.",Toast.LENGTH_LONG).show();
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                        builder.setMessage(" Successfully Deleted!")
                                .setNegativeButton("ok", null)
                                .create()
                                .show();


                    } else {
                        String msg = jsonResponse.getString("error_msg");
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


        DeleteEventRequest deleteEventRequest = new DeleteEventRequest(eventIdText, responseListener);
        queue = Volley.newRequestQueue(getContext());
        queue.add(deleteEventRequest);

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