package com.campusbuzzlive.campusbuzzlive;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SettingsActivity extends AppCompatActivity {
    ImageButton ibtheme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ibtheme= (ImageButton) findViewById(R.id.ibTheme);
        ibtheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final Dialog dialog=new Dialog(getApplicationContext());
                dialog.setContentView(R.layout.dialog_theme);
               ImageButton imagebutton1= (ImageButton) dialog.findViewById(R.id.imageButton1);
                ImageButton imagebutton2= (ImageButton) dialog.findViewById(R.id.imageButton2);
                ImageButton imagebutton3= (ImageButton) dialog.findViewById(R.id.imageButton3);
                ImageButton imagebutton4= (ImageButton) dialog.findViewById(R.id.imageButton4);
                ImageButton imagebutton5= (ImageButton) dialog.findViewById(R.id.imageButton5);
                ImageButton imagebutton6= (ImageButton) dialog.findViewById(R.id.imageButton6);

                imagebutton1.setOnClickListener(this);
                imagebutton2.setOnClickListener(this);
                imagebutton3.setOnClickListener(this);
                imagebutton4.setOnClickListener(this);
                imagebutton5.setOnClickListener(this);
                imagebutton6.setOnClickListener(this);


            }


        });


    }


}

