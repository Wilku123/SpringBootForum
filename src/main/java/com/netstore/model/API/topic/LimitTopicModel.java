package com.netstore.model.API.topic;

import java.sql.Timestamp;

/**
 * Created by Master on 2017-09-02.
 */
public class LimitTopicModel {

    private Timestamp date;
    private Integer id;
    private int howMany;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getHowMany() {
        return howMany;
    }

    public void setHowMany(int howMany) {
        this.howMany = howMany;
    }
}
