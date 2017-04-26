package com.netstore.model;

import javax.persistence.*;

/**
 * Created by Master on 2017-04-26.
 */
@Entity
@Table(name = "USER_has_CIRCLE", schema = "mydb")
@IdClass(UserHasCircleEntityPK.class)
public class UserHasCircleEntity {
    private int circleIdCircle;
    private int userIdUser;

    @Id
    @Column(name = "CIRCLE_ID_Circle")
    public int getCircleIdCircle() {
        return circleIdCircle;
    }

    public void setCircleIdCircle(int circleIdCircle) {
        this.circleIdCircle = circleIdCircle;
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

        UserHasCircleEntity that = (UserHasCircleEntity) o;

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
