package com.thejobs.onlineappointmentschedulingwebsite.service;

import com.thejobs.onlineappointmentschedulingwebsite.dto.*;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Appointment;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Consultant;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Schedule;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Time;
import com.thejobs.onlineappointmentschedulingwebsite.repo.AppointmentRepo;
import com.thejobs.onlineappointmentschedulingwebsite.repo.ConsultantRepo;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import jakarta.persistence.EntityNotFoundException;
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
public class AppointmentService {
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
     private ModelMapper modelMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
     private ConsultantRepo consultantRepo;




    public String saveAppointment(AppointmentDTO appointmentDTO) {
        try {
            Appointment appointmentEntity = modelMapper.map(appointmentDTO, Appointment.class);

            // Check for duplicates based on the time value
            Consultant consultant = modelMapper.map(appointmentDTO.getConsultant(), Consultant.class);
            Appointment existingAppointment = appointmentRepo.findByConsultantAndTimeAndDate(consultant, appointmentDTO.getTime(), appointmentDTO.getDate());

            if (existingAppointment != null) {
                return VarList.RSP_DUPLICATED;
            }

            // Save the appointmentEntity
            appointmentRepo.save(appointmentEntity);
            return VarList.RSP_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace(); // You can replace this with proper logging
            return VarList.RSP_ERROR;
        }
    }



    public void updateActivity(Long appointmentId, AppointmentVerifyDTO appointmentVerifyDTO) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + appointmentId));

        if (appointmentVerifyDTO.isConfirm()) {
            appointment.setActiveState(true);
            emailService.sendConfirmationEmail(appointment.getUser().getEmail());
        } else if (appointmentVerifyDTO.isNonConfirm()) {
            appointmentRepo.delete(appointment);
            emailService.sendCancellationEmail(appointment.getUser().getEmail());
        }
    }


    public List<AppointmentDTO> getAllAppointmentById(String consultantId) {

        Optional<Consultant> consultant = consultantRepo.findById(Long.valueOf(consultantId));

        if (consultant.isPresent()) {
            List<Appointment> appointmentList = appointmentRepo.getAppointmentByConsultant(Optional.of(consultant.get()));
            return appointmentList.stream()
                    .map(this::convertToAppointmentDTO)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

//    public List<AppointmentDTO> getAllAppointmentById(String consultantId) {
//        Optional<Consultant> consultant = consultantRepo.findById(Long.valueOf(consultantId));
//
//        if (consultant.isPresent()) {
//            List<Appointment> appointmentList = appointmentRepo.getAppointmentByConsultant(Optional.of(consultant.get()));
//
//            // Map the Appointment entities to AppointmentDTOs and set the same username
//            return appointmentList.stream()
//                    .map(appointment -> {
//                        AppointmentDTO appointmentDTO = convertToAppointmentDTO(appointment);
//                        appointmentDTO.setUserDTO(appointmentDTO.getUserDTO().getfName()); // Set the username
//                        return appointmentDTO;
//                    })
//                    .collect(Collectors.toList());
//        } else {
//            return new ArrayList<>();
//        }
//    }


    private AppointmentDTO convertToAppointmentDTO(Appointment appointment) {

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setTime(appointment.getTime());
        appointmentDTO.setDate(appointment.getDate());
        appointmentDTO.setDay(appointment.getDay());
        appointmentDTO.setCountry(appointment.getCountry());
        appointmentDTO.setJobType(appointment.getJobType());

        return appointmentDTO;
    }


//    public List<AppointmentDTO> getAllAppointments() {
//            List<Appointment> appointmentList = appointmentRepo.findAll();
//            return modelMapper.map(appointmentList, new TypeToken<List<AppointmentDTO>>() {
//            }.getType());
//    }

    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointmentList = appointmentRepo.findAll();
        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();

        for (Appointment appointment : appointmentList) {
            AppointmentDTO appointmentDTO = modelMapper.map(appointment, AppointmentDTO.class);
            UserDTO userDTO = modelMapper.map(appointment.getUser(), UserDTO.class);
            appointmentDTO.setUserDTO(userDTO);
            appointmentDTOList.add(appointmentDTO);
        }

        return appointmentDTOList;
    }
//public List<AppointmentDTO> getAllAppointments() {
//    List<Appointment> appointmentList = appointmentRepo.findAll();
//    List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
//
//    for (Appointment appointment : appointmentList) {
//        AppointmentDTO appointmentDTO = modelMapper.map(appointment, AppointmentDTO.class);
//
//        // Create UserDTO with only the "fName" field
//        UserDTO userDTO = new UserDTO();
//        userDTO.setfName(appointment.getUser().getfName());
//
//        // Create ConsultantDTO with only the "fName" field
//        ConsultantDTO consultantDTO = new ConsultantDTO();
//        consultantDTO.setfName(appointment.getConsultant().getfName());
//
//        // Set UserDTO and ConsultantDTO in the AppointmentDTO
//        appointmentDTO.setUserDTO(userDTO);
//        appointmentDTO.setConsultant(consultantDTO);
//
//        appointmentDTOList.add(appointmentDTO);
//    }
//
//    return appointmentDTOList;
//}
//public List<AppointmentDTO> getAllAppointments() {
//    List<Appointment> appointmentList = appointmentRepo.findAll();
//    List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
//
//    for (Appointment appointment : appointmentList) {
//        AppointmentDTO appointmentDTO = modelMapper.map(appointment, AppointmentDTO.class);
//
//        // Create UserDTO with only the "fName" field
//        UserDTO userDTO = new UserDTO();
//        userDTO.setfName(appointment.getUser().getfName());
//
//        // Create ConsultantDTO with only the "fName" field
//        ConsultantDTO consultantDTO = new ConsultantDTO();
//        consultantDTO.setfName(appointment.getConsultant().getfName());
//
//        // Set UserDTO and ConsultantDTO in the AppointmentDTO
//        appointmentDTO.setUserDTO(userDTO);
//        appointmentDTO.setConsultant(consultantDTO);
//
//        appointmentDTOList.add(appointmentDTO);
//    }
//
//    return appointmentDTOList;
//}


}
