package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by usman on 16-04-2017.
 */

public class ForgotPasswordRequest extends StringRequest {

    static host h =new host();

    private static final String REGISTER_REQUEST_URL = h.address+"/forgotPassword.php";
    private Map<String, String> params;

    public ForgotPasswordRequest(String email,String enrollmentid , Response.Listener<String> listener,Response.ErrorListener errorListener) {

        super(Method.POST, REGISTER_REQUEST_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email);
        params.put("enrollmentid", enrollmentid);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
