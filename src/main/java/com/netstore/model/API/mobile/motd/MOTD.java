package com.netstore.model.API.mobile.motd;

import com.netstore.model.entity.MotdEntity;
import com.netstore.model.view.UserRestViewEntity;

import java.sql.Timestamp;

public class MOTD {
    private Integer idMotd;
    private String message;
    private Timestamp timestamp;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    private UserRestViewEntity author;

    public UserRestViewEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserRestViewEntity author) {
        this.author = author;
    }

    public Integer getIdMotd() {

        return idMotd;
    }

    public void setIdMotd(Integer idMotd) {
        this.idMotd = idMotd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
