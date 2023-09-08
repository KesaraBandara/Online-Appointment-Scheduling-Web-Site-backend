package com.thejobs.onlineappointmentschedulingwebsite.service;

import com.thejobs.onlineappointmentschedulingwebsite.dto.ScheduleDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.ScheduleSummaryDTO;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Consultant;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Schedule;
import com.thejobs.onlineappointmentschedulingwebsite.repo.ConsultantRepo;
import com.thejobs.onlineappointmentschedulingwebsite.repo.ScheduleRepo;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ConsultantRepo consultantRepo;

    public String saveSchedule(ScheduleDTO scheduleDTO) {
        try {
            // Map the ScheduleDTO to Schedule entity
            Schedule scheduleEntity = modelMapper.map(scheduleDTO, Schedule.class);

            // Retrieve the Consultant entity based on ConsultantDTO
            Consultant consultant = consultantRepo.findById(scheduleDTO.getConsultant().getId()).orElse(null);

            if (consultant == null) {
                // Handle the case where the Consultant is not found
                return VarList.RSP_ERROR;
            }

            // Set the Consultant in the Schedule entity
            scheduleEntity.setConsultant(consultant);

            // Check for duplicates based on the time value
            Schedule existingSchedule = scheduleRepo.findByTimeAndDayAndConsultant(
                    scheduleDTO.getTime(), scheduleDTO.getDay(), consultant);

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
//
//    public List<ScheduleDTO> getAllScheduleByToken(String consultant) {
//
//        List<Schedule> scheduleList = scheduleRepo.getAllScheduleByConsultant(consultant);
//        return modelMapper.map(scheduleList, new TypeToken<List<ScheduleDTO>>() {}.getType());
//
//    }

    public List<ScheduleSummaryDTO> getAllScheduleById(String consultantId) {
        Optional<Consultant> consultant = consultantRepo.findById(Long.valueOf(consultantId));

        if (consultant.isPresent()) {
            List<Schedule> scheduleList = scheduleRepo.getAllScheduleByConsultant(Optional.of(consultant.get()));
            return scheduleList.stream()
                    .map(this::mapToScheduleSummaryDTO)
                    .collect(Collectors.toList());
        } else {
            // Handle the case where the Consultant with the given ID is not found
            return new ArrayList<>(); // Or return an appropriate response
        }
    }

    private ScheduleSummaryDTO mapToScheduleSummaryDTO(Schedule schedule) {
        ScheduleSummaryDTO dto = new ScheduleSummaryDTO();
        dto.setScheduleId(schedule.getId());
        dto.setCountry(schedule.getCountry());
        dto.setDay(schedule.getDay());
        dto.setJobType(schedule.getJobType());
        dto.setTime(schedule.getTime());
        dto.setConsultantId(schedule.getConsultant().getId());
        return dto;
    }




    public String deleteSchedule(String id) {

        if (scheduleRepo.existsById(Long.valueOf(id))) {
            scheduleRepo.deleteById(Long.valueOf(id));
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<ScheduleSummaryDTO> getAllSchedule() {
        List<Schedule> scheduleList = scheduleRepo.findAll();
        return scheduleList.stream()
                .map(this::mapToScheduleSummaryDTO)
                .collect(Collectors.toList());
    }


//    public List<ScheduleDTO> getAllScheduleByCountryAndJobType(String country, String jobType) {
//        List<Schedule> scheduleList = scheduleRepo.getScheduleByCountryAndJobType(country,jobType);
//        return modelMapper.map(scheduleList, new TypeToken<List<ScheduleDTO>>() {}.getType());
//    }

    public List<ScheduleSummaryDTO> getAllScheduleByCountryAndJobType(String country, String jobType) {
        List<Schedule> scheduleList = scheduleRepo.getScheduleByCountryAndJobType(country, jobType);
        return scheduleList.stream()
                .map(this::mapToScheduleSummaryDTO)
                .collect(Collectors.toList());
    }


}
