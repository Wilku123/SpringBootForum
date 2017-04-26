package com.netstore.model;

import javax.persistence.*;

/**
 * Created by Master on 2017-04-26.
 */
@Entity
@Table(name = "USER_SUBSCRIBED_CIRCLE", schema = "mydb")
@IdClass(UserSubscribedCircleEntityPK.class)
public class UserSubscribedCircleEntity {
    private int userIdUser;
    private int circleIdCircle;

    @Id
    @Column(name = "USER_ID_User")
    public int getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(int userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Id
    @Column(name = "CIRCLE_ID_Circle")
    public int getCircleIdCircle() {
        return circleIdCircle;
    }

    public void setCircleIdCircle(int circleIdCircle) {
        this.circleIdCircle = circleIdCircle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSubscribedCircleEntity that = (UserSubscribedCircleEntity) o;

        if (userIdUser != that.userIdUser) return false;
        if (circleIdCircle != that.circleIdCircle) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userIdUser;
        result = 31 * result + circleIdCircle;
        return result;
    }
}
