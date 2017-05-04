package com.netstore.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Master on 2017-04-27.
 */
@Entity
@Table(name = "CIRCLE", schema = "mydb", catalog = "")
public class CircleEntity {

    private Integer idCircle;
    private String name;
    private String description;
    private Date publishDate;

    @Id
    @GeneratedValue
    @Column(name = "ID_Circle")
    public Integer getIdCircle() {
        return idCircle;
    }

    public void setIdCircle(Integer idCircle) {
        this.idCircle = idCircle;
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
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        CircleEntity that = (CircleEntity) o;

        if (idCircle != null ? !idCircle.equals(that.idCircle) : that.idCircle != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCircle != null ? idCircle.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        return result;
    }
}
