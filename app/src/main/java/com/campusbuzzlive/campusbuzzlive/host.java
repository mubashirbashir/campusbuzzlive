package com.campusbuzzlive.campusbuzzlive;

import android.app.Application;

/**
 * Created by mubas on 4/11/2017.
 */

public class host extends Application{
    public static  String address;

    public host() {
      //  address="http://192.168.1.40/android_connect";
        address="http://172.16.40.224/android_connect";
     //address="http://192.168.43.87/android_connect";
         // address="https://terminological-hois.000webhostapp.com";
    }

}

