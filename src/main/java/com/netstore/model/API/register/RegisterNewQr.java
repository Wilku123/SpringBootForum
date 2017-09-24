package com.netstore.model.API.register;

/**
 * Created by Master on 2017-09-23.
 */
public class RegisterNewQr {
    private String token;
    private Integer userId;
    private String pin;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
