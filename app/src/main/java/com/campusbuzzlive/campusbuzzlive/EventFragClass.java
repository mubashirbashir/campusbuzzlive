package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/6/2017.
 */

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
import android.widget.LinearLayout;
import android.widget.TextView;


public class EventFragClass extends Fragment {
    int i=0;
    private LinearLayout linearLayout = null;


    public EventFragClass() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        return inflater.inflate(
                R.layout.event_frag_layout, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Events");
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
       // Color c =  getResources().getColor( );


        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Create your Controls(UI widget, Button,TextView) and add into layout
                TextView btn = new TextView(getActivity());
                TextView btn1 = new TextView(getActivity());
                View btn3 = new View(getActivity());
                btn.setText("Annual Meeting  " + i);
                btn.setTextSize(24 );
                btn.setTextColor(getResources().getColor(R.color.buzzcolor));

                btn1.setText("satuday at pgdca   "+ i);
                btn1.setTextSize(20 );
                btn1.setId(i++);
                btn3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));






               // btn.gra(Gravity.CENTER | Gravity.TOP);
                btn3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));

                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                btn1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)btn3.getLayoutParams();
                params.setMargins(40, 40, 40, 40); //substitute parameters for left, top, right, bottom
                btn3.setLayoutParams(params);
                linearLayout.addView(btn,0);
                linearLayout.addView(btn1,1);
                linearLayout.addView(btn3,2);
            }
        });

    }
}