package com.example.whatpill.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.whatpill.alarm.Alarm;
import com.example.whatpill.activity.MainActivity;
import com.example.whatpill.R;
import com.example.whatpill.alarm.Alarm;

public class Timer extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_alarm, container, false);

        Intent intent = new Intent(getActivity(), Alarm.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

        return view;
    }
}