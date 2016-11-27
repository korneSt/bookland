package com.stepnik.kornel.bookshare.models;

import java.io.Serializable;

/**
 * Created by korSt on 27.10.2016.
 */

public class User implements Serializable {

    private long id;
    private String token;
    private String email;
    private String username;
    private String password;
    private int ratePos;
    private int rateNeg;
    private double prefLocalRadius;
    private double prefLocalLat;
    private double prefLocalLon;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRatePos() {
        return ratePos;
    }

    public void setRatePos(int ratePos) {
        this.ratePos = ratePos;
    }

    public int getRateNeg() {
        return rateNeg;
    }

    public void setRateNeg(int rateNeg) {
        this.rateNeg = rateNeg;
    }

    public double getPrefLocalRadius() {
        return prefLocalRadius;
    }

    public void setPrefLocalRadius(double prefLocalRadius) {
        this.prefLocalRadius = prefLocalRadius;
    }

    public double getPrefLocalLat() {
        return prefLocalLat;
    }

    public void setPrefLocalLat(double prefLocalLat) {
        this.prefLocalLat = prefLocalLat;
    }

    public double getPrefLocalLon() {
        return prefLocalLon;
    }

    public void setPrefLocalLon(double prefLocalLon) {
        this.prefLocalLon = prefLocalLon;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
