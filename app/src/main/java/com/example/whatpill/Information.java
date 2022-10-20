package com.example.whatpill;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Information extends AppCompatActivity {

    Button btnExplain, btnPerson, btnPill, btnFood, btnGood;
    ImageView ivPill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        btnExplain = findViewById(R.id.btnExplain);
        btnPerson = findViewById(R.id.btnPerson);
        btnPill = findViewById(R.id.btnPill);
        btnFood = findViewById(R.id.btnFood);
        btnGood = findViewById(R.id.btnGood);
        ivPill = findViewById(R.id.ivPill);

        Intent intent = getIntent();
        String pillName = intent.getStringExtra("name");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference pills = db.collection("name");


        btnExplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Explain.class);
                startActivity(intent);
            }
        });

        btnPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = pillName;

                Intent intent = new Intent(getApplicationContext(), Person.class);
                intent.putExtra("name", input);
                startActivity(intent);
            }
        });

        btnPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = pillName;

                Intent intent = new Intent(getApplicationContext(), Pill.class);
                intent.putExtra("name", input);
                startActivity(intent);
            }
        });

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = pillName;

                Intent intent = new Intent(getApplicationContext(), Food.class);
                intent.putExtra("name", input);
                startActivity(intent);
            }
        });

        btnGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = pillName;

                Intent intent = new Intent(getApplicationContext(), Good.class);
                intent.putExtra("name", input);
                startActivity(intent);
            }
        });
    }
}