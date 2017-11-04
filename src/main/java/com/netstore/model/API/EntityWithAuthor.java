package com.netstore.model.API;

import com.netstore.model.view.UserRestViewEntity;

/**
 * Created by Master on 2017-11-01.
 */
public class EntityWithAuthor<T> {
    private T data;
    private UserRestViewEntity Author;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public UserRestViewEntity getAuthor() {
        return Author;
    }

    public void setAuthor(UserRestViewEntity author) {
        Author = author;
    }

    public EntityWithAuthor(T data, UserRestViewEntity author) {
        this.data = data;
        Author = author;
    }
}
