package com.stepnik.kornel.bookshare.models;

import java.io.Serializable;

/**
 * Created by korSt on 25.11.2016.
 */

public class LoginResponse implements Serializable{
    private Long userId;
    private String token;

    public LoginResponse(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

