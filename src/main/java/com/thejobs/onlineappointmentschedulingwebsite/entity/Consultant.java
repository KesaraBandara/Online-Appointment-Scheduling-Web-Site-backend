package com.thejobs.onlineappointmentschedulingwebsite.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "consultant")
public class Consultant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column  (name = "fName", length = 30,nullable = false)
    private String fName;
    @Column  (name = "lName", length = 30,nullable = false)
    private String lName;
    @Column  (name = "gender", length = 6,nullable = false)
    private String gender;

    @Column  (name = "email", length = 30,nullable = false)
    private String email;
    @Column  (name = "contactNumber", length = 20,nullable = false)
    private String contactNumber;

    @Column  (name = "password", length = 100,nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consultant")
    private List<Appointment> appointment;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "consultant")
    private List<Schedule> schedule;

    public Consultant() {
    }

    public Consultant(long id, String fName, String lName, String gender, String email, String contactNumber, String password, List<Appointment> appointment, List<Schedule> schedule) {
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
    public Consultant( String fName, String lName, String gender, String email, String contactNumber, String password) {
//        , Set<Country> countries, Set<Job> jobTypes, Set<Time> availableTimes
//        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
//        this.countries = countries;
//        this.jobTypes = jobTypes;
//        this.availableTimes = availableTimes;
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

    public List<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(List<Appointment> appointment) {
        this.appointment = appointment;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Consultant{" +
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
