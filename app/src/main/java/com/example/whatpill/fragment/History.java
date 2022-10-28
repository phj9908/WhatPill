package com.example.whatpill.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatpill.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class History extends Fragment {

    private ArrayList<UserHistory> userHistories = new ArrayList<>();
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private FirebaseAuth firebaseAuth;

    String field[] = {"pill", "time"};
    String getPill[] = new String[3];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        historyAdapter = new HistoryAdapter(userHistories);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyAdapter);

        return v;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, Object> name = new HashMap<>();
        name.put("bearse","베아제");
        name.put("cenoba","세노바");
        name.put("bearrose","베아로제");
        name.put("jungrohwan","정로환");
        name.put("pajaim","파자임");


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //사용자 uid얻어오기
        firebaseAuth =  FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();
//        String uid = "tvorTgHCHeSxbNmKMx8tXmJSU0S2";

        CollectionReference dbPath = db.collection("user").document(uid).collection("history");
        dbPath.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    userHistories.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String docGetPill = document.getString("explain");
                        userHistories.add(new UserHistory((String) name.get(document.getString("name")),document.getString("explain"),document.getId()));

                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

    }

}
