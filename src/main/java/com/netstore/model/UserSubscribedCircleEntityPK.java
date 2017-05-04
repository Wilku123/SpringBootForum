package com.netstore.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Master on 2017-04-27.
 */
public class UserSubscribedCircleEntityPK implements Serializable {
    private Integer userIdUser;
    private Integer circleIdCircle;

    @Column(name = "USER_ID_User")
    @Id
    public Integer getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(Integer userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Column(name = "CIRCLE_ID_Circle")
    @Id
    public Integer getCircleIdCircle() {
        return circleIdCircle;
    }

    public void setCircleIdCircle(Integer circleIdCircle) {
        this.circleIdCircle = circleIdCircle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSubscribedCircleEntityPK that = (UserSubscribedCircleEntityPK) o;

        if (userIdUser != null ? !userIdUser.equals(that.userIdUser) : that.userIdUser != null) return false;
        if (circleIdCircle != null ? !circleIdCircle.equals(that.circleIdCircle) : that.circleIdCircle != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userIdUser != null ? userIdUser.hashCode() : 0;
        result = 31 * result + (circleIdCircle != null ? circleIdCircle.hashCode() : 0);
        return result;
    }
}
