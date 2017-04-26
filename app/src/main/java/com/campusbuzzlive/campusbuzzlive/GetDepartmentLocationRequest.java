package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 4/26/2017.
 */

public class GetDepartmentLocationRequest extends StringRequest{
    static host h =new host();

    private static final String LOGIN_REQUEST_URL = h.address+"/getDepartmentCoordinates.php";
    //private static final String LOGIN_REQUEST_URL = "https://terminological-hois.000webhostapp.com/getcoordinates.php";
    private Map<String, String> params;

    public GetDepartmentLocationRequest (String id ,Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        // Toast.makeText(this,enrollmentid+" "+password,Toast.LENGTH_LONG).show();

        params.put("id", id);
        // params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
