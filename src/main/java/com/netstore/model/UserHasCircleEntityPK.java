package com.netstore.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Master on 2017-04-26.
 */
public class UserHasCircleEntityPK implements Serializable {
    private int circleIdCircle;
    private int userIdUser;

    @Column(name = "CIRCLE_ID_Circle")
    @Id
    public int getCircleIdCircle() {
        return circleIdCircle;
    }

    public void setCircleIdCircle(int circleIdCircle) {
        this.circleIdCircle = circleIdCircle;
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

        UserHasCircleEntityPK that = (UserHasCircleEntityPK) o;

        if (circleIdCircle != that.circleIdCircle) return false;
        if (userIdUser != that.userIdUser) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = circleIdCircle;
        result = 31 * result + userIdUser;
        return result;
    }
}
