package com.example.whatpill;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Food extends AppCompatActivity {

    TextView textview;
    String a, b, c, cls, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        textview = findViewById(R.id.textView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //BEARSE라고 나오는 거
        CollectionReference bea = db.collection("bearse");
        bea.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        a = document.getId();
                    }
                }

            }
        });
        //TACENOL이라고 나오는 거
        CollectionReference tac = db.collection("tacenol");
        tac.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        b = document.getId();
                    }
                }

            }
        });
        //IBUROEN이라고 나오는 거
        CollectionReference ibu = db.collection("iburoen");
        ibu.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        c = document.getId();
                    }
                }

            }
        });
        // 파이썬에서 가져온 컬렉션
        CollectionReference python = db.collection("jeong88");
        python.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        cls = document.getString("class");
                    }
                }

            }
        });

        // 자바 클래스로 바꾸는 짓거리
        if(cls == a) {
            id = "bearse";
        } else if(cls == b) {
            id = "tacenol";
        } else if(cls == c) {
            id = "iburoen";
        }


        CollectionReference pills = db.collection("jeong99").document("pills").collection(id);

        pills.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String nofood = document.getString("nofood");
                        textview.setText(nofood);
                    }
                } else{
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });
    }
}