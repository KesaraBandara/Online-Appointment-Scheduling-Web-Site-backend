package com.thejobs.onlineappointmentschedulingwebsite.service;

import com.thejobs.onlineappointmentschedulingwebsite.dto.TimeDTO;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Time;
import com.thejobs.onlineappointmentschedulingwebsite.repo.TimeRepo;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TimeService {

    @Autowired
    private TimeRepo timeRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String saveTime(TimeDTO timeDTO) {
        // Convert TimeDTO to Time entity
        Time timeEntity = modelMapper.map(timeDTO, Time.class);

        // Check for duplicates based on the time value
        Time existingTime = timeRepo.findByTime(timeDTO.getTime());
        System.out.println(timeEntity);
        if (existingTime != null) {
            return VarList.RSP_DUPLICATED;
        }

        // Save the timeEntity
        timeRepo.save(timeEntity);
        return VarList.RSP_SUCCESS;
    }

    public List<TimeDTO> getAllTimes() {
        List<Time> timeList = timeRepo.findAll();

        return modelMapper.map(timeList, new TypeToken<List<TimeDTO>>() {
        }.getType());

    }
}
