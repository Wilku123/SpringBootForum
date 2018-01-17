package com.netstore.model.API.mobile.register;

import com.netstore.model.view.UserRestViewEntity;

public class CredentialsWithUser {
    private int idCredentials;
    private String token;
    private String pin;
    private UserRestViewEntity user;

    public int getIdCredentials() {
        return idCredentials;
    }

    public void setIdCredentials(int idCredentials) {
        this.idCredentials = idCredentials;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public UserRestViewEntity getUser() {
        return user;
    }

    public void setUser(UserRestViewEntity user) {
        this.user = user;
    }
}
