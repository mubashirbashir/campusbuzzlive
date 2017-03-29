package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 3/29/2017.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://terminological-hois.000webhostapp.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String email, String password, String enrollmentid,String department,String gender, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("enrollmentid", enrollmentid);
        params.put("department", department);
        params.put("gender", gender);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}