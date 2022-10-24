package com.example.whatpill.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.whatpill.R;
import com.example.whatpill.fragment.Camera;
import com.example.whatpill.fragment.History;
import com.example.whatpill.fragment.Home;
import com.example.whatpill.fragment.Timer;
import com.google.android.material.navigation.NavigationBarView;

public class Menu extends AppCompatActivity {

    Home homeView;
    History historyView;
    Camera cameraView;
    Timer timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btns);
        homeView = new Home();
        cameraView = new Camera();
        historyView = new History();
        timerView = new Timer();

        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, homeView).commit();

        NavigationBarView navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btnHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, homeView).commit();
                        return true;
                    case R.id.btnHistory:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, historyView).commit();
                        return true;

                    case R.id.btnCamera:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, cameraView).commit();
                        return true;
                    case R.id.btnTimer:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, timerView).commit();
                        return true;
                }
                return false;
            }
        });
    }
}