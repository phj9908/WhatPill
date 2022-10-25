package com.example.whatpill.information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.whatpill.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Warning extends AppCompatActivity {
    TextView tvPerson, tvPill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

        tvPerson = findViewById(R.id.tvPerson);
        tvPill = findViewById(R.id.tvPill);

        Intent intent = getIntent();
        String pillName = intent.getStringExtra("name");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(pillName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // 데이터를 가져오는 작업이 잘 동작했을 떄
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String a = document.getString("person").replace(", ", "\n");
                                tvPerson.setText(a);
                            }
                        } else {
                            tvPerson.setText("Error => " + task.getException());
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
                                String a = document.getString("pill").replace(", ", "\n");
                                tvPill.setText(a);
                            }
                        } else {
                            tvPill.setText("Error => " + task.getException());
                        }
                    }
                });
    }
}