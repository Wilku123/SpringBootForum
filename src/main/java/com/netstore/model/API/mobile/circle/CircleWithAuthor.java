package com.netstore.model.API.mobile.circle;

import com.netstore.model.view.UserRestViewEntity;

import java.sql.Timestamp;

public class CircleWithAuthor {
    private Integer idCircle;
    private String name;
    private String description;
    private Integer userIdUser;
    private Long countSubbed;
    private Integer isSub;
    private Long countTopic;
    private Timestamp publishDate;
    private String uuid;
    private UserRestViewEntity author;

    public Integer getIdCircle() {
        return idCircle;
    }

    public void setIdCircle(Integer idCircle) {
        this.idCircle = idCircle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(Integer userIdUser) {
        this.userIdUser = userIdUser;
    }

    public Long getCountSubbed() {
        return countSubbed;
    }

    public void setCountSubbed(Long countSubbed) {
        this.countSubbed = countSubbed;
    }

    public Integer getIsSub() {
        return isSub;
    }

    public void setIsSub(Integer isSub) {
        this.isSub = isSub;
    }

    public Long getCountTopic() {
        return countTopic;
    }

    public void setCountTopic(Long countTopic) {
        this.countTopic = countTopic;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public UserRestViewEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserRestViewEntity author) {
        this.author = author;
    }
}
