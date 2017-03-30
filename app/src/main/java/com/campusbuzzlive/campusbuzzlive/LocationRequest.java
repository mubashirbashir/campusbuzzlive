package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 3/30/2017.
 */
public class LocationRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://terminological-hois.000webhostapp.com/getlocations.php";
    private Map<String, String> params;

    public LocationRequest(Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        // Toast.makeText(this,enrollmentid+" "+password,Toast.LENGTH_LONG).show();

        //   params.put("enrollmentid", enrollmentid);
        // params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}