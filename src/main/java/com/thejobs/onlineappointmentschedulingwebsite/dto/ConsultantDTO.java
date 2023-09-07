package com.thejobs.onlineappointmentschedulingwebsite.dto;

public class ConsultantDTO {
    private long id;
    private String fName;
    private String lName;
    private String gender;
    private String email;
    private String contactNumber;
    private String password;
    private AppointmentDTO appointment;
    private ScheduleDTO schedule;
//    private TimeDTO time;


    public ConsultantDTO() {
    }

    public ConsultantDTO(long id, String fName, String lName, String gender, String email, String contactNumber, String password, AppointmentDTO appointment, ScheduleDTO schedule) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
        this.appointment = appointment;
        this.schedule = schedule;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AppointmentDTO getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentDTO appointment) {
        this.appointment = appointment;
    }

    public ScheduleDTO getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDTO schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "ConsultantDTO{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", password='" + password + '\'' +
                ", appointment=" + appointment +
                ", schedule=" + schedule +
                '}';
    }
}
