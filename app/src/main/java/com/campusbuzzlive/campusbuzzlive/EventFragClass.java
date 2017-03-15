package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/6/2017.
 */


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

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

import java.util.Calendar;


public class EventFragClass extends Fragment {
    int i = 0;
    Button bAdd;
    ImageButton bDate, bTime, bLocation;
    TextView tvDate, tvTime, tvLocation;
    EditText etEvent;
    String stringTime, stringDate;
    boolean setDate, setTime; // ad lo alater
    private LinearLayout linearLayout = null;
    private int mYear, mMonth, mDay, mHour, mMinute;


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
                                        stringDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                        tvDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
                                        stringTime = hourOfDay + ":" + minute;


                                        tvTime.setText(hourOfDay + ":" + minute);
                                        setTime = true;
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }

                });
                bLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "add code..", Toast.LENGTH_LONG).show();

                    }
                });


                bAdd.setEnabled(true);
                bAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (setTime && setDate && !etEvent.getText().toString().matches("")) {
                            TextView tvDynamicEvent = new TextView(getActivity());
                            TextView tvDynamicEtc = new TextView(getActivity());
                            View vDynamicLine = new View(getActivity());
                            tvDynamicEvent.setText(etEvent.getText());
                            tvDynamicEvent.setTextSize(24);
                            tvDynamicEvent.setGravity(Gravity.CENTER);
                            tvDynamicEvent.setTextColor(getResources().getColor(R.color.buzzcolor));
                            tvDynamicEtc.setTextSize(20);
                            tvDynamicEtc.setGravity(Gravity.CENTER);
                            tvDynamicEtc.setId(i++);
                            vDynamicLine.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            vDynamicLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                            tvDynamicEvent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            tvDynamicEtc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vDynamicLine.getLayoutParams();
                            params.setMargins(40, 40, 40, 40); //substitute parameters for left, top, right, bottom
                            vDynamicLine.setLayoutParams(params);
                            tvDynamicEtc.setText(stringDate + "  " + stringTime + "  at" + " " + tvDynamicEtc.getId());
                            linearLayout.addView(tvDynamicEvent, 0);
                            linearLayout.addView(tvDynamicEtc, 1);
                            linearLayout.addView(vDynamicLine, 2);

                            Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}