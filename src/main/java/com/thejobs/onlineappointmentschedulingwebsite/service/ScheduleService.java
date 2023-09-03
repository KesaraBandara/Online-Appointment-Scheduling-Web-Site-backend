package com.thejobs.onlineappointmentschedulingwebsite.service;

import com.thejobs.onlineappointmentschedulingwebsite.dto.ScheduleDTO;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Schedule;
import com.thejobs.onlineappointmentschedulingwebsite.repo.ScheduleRepo;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Transactional
public class ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private ModelMapper modelMapper;

    public String saveSchedule(ScheduleDTO scheduleDTO) {
        try {
            Schedule scheduleEntity = modelMapper.map(scheduleDTO, Schedule.class);

            // Check for duplicates based on the time value
            Schedule existingSchedule = scheduleRepo.findByTokenTimeDay(scheduleDTO.getToken(), scheduleDTO.getTime(), scheduleDTO.getDay());

            if (existingSchedule != null) {
                return VarList.RSP_DUPLICATED;
            }

            // Save the scheduleEntity
            scheduleRepo.save(scheduleEntity);
            return VarList.RSP_SUCCESS;
        } catch (Exception e) {
            // Handle the exception, log it, and return an appropriate response
            e.printStackTrace(); // You can replace this with proper logging
            return VarList.RSP_ERROR;
        }
    }

    public List<ScheduleDTO> getAllScheduleByToken(String token) {

        List<Schedule> scheduleList = scheduleRepo.getAllProductByToken(token);
        return modelMapper.map(scheduleList, new TypeToken<List<ScheduleDTO>>() {}.getType());

    }

    public String deleteSchedule(String id) {

        if (scheduleRepo.existsById(Long.valueOf(id))) {
            scheduleRepo.deleteById(Long.valueOf(id));
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
}
