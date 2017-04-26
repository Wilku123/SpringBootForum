package com.netstore.model;

import javax.persistence.*;

/**
 * Created by Master on 2017-04-26.
 */
@Entity
@Table(name = "USER_has_TOPIC", schema = "mydb")
@IdClass(UserHasTopicEntityPK.class)
public class UserHasTopicEntity {
    private int topicIdTopic;
    private int userIdUser;

    @Id
    @Column(name = "TOPIC_ID_Topic")
    public int getTopicIdTopic() {
        return topicIdTopic;
    }

    public void setTopicIdTopic(int topicIdTopic) {
        this.topicIdTopic = topicIdTopic;
    }

    @Id
    @Column(name = "USER_ID_User")
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

        UserHasTopicEntity that = (UserHasTopicEntity) o;

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
