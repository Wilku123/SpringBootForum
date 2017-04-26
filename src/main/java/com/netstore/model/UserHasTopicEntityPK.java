package com.netstore.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Master on 2017-04-26.
 */
public class UserHasTopicEntityPK implements Serializable {
    private int topicIdTopic;
    private int userIdUser;

    @Column(name = "TOPIC_ID_Topic")
    @Id
    public int getTopicIdTopic() {
        return topicIdTopic;
    }

    public void setTopicIdTopic(int topicIdTopic) {
        this.topicIdTopic = topicIdTopic;
    }

    @Column(name = "USER_ID_User")
    @Id
    public int getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(int userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHasTopicEntityPK that = (UserHasTopicEntityPK) o;

        if (topicIdTopic != that.topicIdTopic) return false;
        if (userIdUser != that.userIdUser) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = topicIdTopic;
        result = 31 * result + userIdUser;
        return result;
    }
}
