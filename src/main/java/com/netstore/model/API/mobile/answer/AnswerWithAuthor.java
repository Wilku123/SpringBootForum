package com.netstore.model.API.mobile.answer;

import com.netstore.model.view.UserRestViewEntity;

import java.sql.Timestamp;

public class AnswerWithAuthor {
    private Integer idAnswer;
    private String content;
    private Timestamp publishDate;
    private Integer userIdUser;
    private Integer topicIdTopic;
    private String uuid;
    private int yours;
    private UserRestViewEntity author;

    public UserRestViewEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserRestViewEntity author) {
        this.author = author;
    }

    public Integer getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(Integer idAnswer) {
        this.idAnswer = idAnswer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(Integer userIdUser) {
        this.userIdUser = userIdUser;
    }

    public Integer getTopicIdTopic() {
        return topicIdTopic;
    }

    public void setTopicIdTopic(Integer topicIdTopic) {
        this.topicIdTopic = topicIdTopic;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getYours() {
        return yours;
    }

    public void setYours(int yours) {
        this.yours = yours;
    }
}
