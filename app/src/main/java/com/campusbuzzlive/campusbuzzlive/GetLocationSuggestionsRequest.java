package com.campusbuzzlive.campusbuzzlive;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubas on 4/19/2017.
 */

public class GetLocationSuggestionsRequest extends StringRequest {
    static host h =new host();

    private static final String REGISTER_REQUEST_URL = h.address+"/getLocationSuggestions.php";
    //private static final String REGISTER_REQUEST_URL = "https://terminological-hois.000webhostapp.com/addQuestion.php";
    private Map<String, String> params;

    public GetLocationSuggestionsRequest( String pattern,Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("pattern", pattern);
        Log.e("abc", "GetLocationSuggestionsRequest: "+pattern);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
