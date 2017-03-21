package com.campusbuzzlive.campusbuzzlive;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.campusbuzzlive.campusbuzzlive.R.color.buzzcolor;

public class MapsListActivity extends AppCompatActivity {
    final Context context= this;
LinearLayout linearLayout,linearLayout11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_list);

        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        for (int i=0;i<20;i++){
      Button tvDyanamicCatogories = new Button(this);
        tvDyanamicCatogories.setText("abc "+i);
        tvDyanamicCatogories.setBackgroundResource(R.drawable.transparent_button);
        tvDyanamicCatogories.setTextSize(24);

       //tvDyanamicCatogories.setBackgroundResource(R.color.buzzcolor);
        tvDyanamicCatogories.setPadding(10,10,10,10);
            tvDyanamicCatogories.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        tvDyanamicCatogories.setGravity(Gravity.CENTER);

        tvDyanamicCatogories.setTypeface(Typeface.DEFAULT_BOLD);
        tvDyanamicCatogories.setAllCaps(true);
        tvDyanamicCatogories.setTextColor(getResources().getColor(R.color.buzzcolor));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvDyanamicCatogories.getLayoutParams();
            params.setMargins(20, 20,20, 20); //substitute parameters for left, top, right, bottom
            tvDyanamicCatogories.setLayoutParams(params);
        linearLayout.addView(tvDyanamicCatogories);
            tvDyanamicCatogories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // linearLayout=(LinearLayout)findViewById(R.id.linearLayout);

                    final  Dialog dialog1 = new Dialog(context);
                    dialog1.setContentView(R.layout.locations);

                       linearLayout=(LinearLayout)dialog1.findViewById(R.id.linearLayout11);
                     for (int i=0;i<20;i++){
                        Button dyanamicCatogories = new Button(dialog1.getContext());
                         dyanamicCatogories.setText("abc "+i);
                        final String abc = dyanamicCatogories.getText().toString();
                         dyanamicCatogories.setBackgroundResource(R.drawable.transparent_button);
                         dyanamicCatogories.setTextSize(24);

                       // dyanamicCatogories.setBackgroundResource(R.color.buzzcolor);
                      //  dyanamicCatogories.setPadding(10,10,10,10);
                         dyanamicCatogories.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                         dyanamicCatogories.setGravity(Gravity.CENTER);

                         dyanamicCatogories.setTypeface(Typeface.DEFAULT_BOLD);
                         dyanamicCatogories.setAllCaps(true);
                         dyanamicCatogories.setTextColor(getResources().getColor(R.color.buzzcolor));
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dyanamicCatogories.getLayoutParams();
                        params.setMargins(20, 0,20, 0); //substitute parameters for left, top, right, bottom
                         dyanamicCatogories.setLayoutParams(params);
                        linearLayout.addView(dyanamicCatogories);
                         dyanamicCatogories.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 double lat = 34.063751;

                                 double lng = 74.809215;
                                 Toast.makeText(context,abc,Toast.LENGTH_SHORT).show();
                                 Intent showMap = new Intent(getApplicationContext(),MapsActivity.class);
                                 showMap.putExtra("lat",lat);
                                 showMap.putExtra("lng",lng);
                                 startActivity(showMap);
                             }
                         });
                     }




                    dialog1.show();

                }
            });
        }




    }
}
