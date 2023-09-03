package com.thejobs.onlineappointmentschedulingwebsite.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column  (name = "country_id", length = 20)
    private long id;
    @Column  (name = "country", length = 20,nullable = false)
    private String country;
    @ManyToMany(mappedBy = "countries")
    private Set<Consultant> consultants = new HashSet<>();

    public Country() {
    }

    public Country(long id, String country, Set<Consultant> consultants) {
        this.id = id;
        this.country = country;
        this.consultants = consultants;
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

    public Set<Consultant> getConsultants() {
        return consultants;
    }

    public void setConsultants(Set<Consultant> consultants) {
        this.consultants = consultants;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", consultants=" + consultants +
                '}';
    }
}
