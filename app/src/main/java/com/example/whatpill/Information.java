package com.example.whatpill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Information extends AppCompatActivity {

    TextView tvExplain;
    Button btnWarning, btnFood, btnGood;
    ImageView ivPill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        tvExplain = findViewById(R.id.btnExplain);
        btnWarning = findViewById(R.id.btnWarning);
        btnFood = findViewById(R.id.btnFood);
        btnGood = findViewById(R.id.btnGood);
        ivPill = findViewById(R.id.ivPill);

        Intent intent = getIntent();
        String pillName = intent.getStringExtra("name");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference pills = db.collection("name");


        db.collection(pillName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // 데이터를 가져오는 작업이 잘 동작했을 떄
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String a = document.getString("explain").replace(":", "\n");
                                tvExplain.setText(a);
                            }
                        } else {
                            tvExplain.setText("Error => " + task.getException());
                        }
                    }
                });

        btnWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = pillName;

                Intent intent = new Intent(getApplicationContext(), Warning.class);
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