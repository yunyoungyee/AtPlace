package com.example.atplace;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SearchRequest extends StringRequest{
    @Override //response를 UTF8로 변경해주는 소스코드
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
    try {
        String utf8String = new String(response.data, "UTF-8");
        return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
    } catch (UnsupportedEncodingException e) {
        // log error
        return Response.error(new ParseError(e));
    } catch (Exception e) {
        // log error
        return Response.error(new ParseError(e));
    }
}


        //서버 URL 설정(php 파일 연동)
        final static private String URL = "http://4055-175-126-15-53.ngrok.io/addresses";
        private Map<String, String> map;

        public SearchRequest(String place1, String place2, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);

            map = new HashMap<>();
            map.put("place1",place1);
            map.put("place2", place2);
        }

        @Override
        protected Map<String, String>getParams() throws AuthFailureError {
            return map;
        }
    }



