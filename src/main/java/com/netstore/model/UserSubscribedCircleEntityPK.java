package com.netstore.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Master on 2017-04-26.
 */
public class UserSubscribedCircleEntityPK implements Serializable {
    private int userIdUser;
    private int circleIdCircle;

    @Column(name = "USER_ID_User")
    @Id
    public int getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(int userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Column(name = "CIRCLE_ID_Circle")
    @Id
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

        UserSubscribedCircleEntityPK that = (UserSubscribedCircleEntityPK) o;

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
