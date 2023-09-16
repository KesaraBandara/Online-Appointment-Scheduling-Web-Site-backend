package com.thejobs.onlineappointmentschedulingwebsite.dto;

public class AppointmentDTO {

    private long id;
//    private String userId;
    private String time;
    private String date;
    private String day;
    private String country;
    private String jobType;
    private UserDTO userDTO;
    private ConsultantDTO consultant;

    public AppointmentDTO() {
    }

    public AppointmentDTO(long id, String time, String date, String day, String country, String jobType, UserDTO userDTO, ConsultantDTO consultant) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.day = day;
        this.country = country;
        this.jobType = jobType;
        this.userDTO = userDTO;
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

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public ConsultantDTO getConsultant() {
        return consultant;
    }

    public void setConsultant(ConsultantDTO consultant) {
        this.consultant = consultant;
    }

    @Override
    public String toString() {
        return "AppointmentDTO{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", country='" + country + '\'' +
                ", jobType='" + jobType + '\'' +
                ", userDTO=" + userDTO +
                ", consultant=" + consultant +
                '}';
    }
}
