package com.thejobs.onlineappointmentschedulingwebsite.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "time")
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column  (name = "time_id", length = 20)
    private long id;
    @Column  (name = "time", length = 20,nullable = false)
    private String time;
    @ManyToMany(mappedBy = "availableTimes")
    private Set<Consultant> consultants = new HashSet<>();

    public Time() {
    }

    public Time(long id, String time, Set<Consultant> consultants) {
        this.id = id;
        this.time = time;
        this.consultants = consultants;
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

    public Set<Consultant> getConsultants() {
        return consultants;
    }

    public void setConsultants(Set<Consultant> consultants) {
        this.consultants = consultants;
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", consultants=" + consultants +
                '}';
    }
}
