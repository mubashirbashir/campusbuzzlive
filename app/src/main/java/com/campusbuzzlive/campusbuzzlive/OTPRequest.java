package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by usman on 18-04-2017.
 */

public class OTPRequest extends StringRequest {

    static host h =new host();

    private static final String REGISTER_REQUEST_URL = h.address+"/checkOTP.php";
    private Map<String, String> params;

    public OTPRequest(String enrollmentid, String otp , Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
       //


        params.put("enrollmentid", enrollmentid);
        params.put("otp", otp);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
