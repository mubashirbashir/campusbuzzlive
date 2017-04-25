package com.campusbuzzlive.campusbuzzlive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class splashScreen extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        final Boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn) {
                    Intent intent = new Intent(splashScreen.this, SchoolsClass.class);
                    splashScreen.this.startActivity(intent);
                    splashScreen.this.finish();
                }
                else {
                    Intent intent = new Intent(splashScreen.this, MainActivity.class);
                    splashScreen.this.startActivity(intent);
                    splashScreen.this.finish();
                }
            }        },3000);
    }
}
