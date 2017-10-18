package com.netstore.model.API;

import java.util.List;

/**
 * Created by Master on 2017-10-15.
 */
public class ListWithUser<T,V> {
    private List<T> data;
    private List<V> user;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<V> getUser() {
        return user;
    }

    public void setUser(List<V> user) {
        this.user = user;
    }
}
