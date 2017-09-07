package com.netstore.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Master on 2017-07-23.
 */
@Entity
@Table(name = "CIRCLE_REST_VIEW", schema = "ii301952", catalog = "")
public class CircleRestViewEntity {
    private Integer idCircle;
    private String name;
    private String description;
    private Integer userIdUser;
    private Long countSubbed;
    private Integer isSub;
    private Long countTopic;
    private Timestamp publishDate;
    private String uuid;

    @Basic
    @Id
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

    @Basic
    @Column(name = "Count_Subbed", nullable = true)
    public Long getCountSubbed() {
        return countSubbed;
    }

    public void setCountSubbed(Long countSubbed) {
        this.countSubbed = countSubbed;
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
    @Column(name = "Count_Topic", nullable = true)
    public Long getCountTopic() {
        return countTopic;
    }

    public void setCountTopic(Long countTopic) {
        this.countTopic = countTopic;
    }

    @Basic
    @Column(name = "Publish_Date", nullable = true)
    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CircleRestViewEntity that = (CircleRestViewEntity) o;

        if (idCircle != null ? !idCircle.equals(that.idCircle) : that.idCircle != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (userIdUser != null ? !userIdUser.equals(that.userIdUser) : that.userIdUser != null) return false;
        if (countSubbed != null ? !countSubbed.equals(that.countSubbed) : that.countSubbed != null) return false;
        if (isSub != null ? !isSub.equals(that.isSub) : that.isSub != null) return false;
        if (countTopic != null ? !countTopic.equals(that.countTopic) : that.countTopic != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCircle != null ? idCircle.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (userIdUser != null ? userIdUser.hashCode() : 0);
        result = 31 * result + (countSubbed != null ? countSubbed.hashCode() : 0);
        result = 31 * result + (isSub != null ? isSub.hashCode() : 0);
        result = 31 * result + (countTopic != null ? countTopic.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "uuid", nullable = false, length = 100)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
