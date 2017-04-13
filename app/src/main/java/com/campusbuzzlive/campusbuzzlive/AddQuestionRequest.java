package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 4/5/2017.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class AddQuestionRequest extends StringRequest {
    static host h =new host();

    private static final String REGISTER_REQUEST_URL = h.address+"/addQuestion.php";
    //private static final String REGISTER_REQUEST_URL = "https://terminological-hois.000webhostapp.com/addQuestion.php";
    private Map<String, String> params;

    public AddQuestionRequest(String question, String datetime, String enrollmentid, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("question", question);
        params.put("datetime", datetime);

        params.put("enrollmentid", enrollmentid);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}