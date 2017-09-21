package com.netstore.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Master on 2017-07-10.
 */
@Entity
@Table(name = "ANSWER", schema = "ii301952", catalog = "")
public class AnswerEntity {
    private Integer idAnswer;
    private String content;
    private Timestamp publishDate;
    private Integer userIdUser;
    private Integer topicIdTopic;
    private UserEntity userByUserIdUser;
    private TopicEntity topicByTopicIdTopic;
    private String uuid;

    @Id
    @GeneratedValue
    @Column(name = "idANSWER", nullable = false)
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
    @Column(name = "Publish_Date", nullable = true)
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

        AnswerEntity that = (AnswerEntity) o;

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

    @ManyToOne
    @JoinColumn(name = "USER_idUSER", referencedColumnName = "idUSER", nullable = false,insertable = false, updatable = false)
    public UserEntity getUserByUserIdUser() {
        return userByUserIdUser;
    }

    public void setUserByUserIdUser(UserEntity userByUserIdUser) {
        this.userByUserIdUser = userByUserIdUser;
    }

    @ManyToOne
    @JoinColumn(name = "TOPIC_idTOPIC", referencedColumnName = "idTOPIC", nullable = false,insertable = false, updatable = false)
    public TopicEntity getTopicByTopicIdTopic() {
        return topicByTopicIdTopic;
    }

    public void setTopicByTopicIdTopic(TopicEntity topicByTopicIdTopic) {
        this.topicByTopicIdTopic = topicByTopicIdTopic;
    }

    @Basic
    @Column(name = "Uuid", nullable = false, length = 100)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
