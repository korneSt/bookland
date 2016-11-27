package com.stepnik.kornel.bookshare.models;

import java.io.Serializable;

/**
 * Created by korSt on 25.11.2016.
 */

public class LoginRequest implements Serializable{
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}