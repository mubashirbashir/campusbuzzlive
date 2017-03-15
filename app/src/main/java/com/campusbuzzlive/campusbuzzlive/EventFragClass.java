package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/6/2017.
 */

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Dialog;
import android.content.Context;
import android.widget.TimePicker;
import android.widget.Toast;


public class EventFragClass extends Fragment {
    int i = 0;
    private LinearLayout linearLayout = null;
    DatePicker picker;
    TimePicker TPicker;
    Button okD,okT,Add;
    ImageButton bDate,bTime;
   TextView date, time;
    EditText event ;








    boolean setdate,settime ; // ad lo alater


    public EventFragClass() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        View rt = inflater.inflate(
                R.layout.event_frag_layout, container, false);



        // Color c =  getResources().getColor( );
        getActivity().setTitle("Events");
        linearLayout=(LinearLayout)rt.findViewById(R.id.linearLayout);


        FloatingActionButton fab = (FloatingActionButton) rt.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

////Create your Controls(UI widget, Button,TextView) and add into layout


                final Dialog dialog = new Dialog(getContext());
                event =(EditText) dialog.findViewById(R.id.etEvent);

                dialog.setContentView(R.layout.event_dialog_layout);
                picker = (DatePicker) dialog.findViewById(R.id.datePicker1);
                okD = (Button) dialog.findViewById(R.id.bOkD);
                Add = (Button) dialog.findViewById(R.id.bAdd);
                bDate = (ImageButton) dialog.findViewById(R.id.bDate);
                date = (TextView) dialog.findViewById(R.id.tvDate);
                TPicker = (TimePicker) dialog.findViewById(R.id.TimePicker1);
                okT = (Button) dialog.findViewById(R.id.bOkT);
                bTime = (ImageButton) dialog.findViewById(R.id.bTime);
                time = (TextView) dialog.findViewById(R.id.tvTime);
                event =(EditText) dialog.findViewById(R.id.etEvent);

                dialog.setTitle("ADD Event");


             //   date.setText(getCurrentDate());

        bDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                okD.setVisibility(view.VISIBLE);
                picker.setVisibility(view.VISIBLE);
                date.setText(getCurrentDate());

            }

        });
        okD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setText(getCurrentDate());
                okD.setVisibility(view.INVISIBLE);
                picker.setVisibility(view.INVISIBLE);
                setdate=true;


            }
        });

                bTime.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        okT.setVisibility(view.VISIBLE);
                        TPicker.setVisibility(view.VISIBLE);

                        time.setText(getCurrentDate());

                    }

                });
                okT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time.setText(getCurrentTime());
                        okT.setVisibility(view.INVISIBLE);
                        TPicker.setVisibility(view.INVISIBLE);

                        settime=true;




                    }
                });


                    Add.setEnabled(true);
                Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(settime && setdate && !event.getText().equals(""))
                        {
                            TextView btn = new TextView(getActivity());
                            TextView btn1 = new TextView(getActivity());
                            View btn3 = new View(getActivity());
                            btn.setText(event.getText());
                            btn.setTextSize(24 );
                            btn.setTextColor(getResources().getColor(R.color.buzzcolor));

                            btn1.setText(getCurrentDate() +"   "+getCurrentTime());
                            btn1.setTextSize(20 );
                            btn1.setId(i++);
                            btn3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            btn3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));

                            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                            btn1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)btn3.getLayoutParams();
                            params.setMargins(40, 40, 40, 40); //substitute parameters for left, top, right, bottom
                            btn3.setLayoutParams(params);
                            linearLayout.addView(btn,0);
                            linearLayout.addView(btn1,1);
                            linearLayout.addView(btn3,2);

                        Toast.makeText(getContext()," Added", Toast.LENGTH_SHORT).show();
                            settime=false;
                            setdate=false;
                            dialog.dismiss();
                        }
                    }
                });


        dialog.show();


    }

});







        return rt;
}
    public String getCurrentDate() {
        StringBuilder builder = new StringBuilder();
       // builder.append("Current Date: ");
        builder.append((picker.getMonth() + 1) + "/");//month is 0 based
        builder.append(picker.getDayOfMonth() + "/");
        builder.append(picker.getYear());
        return builder.toString();
    }
    @SuppressLint("NewApi")
    public String getCurrentTime() {
        StringBuilder builder = new StringBuilder();
     //   builder.append("Current Time: ");
        builder.append((TPicker.getHour() ) + ":");//month is 0 based
        builder.append(TPicker.getMinute() );

        return builder.toString();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }
}