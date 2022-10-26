package com.example.whatpill.fragment;

import java.util.Calendar;

public class UserHistory {

    private String pillName,illnessName;
    //private Calendar startDate,endDate;

    public UserHistory(String pillName , String illnessName){
        this.pillName = pillName;
        this.illnessName = illnessName;
    }
//    public UserHistory(String pillName,Calendar startDate,Calendar endDate, String illnessName){
//        this.pillName = pillName;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.illnessName = illnessName;
//    }

    public String getPillName() {
        return pillName;
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

//    public Calendar getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(Calendar startDate) {
//        this.startDate = startDate;
//    }
//
//    public Calendar getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Calendar endDate) {
//        this.endDate = endDate;
//    }

}
