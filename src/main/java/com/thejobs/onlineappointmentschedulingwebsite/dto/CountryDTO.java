package com.thejobs.onlineappointmentschedulingwebsite.dto;

public class CountryDTO {

    private long id;
    private String country;
    private ConsultantDTO consultantId;

    public CountryDTO() {
    }

    public CountryDTO(long id, String country, ConsultantDTO consultantId) {
        this.id = id;
        this.country = country;
        this.consultantId = consultantId;
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

    public ConsultantDTO getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(ConsultantDTO consultantId) {
        this.consultantId = consultantId;
    }

    @Override
    public String toString() {
        return "CountryDTO{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", consultantId=" + consultantId +
                '}';
    }
}
