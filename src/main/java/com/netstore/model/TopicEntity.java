package com.netstore.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Master on 2017-07-10.
 */
@Entity
@Table(name = "TOPIC", schema = "ii301952", catalog = "")
public class TopicEntity {
    private Integer idTopic;
    private String name;
    private Timestamp publishDate;
    private Integer userIdUser;
    private Integer circleIdCircle;
    private Collection<AnswerEntity> answersByIdTopic;
    private Collection<SubscribedTopicEntity> subscribedTopicsByIdTopic;
    private UserEntity userByUserIdUser;
    private CircleEntity circleByCircleIdCircle;

    @Id
    @GeneratedValue
    @Column(name = "idTOPIC", nullable = false)
    public Integer getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(Integer idTopic) {
        this.idTopic = idTopic;
    }

    @Basic
    @Column(name = "Name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "CIRCLE_idCIRCLE", nullable = false)
    public Integer getCircleIdCircle() {
        return circleIdCircle;
    }

    public void setCircleIdCircle(Integer circleIdCircle) {
        this.circleIdCircle = circleIdCircle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TopicEntity that = (TopicEntity) o;

        if (idTopic != null ? !idTopic.equals(that.idTopic) : that.idTopic != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;
        if (userIdUser != null ? !userIdUser.equals(that.userIdUser) : that.userIdUser != null) return false;
        if (circleIdCircle != null ? !circleIdCircle.equals(that.circleIdCircle) : that.circleIdCircle != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTopic != null ? idTopic.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (userIdUser != null ? userIdUser.hashCode() : 0);
        result = 31 * result + (circleIdCircle != null ? circleIdCircle.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "topicByTopicIdTopic")
    public Collection<AnswerEntity> getAnswersByIdTopic() {
        return answersByIdTopic;
    }

    public void setAnswersByIdTopic(Collection<AnswerEntity> answersByIdTopic) {
        this.answersByIdTopic = answersByIdTopic;
    }

    @OneToMany(mappedBy = "topicByTopicIdTopic")
    public Collection<SubscribedTopicEntity> getSubscribedTopicsByIdTopic() {
        return subscribedTopicsByIdTopic;
    }

    public void setSubscribedTopicsByIdTopic(Collection<SubscribedTopicEntity> subscribedTopicsByIdTopic) {
        this.subscribedTopicsByIdTopic = subscribedTopicsByIdTopic;
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
    @JoinColumn(name = "CIRCLE_idCIRCLE", referencedColumnName = "idCIRCLE", nullable = false,insertable = false, updatable = false)
    public CircleEntity getCircleByCircleIdCircle() {
        return circleByCircleIdCircle;
    }

    public void setCircleByCircleIdCircle(CircleEntity circleByCircleIdCircle) {
        this.circleByCircleIdCircle = circleByCircleIdCircle;
    }
}
