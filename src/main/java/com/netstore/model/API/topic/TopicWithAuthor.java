package com.netstore.model.API.topic;

import com.netstore.model.view.UserRestViewEntity;

import java.sql.Timestamp;

public class TopicWithAuthor {
    private Integer idTopic;
    private String name;
    private Timestamp publishDate;
    private Integer userIdUser;
    private Integer isSub;
    private Integer circleIdCircle;
    private String uuid;
    private String description;
    private Long countSubbed;
    private Long countAnswer;
    private UserRestViewEntity author;

    public Integer getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(Integer idTopic) {
        this.idTopic = idTopic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getIsSub() {
        return isSub;
    }

    public void setIsSub(Integer isSub) {
        this.isSub = isSub;
    }

    public Integer getCircleIdCircle() {
        return circleIdCircle;
    }

    public void setCircleIdCircle(Integer circleIdCircle) {
        this.circleIdCircle = circleIdCircle;
    }

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

    public Long getCountSubbed() {
        return countSubbed;
    }

    public void setCountSubbed(Long countSubbed) {
        this.countSubbed = countSubbed;
    }

    public Long getCountAnswer() {
        return countAnswer;
    }

    public void setCountAnswer(Long countAnswer) {
        this.countAnswer = countAnswer;
    }

    public UserRestViewEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserRestViewEntity author) {
        this.author = author;
    }
}
