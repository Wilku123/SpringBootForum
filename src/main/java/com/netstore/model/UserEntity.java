package com.netstore.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Master on 2017-07-10.
 */
@Entity
@Table(name = "USER", schema = "ii301952", catalog = "")
public class UserEntity {
    private Integer idUser;
    private String name;
    private String lastName;
    private String token;
    private Collection<AnswerEntity> answersByIdUser;
    private Collection<CircleEntity> circlesByIdUser;
    private Collection<RoleEntity> rolesByIdUser;
    private Collection<SubscribedCircleEntity> subscribedCirclesByIdUser;
    private Collection<SubscribedTopicEntity> subscribedTopicsByIdUser;
    private Collection<TopicEntity> topicsByIdUser;

    @Id
    @GeneratedValue
    @Column(name = "idUSER", nullable = false)
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "Name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "LastName", nullable = false, length = 85)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "Token", nullable = true, length = -1)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (idUser != null ? !idUser.equals(that.idUser) : that.idUser != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser != null ? idUser.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByUserIdUser")
    public Collection<AnswerEntity> getAnswersByIdUser() {
        return answersByIdUser;
    }

    public void setAnswersByIdUser(Collection<AnswerEntity> answersByIdUser) {
        this.answersByIdUser = answersByIdUser;
    }

    @OneToMany(mappedBy = "userByUserIdUser")
    public Collection<CircleEntity> getCirclesByIdUser() {
        return circlesByIdUser;
    }

    public void setCirclesByIdUser(Collection<CircleEntity> circlesByIdUser) {
        this.circlesByIdUser = circlesByIdUser;
    }

    @OneToMany(mappedBy = "userByUserIdUser")
    public Collection<RoleEntity> getRolesByIdUser() {
        return rolesByIdUser;
    }

    public void setRolesByIdUser(Collection<RoleEntity> rolesByIdUser) {
        this.rolesByIdUser = rolesByIdUser;
    }

    @OneToMany(mappedBy = "userByUserIdUser")
    public Collection<SubscribedCircleEntity> getSubscribedCirclesByIdUser() {
        return subscribedCirclesByIdUser;
    }

    public void setSubscribedCirclesByIdUser(Collection<SubscribedCircleEntity> subscribedCirclesByIdUser) {
        this.subscribedCirclesByIdUser = subscribedCirclesByIdUser;
    }

    @OneToMany(mappedBy = "userByUserIdUser")
    public Collection<SubscribedTopicEntity> getSubscribedTopicsByIdUser() {
        return subscribedTopicsByIdUser;
    }

    public void setSubscribedTopicsByIdUser(Collection<SubscribedTopicEntity> subscribedTopicsByIdUser) {
        this.subscribedTopicsByIdUser = subscribedTopicsByIdUser;
    }

    @OneToMany(mappedBy = "userByUserIdUser")
    public Collection<TopicEntity> getTopicsByIdUser() {
        return topicsByIdUser;
    }

    public void setTopicsByIdUser(Collection<TopicEntity> topicsByIdUser) {
        this.topicsByIdUser = topicsByIdUser;
    }
}
