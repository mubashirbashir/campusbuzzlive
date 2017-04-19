package com.campusbuzzlive.campusbuzzlive;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class RadioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String name,enrollmentid;
    SharedPreferences sharedPreferences;
    Session session = new Session();
    host h = new host();
    int curid;
    String photoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);







        name  =getIntent().getStringExtra("name");
        enrollmentid=getIntent().getStringExtra("enrollmentid");




        sharedPreferences=getSharedPreferences(LoginActivity.MyPreferences, Context.MODE_PRIVATE);
        String sessionEnroll =sharedPreferences.getString("sessionEnroll",null);
        String sessionName =sharedPreferences.getString("sessionName",null);
        session.setEnrollSession(sessionEnroll);
        photoURL=host.address+"/uploads/"+sessionEnroll+".jpeg";

        Toast.makeText(this,"Hi, "+sessionName,Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        TextView tvUsername  = (TextView) headerView.findViewById(R.id.tvUserName);
        TextView tvEnrollmentid  = (TextView) headerView.findViewById(R.id.tvEnroll);
        ImageView imageViewdp = (ImageView )headerView.findViewById(R.id.ivProfile);

        tvUsername.setText(sessionName);
        tvEnrollmentid.setText(sessionEnroll);
        Picasso.with(this)
                .load(photoURL)

                .placeholder(R.mipmap.userdummy)   // optional
                .error(R.mipmap.userdummy)      // optional
                .resize(150,150)                        // optional
                .into(imageViewdp);


        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_Home);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (curid==R.id.nav_Home){
                super.onBackPressed();}
            else {
                displaySelectedScreen(R.id.nav_Home);
                curid=R.id.nav_Home;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.radio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(RadioActivity.this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.action_ContactUs){
            Intent intent = new Intent(RadioActivity.this,ContactUs.class);
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {


        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_Home:
                fragment = new HomeFragClass();

                break;
            case R.id.nav_Profile:
                fragment = new ProfileFragClass();
                break;
            case R.id.nav_Events:
                fragment = new EventFragClass();
                break;
            case R.id.nav_FAQ:
                fragment = new FaqFragClass();
                break;
            case R.id.nav_Feedback:
                fragment = new FeedbackFragClass();
                break;
            case R.id.nav_Notifications:
                fragment = new NotificationFragClass();
                break;
            case R.id.nav_About:
                fragment = new AboutFragClass();
                break;
            case R.id.nav_SignOut:
                Intent intent = new Intent(RadioActivity.this,MainActivity.class);
                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.putBoolean("isLoggedIn",false);
                editor.commit();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.nav_Locations:
                Intent intent1 = new Intent(RadioActivity.this,MapsListActivity.class);
                startActivity(intent1);
                break;

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.content_frame, fragment);

            ft.commit();

            // ft.addToBackStack(null);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // TextView tvusername = (TextView) findViewById(R.id.tvUserName);
        //tvusername.setText(name);
        curid=item.getItemId();
        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }


}