package com.thejobs.onlineappointmentschedulingwebsite.service;


import com.thejobs.onlineappointmentschedulingwebsite.dto.CountryDTO;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Country;
import com.thejobs.onlineappointmentschedulingwebsite.repo.CountryRepo;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CountryService {

    @Autowired
    private CountryRepo countryRepo;

    @Autowired
    private ModelMapper modelMapper;
    public String saveCountry(CountryDTO countryDTO) {
        Country countryEntity = modelMapper.map(countryDTO, Country.class);

        // Check for duplicates based on the Country value
        Country existingCountry = countryRepo.findByCountry(countryDTO.getCountry());
        System.out.println(countryEntity);
        if (existingCountry != null) {
            return VarList.RSP_DUPLICATED;
        }

        // Save the countryEntity
        countryRepo.save(countryEntity);
        return VarList.RSP_SUCCESS;

    }

    public List<CountryDTO> getAllCountries() {

        List<Country> countriesList = countryRepo.findAll();

        return modelMapper.map(countriesList, new TypeToken<List<CountryDTO>>() {
        }.getType());

    }
}
