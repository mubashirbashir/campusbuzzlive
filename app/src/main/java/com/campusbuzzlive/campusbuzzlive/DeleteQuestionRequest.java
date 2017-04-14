package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 4/14/2017.
 */

public class DeleteQuestionRequest extends StringRequest {
    static host h =new host();

    private static final String REGISTER_REQUEST_URL = h.address+"/deleteQuestion.php";
    private Map<String, String> params;

    public DeleteQuestionRequest(String questionid, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("questionid", questionid);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}