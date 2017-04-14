package com.campusbuzzlive.campusbuzzlive;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 4/15/2017.
 */


public class UploadImageRequest extends StringRequest {
    static host h =new host();

    private static final String REGISTER_REQUEST_URL = h.address+"/upload.php";
    private Map<String, String> params;

    public UploadImageRequest(String enrollmentid, String image, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("enrollmentid", enrollmentid);
        params.put("image", image);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}