package com.example.atplace;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = "http://4055-175-126-15-53.ngrok.io/login";

    private Map<String, String> map;

    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
    }



    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}