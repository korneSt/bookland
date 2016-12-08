package com.stepnik.kornel.bookshare.models;

/**
 * Created by korSt on 08.12.2016.
 */

public class RegisterRequest {
    String username;
    String email;
    String password;

    public RegisterRequest() {

    }

    public RegisterRequest(String username, String email, String password) {

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
}
