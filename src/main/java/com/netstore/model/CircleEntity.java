package com.netstore.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Master on 2017-04-26.
 */
@Entity
@Table(name = "CIRCLE", schema = "mydb")
public class CircleEntity {
    private int idCircle;
    private String name;
    private String description;
    private Date publishDate;

    @Id
    @Column(name = "ID_Circle")
    public int getIdCircle() {
        return idCircle;
    }

    public void setIdCircle(int idCircle) {
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

        if (idCircle != that.idCircle) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCircle;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        return result;
    }
}
