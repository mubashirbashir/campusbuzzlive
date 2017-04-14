package com.campusbuzzlive.campusbuzzlive;

import android.app.Application;

/**
 * Created by mubas on 4/11/2017.
 */

public class Session extends Application {
    public static String enrollSession ;



    public String getEnrollSession() {
        return enrollSession;
    }

    public void setEnrollSession(String enrollSession) {
        this.enrollSession = enrollSession;
    }
}
