package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by usman on 25-04-2017.
 */
public class GetDepartmentsRequest extends StringRequest {
    static host h =new host();
    private static final String REGISTER_REQUEST_URL = h.address+"/getDepartments.php";
    private Map<String, String> params;

    public GetDepartmentsRequest( String schoolId , Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("schoolid",schoolId);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
