package com.netstore.model.view;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Master on 2017-08-23.
 */
@Entity
@Table(name = "ANSWER_REST_VIEW", schema = "ii301952", catalog = "")
public class AnswerRestViewEntity {
    private Integer idAnswer;
    private String content;
    private Timestamp publishDate;
    private Integer userIdUser;
    private Integer topicIdTopic;
    private String uuid;
    private int yours;

    public void setYours(Integer yours) {
        this.yours = yours;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    public void setUserIdUser(int userIdUser) {
        this.userIdUser = userIdUser;
    }

    public void setTopicIdTopic(int topicIdTopic) {
        this.topicIdTopic = topicIdTopic;
    }

    @Basic
    @Id
    @Column(name = "idAnswer", nullable = false)
    public Integer getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(Integer idAnswer) {
        this.idAnswer = idAnswer;
    }

    @Basic
    @Column(name = "Content", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "Publish_date", nullable = true)
    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    @Basic
    @Column(name = "USER_idUSER", nullable = false)
    public Integer getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(Integer userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Basic
    @Column(name = "TOPIC_idTOPIC", nullable = false)
    public Integer getTopicIdTopic() {
        return topicIdTopic;
    }

    public void setTopicIdTopic(Integer topicIdTopic) {
        this.topicIdTopic = topicIdTopic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerRestViewEntity that = (AnswerRestViewEntity) o;

        if (idAnswer != null ? !idAnswer.equals(that.idAnswer) : that.idAnswer != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;
        if (userIdUser != null ? !userIdUser.equals(that.userIdUser) : that.userIdUser != null) return false;
        if (topicIdTopic != null ? !topicIdTopic.equals(that.topicIdTopic) : that.topicIdTopic != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAnswer != null ? idAnswer.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (userIdUser != null ? userIdUser.hashCode() : 0);
        result = 31 * result + (topicIdTopic != null ? topicIdTopic.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "uuid", nullable = false, length = 100)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "Yours", nullable = false)
    public int getYours() {
        return yours;
    }

    public void setYours(int yours) {
        this.yours = yours;
    }
}
