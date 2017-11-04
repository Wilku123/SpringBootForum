package com.netstore.model.API;

/**
 * Created by Master on 2017-09-04.
 */
public class SchemaRest<T> {

    private boolean response;
    private String message;
    private int errorCode;
    private T data;

    public SchemaRest(boolean response, String message, int errorCode, T data) {
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
