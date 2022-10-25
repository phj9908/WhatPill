package com.example.whatpill.information;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.whatpill.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Good extends AppCompatActivity {

    ImageView ivGood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);

        ivGood = findViewById(R.id.ivGood);

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
                                String a = document.getString("good");

                                FirebaseStorage storage = FirebaseStorage.getInstance("gs://what-pill.appspot.com");
                                StorageReference storageReference = storage.getReference();
                                StorageReference pathReference = storageReference.child(a);
                                pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Glide.with(getApplicationContext()).load(uri).into(ivGood);
                                    }
                                });
                            }
                        }
                    }
                });
    }
}