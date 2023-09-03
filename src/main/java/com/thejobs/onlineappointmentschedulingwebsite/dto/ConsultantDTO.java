package com.thejobs.onlineappointmentschedulingwebsite.dto;

public class ConsultantDTO {
    private long id;
    private String fName;
    private String lName;
    private String gender;
    private String email;
    private String contactNumber;
    private String password;
    private CountryDTO country;
    private JobDTO jobType;
    private TimeDTO time;

    public ConsultantDTO() {
    }

    public ConsultantDTO(long id, String fName, String lName, String gender, String email, String contactNumber, String password, CountryDTO country, JobDTO jobType, TimeDTO time) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
        this.country = country;
        this.jobType = jobType;
        this.time = time;
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

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public JobDTO getJobType() {
        return jobType;
    }

    public void setJobType(JobDTO jobType) {
        this.jobType = jobType;
    }

    public TimeDTO getTime() {
        return time;
    }

    public void setTime(TimeDTO time) {
        this.time = time;
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
                ", country=" + country +
                ", jobType=" + jobType +
                ", time=" + time +
                '}';
    }
}
