package com.netstore.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Master on 2017-04-27.
 */
@Entity
@Table(name = "ANSWER", schema = "mydb", catalog = "")
public class AnswerEntity {
    private Integer idAnswer;
    private String content;
    private Date publishDate;

    @Id
    @Column(name = "ID_Answer")
    public Integer getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(Integer idAnswer) {
        this.idAnswer = idAnswer;
    }

    @Basic
    @Column(name = "Content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

        AnswerEntity that = (AnswerEntity) o;

        if (idAnswer != null ? !idAnswer.equals(that.idAnswer) : that.idAnswer != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAnswer != null ? idAnswer.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        return result;
    }
}
