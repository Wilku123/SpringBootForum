package com.netstore.model.entity;

import javax.persistence.*;

/**
 * Created by Master on 2017-07-10.
 */
@Entity
@Table(name = "SUBSCRIBED_TOPIC", schema = "ii301952", catalog = "")
@IdClass(SubscribedTopicEntityPK.class)
public class SubscribedTopicEntity {
    private Integer userIdUser;
    private Integer topicIdTopic;
    private UserEntity userByUserIdUser;
    private TopicEntity topicByTopicIdTopic;

    @Id
    @Column(name = "USER_idUSER", nullable = false)
    public Integer getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(Integer userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Id
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

        SubscribedTopicEntity that = (SubscribedTopicEntity) o;

        if (userIdUser != null ? !userIdUser.equals(that.userIdUser) : that.userIdUser != null) return false;
        if (topicIdTopic != null ? !topicIdTopic.equals(that.topicIdTopic) : that.topicIdTopic != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userIdUser != null ? userIdUser.hashCode() : 0;
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
}
