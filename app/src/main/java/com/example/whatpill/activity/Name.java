package com.example.whatpill.activity;

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
import com.example.whatpill.R;
import com.example.whatpill.information.Information;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Name extends AppCompatActivity {

    String pill [] = {};
    Button[] nameBtn = new Button[5];
    Integer [] nameBtnID = {R.id.name1, R.id.name2, R.id.name3, R.id.name4, R.id.name5};
    ImageView ivDetecting;

    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        ivDetecting = findViewById(R.id.ivDetecting);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference pills = db.collection("pill");

        pills.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        pills.add(document.getId());
                    }
                }
            }
        });

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
                    String input = nameBtn[index].getText().toString();

                    Intent intent = new Intent(getApplicationContext(), Information.class);
                    intent.putExtra("name", input);
                    startActivity(intent);
                }
            });

            FirebaseStorage storage = FirebaseStorage.getInstance("gs://what-pill.appspot.com");
            StorageReference storageReference = storage.getReference();
            StorageReference pathReference = storageReference.child("downloads/img.png");
            pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getApplicationContext()).load(uri).into(ivDetecting);
                }
            });
        }
    }
}