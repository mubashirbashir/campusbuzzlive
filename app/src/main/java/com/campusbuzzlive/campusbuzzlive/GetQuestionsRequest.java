package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 4/5/2017.
 */

public class GetQuestionsRequest extends StringRequest {
    static host h =new host();
    private static final String REGISTER_REQUEST_URL = h.address+"/getQuestions.php";
    private Map<String, String> params;

    public GetQuestionsRequest( Response.Listener<String> listener,Response.ErrorListener errorListener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, errorListener);
        params = new HashMap<>();
//        params.put("eventName", eventName);
//        params.put("date", date);
//        params.put("time", time);
//        params.put("location", location);
//        params.put("enrollmentid", enrollmentid);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}