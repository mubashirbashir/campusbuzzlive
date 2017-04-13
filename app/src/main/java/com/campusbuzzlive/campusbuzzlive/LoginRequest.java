package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/29/2017.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    static host h =new host();
    private static final String LOGIN_REQUEST_URL = h.address+"/Login.php";

    //private static final String LOGIN_REQUEST_URL = "https://terminological-hois.000webhostapp.com/Login.php";
    private Map<String, String> params;

    public LoginRequest(String enrollmentid, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        // Toast.makeText(this,enrollmentid+" "+password,Toast.LENGTH_LONG).show();

        params.put("enrollmentid", enrollmentid);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}