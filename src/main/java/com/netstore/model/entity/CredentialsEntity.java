package com.netstore.model.entity;

import javax.persistence.*;

/**
 * Created by Master on 2017-09-21.
 */
@Entity
@Table(name = "CREDENTIALS", schema = "ii301952", catalog = "")
public class CredentialsEntity {
    private int idCredentials;
    private String token;
    private String pin;
    private int userIdUser;

    @Id
    @GeneratedValue
    @Column(name = "idCREDENTIALS", nullable = false)
    public int getIdCredentials() {
        return idCredentials;
    }

    public void setIdCredentials(int idCredentials) {
        this.idCredentials = idCredentials;
    }

    @Basic
    @Column(name = "Token", nullable = true, length = 255)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "Pin", nullable = true, length = 255)
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CredentialsEntity that = (CredentialsEntity) o;

        if (idCredentials != that.idCredentials) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (pin != null ? !pin.equals(that.pin) : that.pin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCredentials;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (pin != null ? pin.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "USER_idUSER", nullable = false)
    public int getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(int userIdUser) {
        this.userIdUser = userIdUser;
    }
}
