package com.netstore.model.API.mobile;

import java.util.List;

/**
 * Created by Master on 2017-09-04.
 */
public class SchemaRestList<T> {

    private boolean response;
    private String message;
    private int errorCode;
    private List<T> data;

    public SchemaRestList(boolean response, String message, int errorCode, List<T> data) {
        this.response = response;
        this.message = message;
        this.errorCode = errorCode;
        this.data = data;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
