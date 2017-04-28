package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 4/26/2017.
 */

public class GetProfileRequest extends StringRequest {
    static host h =new host();

    private static final String LOGIN_REQUEST_URL = h.address+"/getProfile.php";
    //private static final String LOGIN_REQUEST_URL = "https://terminological-hois.000webhostapp.com/getcoordinates.php";
    private Map<String, String> params;

    public GetProfileRequest (String enroll ,Response.Listener<String> listener,Response.ErrorListener errorListener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, errorListener);
        params = new HashMap<>();
        // Toast.makeText(this,enrollmentid+" "+password,Toast.LENGTH_LONG).show();

        params.put("enroll", enroll);
        // params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}