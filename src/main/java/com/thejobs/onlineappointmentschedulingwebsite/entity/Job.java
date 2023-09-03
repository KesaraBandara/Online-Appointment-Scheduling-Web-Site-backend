package com.thejobs.onlineappointmentschedulingwebsite.entity;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column  (name = "job_id", length = 20)
    private long id;
    @Column  (name = "job_type", length = 20,nullable = false)
    private String jobType;
    @ManyToMany(mappedBy = "jobTypes")
    private Set<Consultant> consultants = new HashSet<>();

    public Job() {
    }

    public Job(long id, String jobType, Set<Consultant> consultants) {
        this.id = id;
        this.jobType = jobType;
        this.consultants = consultants;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Set<Consultant> getConsultants() {
        return consultants;
    }

    public void setConsultants(Set<Consultant> consultants) {
        this.consultants = consultants;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", jobType='" + jobType + '\'' +
                ", consultants=" + consultants +
                '}';
    }
}
