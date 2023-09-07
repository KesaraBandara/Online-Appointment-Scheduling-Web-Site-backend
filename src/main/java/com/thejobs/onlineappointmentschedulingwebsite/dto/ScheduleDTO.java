package com.thejobs.onlineappointmentschedulingwebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ScheduleDTO {
    private long id;
//    private String token;
    private String country;
    private String time;
    private String day;
    private String jobType;
    private ConsultantDTO consultant;

    public ScheduleDTO() {
    }

    public ScheduleDTO(long id, String country, String time, String day, String jobType, ConsultantDTO consultant) {
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

    public ConsultantDTO getConsultant() {
        return consultant;
    }

    public void setConsultant(ConsultantDTO consultant) {
        this.consultant = consultant;
    }

    @Override
    public String toString() {
        return "ScheduleDTO{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", time='" + time + '\'' +
                ", day='" + day + '\'' +
                ", jobType='" + jobType + '\'' +
                ", consultant=" + consultant +
                '}';
    }
    //
//    public ScheduleDTO() {
//    }
//
//    public ScheduleDTO(long id, String country, String time, String day, String jobType, ConsultantDTO consultant) {
//        this.id = id;
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
//    public ConsultantDTO getConsultant() {
//        return consultant;
//    }
//
//    public void setConsultant(ConsultantDTO consultant) {
//        this.consultant = consultant;
//    }
//
//    @Override
//    public String toString() {
//        return "ScheduleDTO{" +
//                "id=" + id +
//                ", country='" + country + '\'' +
//                ", time='" + time + '\'' +
//                ", day='" + day + '\'' +
//                ", jobType='" + jobType + '\'' +
//                ", consultant=" + consultant +
//                '}';
//    }
}
