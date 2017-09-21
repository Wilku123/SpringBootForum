package com.netstore.model.entity;

import javax.persistence.*;

/**
 * Created by Master on 2017-07-10.
 */
@Entity
@Table(name = "SUBSCRIBED_CIRCLE", schema = "ii301952", catalog = "")
@IdClass(SubscribedCircleEntityPK.class)
public class SubscribedCircleEntity {
    private Integer userIdUser;
    private Integer circleIdCircle;
    private UserEntity userByUserIdUser;
    private CircleEntity circleByCircleIdCircle;

    @Id
    @Column(name = "USER_idUSER", nullable = false)
    public Integer getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(Integer userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Id
    @Column(name = "CIRCLE_idCIRCLE", nullable = false)
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

        SubscribedCircleEntity that = (SubscribedCircleEntity) o;

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

    @ManyToOne
    @JoinColumn(name = "USER_idUSER", referencedColumnName = "idUSER", nullable = false,insertable = false, updatable = false)
    public UserEntity getUserByUserIdUser() {
        return userByUserIdUser;
    }

    public void setUserByUserIdUser(UserEntity userByUserIdUser) {
        this.userByUserIdUser = userByUserIdUser;
    }

    @ManyToOne
    @JoinColumn(name = "CIRCLE_idCIRCLE", referencedColumnName = "idCIRCLE", nullable = false,insertable = false, updatable = false)
    public CircleEntity getCircleByCircleIdCircle() {
        return circleByCircleIdCircle;
    }

    public void setCircleByCircleIdCircle(CircleEntity circleByCircleIdCircle) {
        this.circleByCircleIdCircle = circleByCircleIdCircle;
    }
}
