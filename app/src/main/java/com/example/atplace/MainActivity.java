package com.example.atplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.daum.android.map.MapView;

public class MainActivity extends AppCompatActivity {


    private Button cancel_button;
    private Button search_button;
    private EditText editText1,editText2,editText3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String address = intent.getStringExtra("Address");
        String name= intent.getStringExtra("name");
        String road_address = intent.getStringExtra("road_address");


        setContentView(R.layout.activity_main);
        editText1=findViewById(R.id.edittext1);
        editText1.setText(address);
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
                Intent intent = new Intent( MainActivity.this, LocationActivity.class );
                startActivity( intent );
            }

        });
    }
}