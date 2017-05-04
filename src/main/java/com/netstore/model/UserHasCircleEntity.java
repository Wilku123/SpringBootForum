package com.netstore.model;

import javax.persistence.*;

/**
 * Created by Master on 2017-04-27.
 */
@Entity
@Table(name = "USER_has_CIRCLE", schema = "mydb", catalog = "")
@IdClass(UserHasCircleEntityPK.class)
public class UserHasCircleEntity {
    private Integer circleIdCircle;
    private Integer userIdUser;
    private CircleEntity circleByCircleIdCircle;
    private UserEntity userByUserIdUser;

    @Id
    @Column(name = "CIRCLE_ID_Circle")
    public Integer getCircleIdCircle() {
        return circleIdCircle;
    }

    public void setCircleIdCircle(Integer circleIdCircle) {
        this.circleIdCircle = circleIdCircle;
    }

    @Id
    @Column(name = "USER_ID_User")
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

        UserHasCircleEntity that = (UserHasCircleEntity) o;

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

    @ManyToOne
    @JoinColumn(name = "CIRCLE_ID_Circle", referencedColumnName = "ID_Circle",insertable = false,updatable = false,nullable = false)
    public CircleEntity getCircleByCircleIdCircle() {
        return circleByCircleIdCircle;
    }

    public void setCircleByCircleIdCircle(CircleEntity circleByCircleIdCircle) {
        this.circleByCircleIdCircle = circleByCircleIdCircle;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID_User", referencedColumnName = "ID_User",insertable = false,updatable = false , nullable = false)
    public UserEntity getUserByUserIdUser() {
        return userByUserIdUser;
    }

    public void setUserByUserIdUser(UserEntity userByUserIdUser) {
        this.userByUserIdUser = userByUserIdUser;
    }
}
