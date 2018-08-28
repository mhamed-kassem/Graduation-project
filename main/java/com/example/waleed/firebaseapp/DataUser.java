package com.example.waleed.firebaseapp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by waleed on 3/30/2018.
 */


public class DataUser {
    private String time;
    private String mail;
    private int heartRate;
    private float tempreture;
    private int low_prusser;
    private int high_pressure;

    public  DataUser(){
//        this.time = "5/5/2015";
//        this.mail = "";
//        this.heartRate = 10;
//        this.tempreture = 10.10f;
//        this.low_prusser = 10;
//        this.high_pressure = 10;
    }

    public DataUser(String time, String mail, int heartRate, float tempreture, int low_prusser, int high_pressure) {
        this.time = time;
        this.mail = mail;
        this.heartRate = heartRate;
        this.tempreture = tempreture;
        this.low_prusser = low_prusser;
        this.high_pressure = high_pressure;
    }

    public String getTime() {
        return time;
    }
    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }



    public int getHeartRate() {
        return heartRate;
    }
    @JsonProperty("heartRate")
    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }



    public float getTempreture() {
        return tempreture;
    }
    @JsonProperty("tempreture")
    public void setTempreture(float tempreture) {
        this.tempreture = tempreture;
    }


    public int getLow_prusser() {
        return low_prusser;
    }
    @JsonProperty("low_prusser")
    public void setLow_prusser(int low_prusser) {
        this.low_prusser = low_prusser;
    }


    public int getHigh_pressure() {
        return high_pressure;
    }
    @JsonProperty("high_pressure")
    public void setHigh_pressure(int high_pressure) {
        this.high_pressure = high_pressure;
    }
}
