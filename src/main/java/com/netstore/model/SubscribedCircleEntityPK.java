package com.netstore.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Master on 2017-07-10.
 */
public class SubscribedCircleEntityPK implements Serializable {
    private Integer userIdUser;
    private Integer circleIdCircle;

    @Column(name = "USER_idUSER", nullable = false)
    @Id
    public Integer getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(Integer userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Column(name = "CIRCLE_idCIRCLE", nullable = false)
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

        SubscribedCircleEntityPK that = (SubscribedCircleEntityPK) o;

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
