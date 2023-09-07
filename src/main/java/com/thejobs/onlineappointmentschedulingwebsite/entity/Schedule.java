package com.thejobs.onlineappointmentschedulingwebsite.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column  (name = "schedule_id", length = 20)
    private long id;
//    @Column  (name = "token", length = 50,nullable = false)
//    private String token;
    @Column  (name = "country", length = 20,nullable = false)
    private String country;
    @Column  (name = "time", length = 20,nullable = false)
    private String time;
    @Column  (name = "day", length = 20,nullable = false)
    private String day;
    @Column  (name = "job_Type", length = 20,nullable = false)
    private String jobType;
    @ManyToOne(fetch = FetchType.LAZY)
    private Consultant consultant;

    public Schedule() {
    }

    public Schedule(long id, String country, String time, String day, String jobType, Consultant consultant) {
        this.id = id;
        this.country = country;
        this.time = time;
        this.day = day;
        this.jobType = jobType;
        this.consultant = consultant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", time='" + time + '\'' +
                ", day='" + day + '\'' +
                ", jobType='" + jobType + '\'' +
                ", consultant=" + consultant +
                '}';
    }
    //    public Schedule() {
//    }
//
//    public Schedule(long id, String token, String country, String time, String day, String jobType, Consultant consultant) {
//        this.id = id;
//        this.token = token;
//        this.country = country;
//        this.time = time;
//        this.day = day;
//        this.jobType = jobType;
//        this.consultant = consultant;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public String getDay() {
//        return day;
//    }
//
//    public void setDay(String day) {
//        this.day = day;
//    }
//
//    public String getJobType() {
//        return jobType;
//    }
//
//    public void setJobType(String jobType) {
//        this.jobType = jobType;
//    }
//
//    public Consultant getConsultant() {
//        return consultant;
//    }
//
//    public void setConsultant(Consultant consultant) {
//        this.consultant = consultant;
//    }
//
//    @Override
//    public String toString() {
//        return "Schedule{" +
//                "id=" + id +
//                ", token='" + token + '\'' +
//                ", country='" + country + '\'' +
//                ", time='" + time + '\'' +
//                ", day='" + day + '\'' +
//                ", jobType='" + jobType + '\'' +
//                ", consultant=" + consultant +
//                '}';
//    }
}
