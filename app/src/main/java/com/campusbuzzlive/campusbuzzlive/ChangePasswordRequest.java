package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 4/11/2017.
 */

public class ChangePasswordRequest extends StringRequest {
    static host h =new host();

    private static final String REGISTER_REQUEST_URL = h.address+"/changePassword.php";
    private Map<String, String> params;

    public ChangePasswordRequest(String enrollmentid, String password , Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("password", password);


        params.put("enrollmentid", enrollmentid);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
