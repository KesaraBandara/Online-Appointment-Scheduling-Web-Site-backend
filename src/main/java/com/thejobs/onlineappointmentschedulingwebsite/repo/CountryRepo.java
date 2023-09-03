package com.thejobs.onlineappointmentschedulingwebsite.repo;


import com.thejobs.onlineappointmentschedulingwebsite.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepo extends JpaRepository<Country,Long> {

    Country findByCountry(String country);
}
