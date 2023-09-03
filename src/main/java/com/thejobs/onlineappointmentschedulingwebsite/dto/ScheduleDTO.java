package com.thejobs.onlineappointmentschedulingwebsite.dto;

public class ScheduleDTO {
    private long id;
    private String token;
    private String country;
    private String time;
    private String day;
    private String jobType;

    public ScheduleDTO() {
    }

    public ScheduleDTO(long id, String token, String country, String time, String day, String jobType) {
        this.id = id;
        this.token = token;
        this.country = country;
        this.time = time;
        this.day = day;
        this.jobType = jobType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    @Override
    public String toString() {
        return "AppointmentDTO{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", country='" + country + '\'' +
                ", time='" + time + '\'' +
                ", day='" + day + '\'' +
                ", jobType='" + jobType + '\'' +
                '}';
    }
}
