package com.example.whatpill;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class Name extends AppCompatActivity {

    String pill [] = {"class0", "class1", "class2", "class3"};
    Button[] nameBtn = new Button[5];
    Integer [] nameBtnID = {R.id.name1, R.id.name2, R.id.name3, R.id.name4, R.id.name5};

    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference pills = db.collection("jeong88");

        for (i = 0; i < pill.length; i++) {
            nameBtn[i] = findViewById(nameBtnID[i]);
        }

        for (i = 0; i < pill.length; i++) {
            final int index;
            index = i;

            nameBtn[index].setVisibility(View.VISIBLE);

            pills.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String a = document.getString(pill[index]);
                            nameBtn[index].setText(a);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }

                }
            });

            nameBtn[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Information.class);
                    startActivity(intent);
                }
            });
        }
    }
}