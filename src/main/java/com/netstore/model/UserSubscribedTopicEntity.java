package com.netstore.model;

import javax.persistence.*;

/**
 * Created by Master on 2017-04-26.
 */
@Entity
@Table(name = "USER_SUBSCRIBED_TOPIC", schema = "mydb")
@IdClass(UserSubscribedTopicEntityPK.class)
public class UserSubscribedTopicEntity {
    private int userIdUser;
    private int topicIdTopic;

    @Id
    @Column(name = "USER_ID_User")
    public int getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(int userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Id
    @Column(name = "TOPIC_ID_Topic")
    public int getTopicIdTopic() {
        return topicIdTopic;
    }

    public void setTopicIdTopic(int topicIdTopic) {
        this.topicIdTopic = topicIdTopic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSubscribedTopicEntity that = (UserSubscribedTopicEntity) o;

        if (userIdUser != that.userIdUser) return false;
        if (topicIdTopic != that.topicIdTopic) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userIdUser;
        result = 31 * result + topicIdTopic;
        return result;
    }
}
