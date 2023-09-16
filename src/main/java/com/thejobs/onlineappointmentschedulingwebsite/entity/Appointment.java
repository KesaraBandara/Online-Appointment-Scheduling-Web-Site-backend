package com.thejobs.onlineappointmentschedulingwebsite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column  (name = "appointment_id", length = 20)
    private long id;
//    @Column  (name = "consultant_id", length = 50,nullable = false)
//    private String consultantId;
//    @Column  (name = "user_token", length = 50,nullable = false)
//    private String userToken;
    @Column  (name = "time", length = 50,nullable = false)
    private String time;
    @Column(name = "date",length = 100)
    private String date;
    @Column(name = "day",length = 100)
    private String day;
    @Column(name = "country",length = 100)
    private String country;

    @Column(name = "job_type",length = 100)
    private String jobType;
//    @Column(name = "created_date", length = 100)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createDate;

    @Column(name = "active_state")
    private boolean activeState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Consultant consultant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

    public Appointment() {
    }

    public Appointment(long id, String time, String date, String day, String country, String jobType, boolean activeState, User user, Consultant consultant) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.day = day;
        this.country = country;
        this.jobType = jobType;
        this.activeState = activeState;
        this.user = user;
        this.consultant = consultant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public boolean isActiveState() {
        return activeState;
    }

    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", country='" + country + '\'' +
                ", jobType='" + jobType + '\'' +
                ", activeState=" + activeState +
                ", user=" + user +
                ", consultant=" + consultant +
                '}';
    }
}
