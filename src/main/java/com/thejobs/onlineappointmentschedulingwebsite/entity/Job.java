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


    public Job() {
    }

    public Job(long id, String jobType) {
        this.id = id;
        this.jobType = jobType;
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

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", jobType='" + jobType + '\'' +
                '}';
    }
}
