package com.netstore.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Master on 2017-07-10.
 */
@Entity
@Table(name = "CIRCLE", schema = "ii301952", catalog = "")
public class CircleEntity {
    private Integer idCircle;
    private String name;
    private Timestamp publishDate;
    private String description;
    private Integer userIdUser;
    private UserEntity userByUserIdUser;
    private Collection<SubscribedCircleEntity> subscribedCirclesByIdCircle;
    private Collection<TopicEntity> topicsByIdCircle;
    private String uuid;

    @Id
    @GeneratedValue
    @Column(name = "idCIRCLE", nullable = false)
    public Integer getIdCircle() {
        return idCircle;
    }

    public void setIdCircle(Integer idCircle) {
        this.idCircle = idCircle;
    }

    @Basic
    @Column(name = "Name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Publish_Date", nullable = true)
    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    @Basic
    @Column(name = "Description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "USER_idUSER", nullable = false)
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

        CircleEntity that = (CircleEntity) o;

        if (idCircle != null ? !idCircle.equals(that.idCircle) : that.idCircle != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (userIdUser != null ? !userIdUser.equals(that.userIdUser) : that.userIdUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCircle != null ? idCircle.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (userIdUser != null ? userIdUser.hashCode() : 0);
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

    @OneToMany(mappedBy = "circleByCircleIdCircle")
    public Collection<SubscribedCircleEntity> getSubscribedCirclesByIdCircle() {
        return subscribedCirclesByIdCircle;
    }

    public void setSubscribedCirclesByIdCircle(Collection<SubscribedCircleEntity> subscribedCirclesByIdCircle) {
        this.subscribedCirclesByIdCircle = subscribedCirclesByIdCircle;
    }

    @OneToMany(mappedBy = "circleByCircleIdCircle")
    public Collection<TopicEntity> getTopicsByIdCircle() {
        return topicsByIdCircle;
    }

    public void setTopicsByIdCircle(Collection<TopicEntity> topicsByIdCircle) {
        this.topicsByIdCircle = topicsByIdCircle;
    }

    @Basic
    @Column(name = "Uuid", nullable = false, length = 100)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
