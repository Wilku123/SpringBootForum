package com.netstore.model.API.mobile.circle;

import java.sql.Timestamp;

/**
 * Created by Master on 2017-09-01.
 */
public class LimitCircleModel {
    private Timestamp date;
    private int howMany;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getHowMany() {
        return howMany;
    }

    public void setHowMany(int howMany) {
        this.howMany = howMany;
    }
}
