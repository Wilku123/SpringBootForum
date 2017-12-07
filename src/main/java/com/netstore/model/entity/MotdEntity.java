package com.netstore.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "MOTD", schema = "mydb", catalog = "")
public class MotdEntity {
    private Integer idMotd;
    private String message;
    private Integer authorId;
    private Timestamp publishDate;

    @Basic
    @Id
    @GeneratedValue
    @Column(name = "idMOTD", nullable = false)
    public Integer getIdMotd() {
        return idMotd;
    }

    public void setIdMotd(Integer idMotd) {
        this.idMotd = idMotd;
    }

    @Basic
    @Column(name = "Message", nullable = true, length = 250)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MotdEntity that = (MotdEntity) o;

        if (idMotd != null ? !idMotd.equals(that.idMotd) : that.idMotd != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMotd != null ? idMotd.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "AuthorId", nullable = true)
    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    @Basic
    @Column(name = "Publish_Date", nullable = true)
    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }
}
