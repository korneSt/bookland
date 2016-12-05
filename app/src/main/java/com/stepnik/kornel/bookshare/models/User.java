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
    private float prefLocalRadius;
    private float prefLocalLat;
    private float prefLocalLon;

    public User() {

    }

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

    public float getPrefLocalRadius() {
        return prefLocalRadius;
    }

    public void setPrefLocalRadius(float prefLocalRadius) {
        this.prefLocalRadius = prefLocalRadius;
    }

    public float getPrefLocalLat() {
        return prefLocalLat;
    }

    public void setPrefLocalLat(float prefLocalLat) {
        this.prefLocalLat = prefLocalLat;
    }

    public float getPrefLocalLon() {
        return prefLocalLon;
    }

    public void setPrefLocalLon(float prefLocalLon) {
        this.prefLocalLon = prefLocalLon;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
