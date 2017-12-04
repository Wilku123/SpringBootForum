package com.netstore.model.API.mobile.answer;

/**
 * Created by Master on 2017-09-02.
 */
public class NewAnswerModel {
    private Integer id;
    private String content;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
