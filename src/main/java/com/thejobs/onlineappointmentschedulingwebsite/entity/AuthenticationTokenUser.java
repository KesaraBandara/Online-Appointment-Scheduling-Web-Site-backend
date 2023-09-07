package com.thejobs.onlineappointmentschedulingwebsite.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tokens_user")
public class AuthenticationTokenUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column  (name = "userToken", length = 70,nullable = false)
    private String userToken;

    @Column(name = "created_date")
    private Date createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public AuthenticationTokenUser() {
    }

    public AuthenticationTokenUser(Integer id, String userToken, Date createdDate, User user) {
        this.id = id;
        this.userToken = userToken;
        this.createdDate = createdDate;
        this.user = user;
    }


    public AuthenticationTokenUser(User user) {
        this.user = user;
        this.createdDate = new Date();
        this.userToken = UUID.randomUUID().toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String token) {
        this.userToken = token;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
