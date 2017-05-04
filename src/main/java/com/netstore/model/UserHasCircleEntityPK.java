package com.netstore.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Master on 2017-04-27.
 */
public class UserHasCircleEntityPK implements Serializable {
    private Integer circleIdCircle;
    private Integer userIdUser;

    @Column(name = "CIRCLE_ID_Circle")
    @Id
    public Integer getCircleIdCircle() {
        return circleIdCircle;
    }

    public void setCircleIdCircle(Integer circleIdCircle) {
        this.circleIdCircle = circleIdCircle;
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

        UserHasCircleEntityPK that = (UserHasCircleEntityPK) o;

        if (circleIdCircle != null ? !circleIdCircle.equals(that.circleIdCircle) : that.circleIdCircle != null)
            return false;
        if (userIdUser != null ? !userIdUser.equals(that.userIdUser) : that.userIdUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = circleIdCircle != null ? circleIdCircle.hashCode() : 0;
        result = 31 * result + (userIdUser != null ? userIdUser.hashCode() : 0);
        return result;
    }
}
