package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 4/12/2017.
 */

public class DeleteAnswerRequest extends StringRequest {
    static host h =new host();

    private static final String REGISTER_REQUEST_URL = h.address+"/deleteAnswer.php";
    private Map<String, String> params;

    public DeleteAnswerRequest(String answerid, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("answerid", answerid);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}