package com.netstore.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Master on 2017-04-27.
 */
@Entity
@Table(name = "TOPIC", schema = "mydb", catalog = "")
public class TopicEntity {
    private Integer idTopic;
    private String name;
    private Date publishDate;
    private CircleEntity circleByCircleIdCircle;

    @Id
    @Column(name = "ID_Topic")
    public Integer getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(Integer idTopic) {
        this.idTopic = idTopic;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Publish_Date")
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TopicEntity that = (TopicEntity) o;

        if (idTopic != null ? !idTopic.equals(that.idTopic) : that.idTopic != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTopic != null ? idTopic.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "CIRCLE_ID_Circle", referencedColumnName = "ID_Circle", nullable = false)
    public CircleEntity getCircleByCircleIdCircle() {
        return circleByCircleIdCircle;
    }

    public void setCircleByCircleIdCircle(CircleEntity circleByCircleIdCircle) {
        this.circleByCircleIdCircle = circleByCircleIdCircle;
    }
}
