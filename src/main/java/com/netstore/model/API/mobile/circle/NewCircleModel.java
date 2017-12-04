package com.netstore.model.API.mobile.circle;

/**
 * Created by Master on 2017-09-01.
 */
public class NewCircleModel {
    private String description;
    private String name;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
