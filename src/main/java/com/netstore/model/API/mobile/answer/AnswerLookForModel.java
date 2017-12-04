package com.netstore.model.API.mobile.answer;

/**
 * Created by Master on 2017-09-07.
 */
public class AnswerLookForModel {
    private int howMany;
    private String content;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHowMany() {
        return howMany;
    }

    public void setHowMany(int howMany) {
        this.howMany = howMany;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
