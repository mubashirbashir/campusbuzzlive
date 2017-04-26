package com.campusbuzzlive.campusbuzzlive;

import android.app.Notification;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Guestmode extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestmode);
        tv = (TextView) this.findViewById(R.id.mywidget);
        tv.setSelected(true);
    }

    public void add(View v)

    {

        if (v.getId() == R.id.blogin) {
            Intent blog = new Intent(Guestmode.this, LoginActivity.class);

            startActivity(blog);
        }
        if (v.getId() == R.id.bSignup) {
            Intent sup = new Intent(Guestmode.this, SignupActivity.class);
            startActivity(sup);

        }

        if (v.getId() == R.id.navb) {
            Intent abc = new Intent(Guestmode.this, MapsListActivity.class);

        startActivity(abc);
    }
        if (v.getId() == R.id.notb) {
            Intent np = new Intent(Guestmode.this, GuestNotification.class);
            startActivity(np);

        }
        if (v.getId() == R.id.backb) {
           onBackPressed();
        }
        if (v.getId() == R.id.imageButton9) {
            Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_LONG).show();
        }
        if (v.getId() == R.id.imageButton8) {
            Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_LONG).show();
        }
        if (v.getId() == R.id.imageButton7) {
            Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_LONG).show();
        }
    }
}
