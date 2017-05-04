package com.netstore.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Master on 2017-04-27.
 */
public class UserHasTopicEntityPK implements Serializable {
    private Integer topicIdTopic;
    private Integer userIdUser;

    @Column(name = "TOPIC_ID_Topic")
    @Id
    public Integer getTopicIdTopic() {
        return topicIdTopic;
    }

    public void setTopicIdTopic(Integer topicIdTopic) {
        this.topicIdTopic = topicIdTopic;
    }

    @Column(name = "USER_ID_User")
    @Id
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

        UserHasTopicEntityPK that = (UserHasTopicEntityPK) o;

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
}
