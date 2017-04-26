package com.netstore.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Master on 2017-04-26.
 */
public class UserSubscribedTopicEntityPK implements Serializable {
    private int userIdUser;
    private int topicIdTopic;

    @Column(name = "USER_ID_User")
    @Id
    public int getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(int userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Column(name = "TOPIC_ID_Topic")
    @Id
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

        UserSubscribedTopicEntityPK that = (UserSubscribedTopicEntityPK) o;

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
