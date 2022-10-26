package com.example.whatpill.information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.whatpill.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Information extends AppCompatActivity {

    TextView tvExplain, tvName;
    Button btnWarning, btnFood, btnGood, btnHistory;
    ImageView ivPill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        tvExplain = findViewById(R.id.tvExplain);
        tvName = findViewById(R.id.tvName);
        btnWarning = findViewById(R.id.btnWarning);
        btnFood = findViewById(R.id.btnFood);
        btnGood = findViewById(R.id.btnGood);
        btnHistory = findViewById(R.id.btnHistory);
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
                                String a = document.getString("name");
                                tvName.setText(a);
                            }
                        } else {
                            tvExplain.setText("Error => " + task.getException());
                        }
                    }
                });

        db.collection(pillName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // 데이터를 가져오는 작업이 잘 동작했을 떄
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String a = document.getString("explain");
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

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> user = new HashMap<>();

                user.put("name", pillName);
                user.put("date", new Timestamp(new Date()));

                db.collection("user")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"저장 완료", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"저장 실패", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            private String getTime() {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = dateFormat.format(date);

                return getTime;
            }
        });

        // 약별로 사진 넣어줌
        db.collection(pillName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // 데이터를 가져오는 작업이 잘 동작했을 떄
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String a = document.getString("image");

                                FirebaseStorage storage = FirebaseStorage.getInstance("gs://what-pill.appspot.com");
                                StorageReference storageReference = storage.getReference();
                                StorageReference pathReference = storageReference.child(a);
                                pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Glide.with(getApplicationContext()).load(uri).into(ivPill);
                                    }
                                });
                            }
                        }
                    }
                });
    }
}