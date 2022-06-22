package com.example.atplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


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
                Intent intent = new Intent( MainActivity.this, LocationActivity.class );
                startActivity( intent );
            }
        });

    }

}