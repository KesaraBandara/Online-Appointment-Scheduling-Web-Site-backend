package com.thejobs.onlineappointmentschedulingwebsite.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tokens")
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    private String token;

    @Column(name = "created_date")
    private Date createdDate;

    @OneToOne(targetEntity = Consultant.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Consultant consultant;

    public AuthenticationToken(Consultant consultant) {
        this.consultant = consultant;
        this.createdDate = new Date();
        this.token = UUID.randomUUID().toString();
    }


    public AuthenticationToken() {

    }

    public AuthenticationToken(Integer id, String token, Date createdDate, Consultant consultant) {
        this.id = id;
        this.token = token;
        this.createdDate = createdDate;
        this.consultant = consultant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }
}