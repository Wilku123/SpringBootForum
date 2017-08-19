package com.netstore.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Master on 2017-07-28.
 */
@Entity
@Table(name = "TOPIC_REST_VIEW", schema = "ii301952", catalog = "")
public class TopicRestViewEntity {
    private Integer idTopic;
    private String name;
    private Timestamp publishDate;
    private Integer userIdUser;
    private Integer isSub;
    private Integer circleIdCircle;

    @Id
    @Column(name = "idTopic", nullable = false)
    public Integer getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(Integer idTopic) {
        this.idTopic = idTopic;
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
    @Column(name = "publish_date", nullable = true)
    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    @Basic
    @Column(name = "USER_idUSER", nullable = false)
    public Integer getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(Integer userIdUser) {
        this.userIdUser = userIdUser;
    }

    @Basic
    @Column(name = "Is_Sub", nullable = false)
    public Integer getIsSub() {
        return isSub;
    }

    public void setIsSub(Integer isSub) {
        this.isSub = isSub;
    }

    @Basic
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

        TopicRestViewEntity that = (TopicRestViewEntity) o;

        if (idTopic != null ? !idTopic.equals(that.idTopic) : that.idTopic != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;
        if (userIdUser != null ? !userIdUser.equals(that.userIdUser) : that.userIdUser != null) return false;
        if (isSub != null ? !isSub.equals(that.isSub) : that.isSub != null) return false;
        if (circleIdCircle != null ? !circleIdCircle.equals(that.circleIdCircle) : that.circleIdCircle != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTopic != null ? idTopic.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (userIdUser != null ? userIdUser.hashCode() : 0);
        result = 31 * result + (isSub != null ? isSub.hashCode() : 0);
        result = 31 * result + (circleIdCircle != null ? circleIdCircle.hashCode() : 0);
        return result;
    }
}
