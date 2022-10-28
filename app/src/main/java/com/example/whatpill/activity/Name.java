package com.example.whatpill.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Name extends AppCompatActivity {

    String pill [] = {"class0", "class1", "class2","class3","class4"};
    Button[] nameBtn = new Button[5];
//    Button btnHome;
    Integer [] nameBtnID = {R.id.name1, R.id.name2, R.id.name3, R.id.name4, R.id.name5};
    ImageView ivDetecting;
    ArrayList<String> pillName = new ArrayList<>();

    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Map<String, Object> name = new HashMap<>();
        name.put("bearse","베아제");
        name.put("cenoba","세노바");
        name.put("bearrose","베아로제");
        name.put("jeongrohwan","정로환");
        name.put("pajaim","파자임");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        ivDetecting = findViewById(R.id.ivDetecting);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference pills = db.collection("pill");

        for (i = 0; i < pill.length; i++) {
            nameBtn[i] = findViewById(nameBtnID[i]);
        }
//        btnHome = findViewById(R.id.btnHome);

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
                            nameBtn[index].setText((CharSequence) name.get(a));
                            pillName.add(a);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });

            nameBtn[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //String input = nameBtn[index].getText().toString();
                    String input = pillName.get(index);

                    Intent intent = new Intent(getApplicationContext(), Information.class);
                    intent.putExtra("name", input);

                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    startActivity(intent);
                }
            });

//            btnHome.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), Menu.class);
//                    startActivity(intent);
//                }
//            });

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