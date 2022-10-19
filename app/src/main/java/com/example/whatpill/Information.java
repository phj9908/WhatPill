package com.example.whatpill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Information extends AppCompatActivity {

    Button btnExplain, btnPerson, btnPill, btnFood, btnGood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        btnExplain = findViewById(R.id.btnExplain);
        btnPerson = findViewById(R.id.btnPerson);
        btnPill = findViewById(R.id.btnPill);
        btnFood = findViewById(R.id.btnFood);
        btnGood = findViewById(R.id.btnGood);

        btnExplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Worning.class);
                startActivity(intent);
            }
        });
    }
}