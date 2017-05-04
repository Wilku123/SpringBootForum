package com.netstore.model;

import javax.persistence.*;

/**
 * Created by Master on 2017-04-27.
 */
@Entity
@Table(name = "USER_has_TOPIC", schema = "mydb", catalog = "")
@IdClass(UserHasTopicEntityPK.class)
public class UserHasTopicEntity {
    private Integer topicIdTopic;
    private Integer userIdUser;
    private TopicEntity topicByTopicIdTopic;
    private UserEntity userByUserIdUser;

    @Id
    @Column(name = "TOPIC_ID_Topic")
    public Integer getTopicIdTopic() {
        return topicIdTopic;
    }

    public void setTopicIdTopic(Integer topicIdTopic) {
        this.topicIdTopic = topicIdTopic;
    }

    @Id
    @Column(name = "USER_ID_User")
    public Integer getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(Integer userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHasTopicEntity that = (UserHasTopicEntity) o;

        if (topicIdTopic != null ? !topicIdTopic.equals(that.topicIdTopic) : that.topicIdTopic != null) return false;
        if (userIdUser != null ? !userIdUser.equals(that.userIdUser) : that.userIdUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = topicIdTopic != null ? topicIdTopic.hashCode() : 0;
        result = 31 * result + (userIdUser != null ? userIdUser.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "TOPIC_ID_Topic", referencedColumnName = "ID_Topic",insertable = false,updatable = false, nullable = false)
    public TopicEntity getTopicByTopicIdTopic() {
        return topicByTopicIdTopic;
    }

    public void setTopicByTopicIdTopic(TopicEntity topicByTopicIdTopic) {
        this.topicByTopicIdTopic = topicByTopicIdTopic;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID_User", referencedColumnName = "ID_User",insertable = false,updatable = false, nullable = false)
    public UserEntity getUserByUserIdUser() {
        return userByUserIdUser;
    }

    public void setUserByUserIdUser(UserEntity userByUserIdUser) {
        this.userByUserIdUser = userByUserIdUser;
    }
}
