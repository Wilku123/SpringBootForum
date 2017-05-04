package com.netstore.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Master on 2017-04-27.
 */
public class UserSubscribedTopicEntityPK implements Serializable {
    private Integer userIdUser;
    private Integer topicIdTopic;

    @Column(name = "USER_ID_User")
    @Id
    public Integer getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(Integer userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Column(name = "TOPIC_ID_Topic")
    @Id
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

        UserSubscribedTopicEntityPK that = (UserSubscribedTopicEntityPK) o;

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
}
