package com.thejobs.onlineappointmentschedulingwebsite.entity;

import jakarta.persistence.*;

import java.util.HashSet;
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
    @ManyToMany
    @JoinTable(
            name = "consultant_country",
            joinColumns = @JoinColumn(name = "consultant_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> countries = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "consultant_job",
            joinColumns = @JoinColumn(name = "consultant_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private Set<Job> jobTypes = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "consultant_time",
            joinColumns = @JoinColumn(name = "consultant_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "time_id")
    )
    private Set<Time> availableTimes = new HashSet<>();

    public Consultant() {
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

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public Set<Job> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(Set<Job> jobTypes) {
        this.jobTypes = jobTypes;
    }

    public Set<Time> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(Set<Time> availableTimes) {
        this.availableTimes = availableTimes;
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
                ", countries=" + countries +
                ", jobTypes=" + jobTypes +
                ", availableTimes=" + availableTimes +
                '}';
    }
}
