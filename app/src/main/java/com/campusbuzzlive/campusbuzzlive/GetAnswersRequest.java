package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 4/5/2017.
 */

public class GetAnswersRequest extends StringRequest {
    static host h =new host();

    private static final String REGISTER_REQUEST_URL = h.address+"/getAnswers.php";
 //   private static final String REGISTER_REQUEST_URL = "https://terminological-hois.000webhostapp.com/getAnswers.php";
    private Map<String, String> params;

    public GetAnswersRequest(String questionid, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("questionid", questionid);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}