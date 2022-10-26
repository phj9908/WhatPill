package com.example.whatpill.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.whatpill.R;
import com.example.whatpill.fragment.Camera;
import com.example.whatpill.fragment.History;
import com.example.whatpill.fragment.Search;
import com.example.whatpill.fragment.Timer;
import com.example.whatpill.information.Good;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class Menu extends AppCompatActivity {

    Search searchView;
    History historyView;
    Timer timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btns);
        searchView = new Search();
        historyView = new History();
        timerView = new Timer();

        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, searchView).commit();
        NavigationBarView navigationBarView = findViewById(R.id.bottomNavigationView);

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btnHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, searchView).commit();
                        return true;
                    case R.id.btnHistory:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, historyView).commit();
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