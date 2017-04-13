package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 4/5/2017.
 */
public class AddAnswerRequest extends StringRequest {
    static host h =new host();

    private static final String REGISTER_REQUEST_URL = h.address+"/addAnswer.php";
    private Map<String, String> params;

    public AddAnswerRequest(String questtionid, String datetime  , String answer,String enrollmentid, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("questionid", questtionid);
        params.put("datetime", datetime);
        params.put("answer", answer);

        params.put("enrollmentid", enrollmentid);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}