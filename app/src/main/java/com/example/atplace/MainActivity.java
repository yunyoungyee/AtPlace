package com.example.atplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button cancel_button;
    private Button search_button;
    private EditText[] editText=new EditText[2];
    static List<String> address = new ArrayList<>();
    private int[] text = {R.id.edittext1,R.id.edittext2};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String data = intent.getStringExtra("Address");
        setContentView(R.layout.activity_main);
        if (data != null) {
            address.add(data);
        }
        for(int i=0;i<2;i++){
            editText[i]=findViewById(text[i]);
            editText[i].setOnClickListener((view)-> startActivity(  new Intent(MainActivity.this, LocationActivity.class )));
        }
        for(int i=0;i<address.size();i++){
            editText[i].setText(address.get(i));
        }
        cancel_button = findViewById( R.id.CANCEL);
        cancel_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, MainActivity.class );
                startActivity( intent );
            }
        });
        search_button = findViewById( R.id.SEARCH);
        search_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String place1 = editText[0].getText().toString();
                String place2 = editText[1].getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if(success) {//로그인 성공시


                               /* jsonObject.getDouble(" place1X");
                                jsonObject.getDouble(" place1Y");
                                jsonObject.getDouble(" place2X");
                                jsonObject.getDouble(" place2Y");*/
                                String x = jsonObject.getString("x");
                                String y = jsonObject.getString("y");
                                JSONObject data = (JSONObject) jsonObject.get("data");
                                JSONArray document = (JSONArray) data.get("documents");
                                System.out.println(document);
                                JSONObject jsonObject1 = document.getJSONObject(0);
                                JSONObject temp = (JSONObject)jsonObject1.get("address");
                                String address = temp.getString("address_name");

                                Intent intent = new Intent( MainActivity.this, midActivity.class );
                                System.out.println(address+" "+x+" "+y);
                                intent.putExtra("address", address);
                                intent.putExtra("x",x);
                                intent.putExtra("y",y);
                                startActivity( intent );

                            } else {//로그인 실패시
                                Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SearchRequest searchRequest = new SearchRequest(place1, place2, responseListener);
                RequestQueue queue = Volley.newRequestQueue( MainActivity.this );
                queue.add( searchRequest );
            }
        });

    }

}