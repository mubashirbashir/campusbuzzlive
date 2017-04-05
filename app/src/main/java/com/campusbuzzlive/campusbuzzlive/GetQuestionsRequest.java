package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 4/5/2017.
 */

public class GetQuestionsRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://terminological-hois.000webhostapp.com/getQuestions.php";
    private Map<String, String> params;

    public GetQuestionsRequest( Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
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