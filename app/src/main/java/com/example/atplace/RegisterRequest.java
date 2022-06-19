package com.example.atplace;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://36a1-116-47-96-224.ngrok.io/join";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public RegisterRequest(String username, String password, String name,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("name", name);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
