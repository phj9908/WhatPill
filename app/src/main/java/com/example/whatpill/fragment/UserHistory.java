package com.example.whatpill.fragment;

import java.util.Calendar;

public class UserHistory {

    private String pillName,illnessName,date;

    public UserHistory(String pillName , Object illnessName, String date){
        this.pillName = pillName;
        this.illnessName = (String) illnessName;
        this.date = date;
    }

    public String getPillName() {
        return pillName;
    }

    public String getDate() {
        return date;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public String getIllnessName() {
        return illnessName;
    }

    public void setIllnessName(String illnessName) {
        this.illnessName = illnessName;
    }


}
