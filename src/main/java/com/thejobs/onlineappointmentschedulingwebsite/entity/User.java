package com.thejobs.onlineappointmentschedulingwebsite.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "fName", length = 30,nullable = false)
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Appointment> appointment;

    public User() {
    }

    public User(long id, String fName, String lName, String gender, String email, String contactNumber, String password, List<Appointment> appointment) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
        this.appointment = appointment;
    }
    public User( String fName, String lName, String gender, String email, String contactNumber, String password) {

        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", password='" + password + '\'' +
                ", appointment=" + appointment +
                '}';
    }
}
